package hong.CashGuard.global.scheduler;

import hong.CashGuard.domain.budget.dto.response.CgBudgetList;
import hong.CashGuard.domain.budget.service.CgBudgetService;
import hong.CashGuard.domain.code.BudgetTrans;
import hong.CashGuard.domain.trans.dto.request.CgTransCheck;
import hong.CashGuard.domain.trans.dto.response.CgTransCheckAlert;
import hong.CashGuard.domain.trans.service.CgTransService;
import hong.CashGuard.global.mail.GoogleEmailService;
import hong.CashGuard.global.util.StringUtil;
import hong.CashGuard.global.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * packageName    : hong.CashGuard.global.scheduler
 * fileName       : CheckBudget
 * author         : work
 * date           : 2025-04-07
 * description    : {budget}에 따라서 {trans}값을 체크하고 알람 전송 (스케줄러)
 *                  # cron: 초  / 분  / 시  / 일  / 월  / 요일(일~토)
 *                  => 모든 값 : *
 *                  => 여러개 나열 : 콤마(,) 이용
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckBudget {

    private final CgBudgetService budgetService;
    private final CgTransService transService;
    private final GoogleEmailService emailService;

    @Scheduled(cron = "0 19 12 * * *")
    public void checkBudgetScheduler() {

        // [EXCEED_ALERT = 'Y'] 인 정보들만 조회
        List<CgBudgetList> budgets = budgetService.findAllBudgetList();
        budgets.forEach(budget -> {
            // [step1] 기간 구하기
            String periodType = budget.getPeriodType();
            long periodVal = budget.getPeriodVal();

            LocalDate today = TimeUtil.today();
            LocalDate startDate = null;
            LocalDate endDate = null;
            switch (periodType) {
                case "YEAR":
                    startDate = today.withDayOfYear(1);
                    endDate = startDate.plusYears(periodVal).minusDays(1);
                    break;

                case "MONTH":
                    startDate = today.withDayOfMonth(1);
                    endDate = startDate.plusMonths(periodVal).minusDays(1);
                    break;

                case "DAYS":
                    startDate = today;
                    endDate = startDate.plusDays(periodVal - 1); // 오늘 포함
                    break;
            }
            LocalDateTime startDateTime = startDate.atStartOfDay(); // 00:00:00
            LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // 23:59:59

            // [step2] 거래 내역 합계 조회
            this.handleExpense(budget, startDateTime, endDateTime);
        });
    }

    private void handleExpense(CgBudgetList budget, LocalDateTime start, LocalDateTime end) {

        CgTransCheck cgTransCheck = CgTransCheck.checkTransBuilder()
                .userUid(budget.getUserUid())
                .ledgerUid(budget.getLedgerUid())
                .transCd(budget.getTransTpCd())
                .start(start).end(end).build();

        // [step3] {start ~ end} 범위에 대해서 {transCd} 유형에 대해 총 합계 
        // => INCOME : 수입 합
        // => EXPENSE : 지출 합
        long transTotal = transService.checkTransExceed(cgTransCheck);
        boolean ifExceed = transTotal > budget.getTransTargetAmount();      // 합계의 초과 여부

        String typeLabel = "INCOME".equals(budget.getTransTpCd()) ? "수입" : "지출";
        String resultLabel = "INCOME".equals(budget.getTransTpCd()) ? "달성" : "초과";

        log.info("💰 [{} 목표 체크] 유저={}, 가계부={}, {}액={}원 / 목표={}원 -> {} 여부: {}",
                typeLabel, budget.getUserUid(), budget.getLedgerUid(), typeLabel,
                StringUtil.numberFormat(transTotal), StringUtil.numberFormat(budget.getTransTargetAmount()), resultLabel, ifExceed);

        // [step4] 만일 목표 금액을 초과한다면 ...
        if (ifExceed) {
            long exceededAmount = transTotal - budget.getTransTargetAmount();
            log.info("⚠️ [{} 초과 알림] 유저={}, 가계부={}, {}액={}원 / 목표={}원 → 초과된 금액: {}원",
                    typeLabel, budget.getUserUid(), budget.getLedgerUid(), typeLabel,
                    StringUtil.numberFormat(transTotal), StringUtil.numberFormat(budget.getTransTargetAmount()), StringUtil.numberFormat(exceededAmount));

            // [step5] 해당 유저에게 초과 메일 발송
            // => 아직 초과 알림을 전송한적 없는 유저에게만 메일을 발송한다.
            String sendAlarmByUid = budgetService.findSendAlarmByUid(budget.getBudgetUid());
            if("N".equals(sendAlarmByUid)) {
                CgTransCheckAlert info = transService.getAlertUserInfoByLedgerUid(budget.getLedgerUid());
                info.setTransInfo(BudgetTrans.getBudgetTrans(budget.getTransTpCd()));
                info.setAmountInfo(StringUtil.numberFormat(exceededAmount),
                        StringUtil.numberFormat(budget.getTransTargetAmount()),
                        StringUtil.numberFormat(transTotal));

                String title = this.formatTitle(info);
                String content = this.formatContent(info);
                emailService.sendAlertEmail(info.getUserEmail(), title, content);

                // [step6] send_alarm 수정하기
                budgetService.changeSendAlarmByUid(budget.getBudgetUid());
            }
        }
    }

    private String formatTitle(CgTransCheckAlert info) {
        if(BudgetTrans.EXPENSE.name().equals(info.getTransCd())) {
            return String.format("[Cash Guard] %s님, '%s' 가계부의 %s 목표를 초과했습니다!",
                    info.getUserNm(),
                    info.getLedgerNm(),
                    info.getTransCdNm()
            );
        } else if (BudgetTrans.INCOME.name().equals(info.getTransCd())) {
            return String.format("[Cash Guard] %s님, '%s' 가계부의 %s 목표를 초과 달성했어요! 🎉",
                    info.getUserNm(),
                    info.getLedgerNm(),
                    info.getTransCdNm()
            );
        }
        return "";
    }

    private String formatContent(CgTransCheckAlert alert) {
        if(BudgetTrans.EXPENSE.name().equals(alert.getTransCd())) {
            return String.format("""
                <div style="font-family: 'Segoe UI', sans-serif; font-size: 14px; color: #333; line-height: 1.6;">
                  <h2 style="color: #2c3e50;">💰 수입/지출 목표 초과 알림</h2>
                  
                  <p>안녕하세요, <strong>%s</strong>님.</p>
                
                  <p>
                    고객님이 사용 중인 가계부 <strong>[%s]</strong>에서 설정한 
                    <strong>%s</strong> 목표를 초과하였습니다.
                  </p>
                
                  <table style="border-collapse: collapse; width: 100%%; margin-top: 20px;">
                    <thead>
                      <tr>
                        <th colspan="2" style="background-color: #f6f6f6; padding: 10px; text-align: left; border: 1px solid #ddd;">📌 내역 요약</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                        <td style="padding: 10px; border: 1px solid #ddd; width: 30%%;">가계부 이름</td>
                        <td style="padding: 10px; border: 1px solid #ddd;">%s</td>
                      </tr>
                      <tr>
                        <td style="padding: 10px; border: 1px solid #ddd;">거래 구분</td>
                        <td style="padding: 10px; border: 1px solid #ddd;">%s</td>
                      </tr>
                      <tr>
                        <td style="padding: 10px; border: 1px solid #ddd;">목표 금액</td>
                        <td style="padding: 10px; border: 1px solid #ddd;">%s원</td>
                      </tr>
                      <tr>
                        <td style="padding: 10px; border: 1px solid #ddd;">현재 합계</td>
                        <td style="padding: 10px; border: 1px solid #ddd;">%s원</td>
                      </tr>
                      <tr>
                        <td style="padding: 10px; border: 1px solid #ddd;">초과 금액</td>
                        <td style="padding: 10px; border: 1px solid #ddd; color: #e74c3c;"><strong>%s원</strong></td>
                      </tr>
                    </tbody>
                  </table>
                
                  <p style="margin-top: 30px;">
                    📢 설정하신 목표를 초과하였습니다.<br/>
                    <strong>%s</strong> 계획을 다시 점검해 보시기 바랍니다.
                  </p>
                
                  <p style="margin-top: 20px;">감사합니다.<br/><strong>CashGuard</strong> 드림</p>
                </div>
                """,
                    alert.getUserNm(),
                    alert.getLedgerNm(),
                    alert.getTransCdNm(),
                    alert.getLedgerNm(),
                    alert.getTransCdNm(),
                    alert.getTargetAmount(),
                    alert.getTotalAmount(),
                    alert.getExceedTotal(),
                    alert.getTransCdNm()
            );
        } else if (BudgetTrans.INCOME.name().equals(alert.getTransCd())) {
            return String.format("""
                        <div style="font-family: 'Arial', sans-serif; padding: 20px;">
                            <h2 style="color: #28a745;">🎉 수입 목표 초과 달성!</h2>
                            <p><strong>%s</strong>님, 축하드립니다! <strong>'%s'</strong> 가계부에서 수입 목표를 초과 달성하셨어요.</p>
                            <table style="width: 100%%; border-collapse: collapse; margin-top: 20px;">
                                <tr><td style="padding: 8px;">수입 목표</td><td style="padding: 8px;">%s원</td></tr>
                                <tr><td style="padding: 8px;">현재 수입</td><td style="padding: 8px;">%s원</td></tr>
                                <tr><td style="padding: 8px;">초과 금액</td><td style="padding: 8px; color: green;">%s원</td></tr>
                            </table>
                            <p style="margin-top: 20px;">멋진 재정 관리 계속 이어가세요! 💪</p>
                        </div>
                    """,
                    alert.getUserNm(),
                    alert.getLedgerNm(),
                    alert.getTargetAmount(),
                    alert.getTotalAmount(),
                    alert.getExceedTotal()
            );
        }
        return "";
    }

}
