package hong.CashGuard.domain.emaillog.service;

import hong.CashGuard.domain.emaillog.domain.CgEmailLog;
import hong.CashGuard.domain.emaillog.domain.CgEmailLogMapper;
import hong.CashGuard.domain.emaillog.dto.request.EmailLogSave;
import hong.CashGuard.domain.emaillog.dto.request.EmailLogSaveAlarm;
import hong.CashGuard.global.mail.GoogleEmailService;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : hong.CashGuard.domain.email.service
 * fileName       : CgEmailLogService
 * author         : work
 * date           : 2025-04-02
 * description    : 이메일 발송 로그 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-07        work       예산 초과에 따른 메일 발송 서비스 로직 추가
 */

@Service
@RequiredArgsConstructor
public class CgEmailLogService {

    private final CgEmailLogMapper mapper;
    private final GoogleEmailService googleEmailService;

    /**
     * @method      EmailLogSaveAndSend
     * @author      work
     * @date        2025-04-02
     * @deacription 이메일 발송 정보 저장 및 전송
     *              => 그룹 초대 시점 이용
    **/
    @Transactional
    public void EmailLogSaveAndSend(EmailLogSave request) {
        // 1. 이메일 전송 정보 저장
        mapper.insert(new CgEmailLog(UserUtil.getLoginUser(), request));

        // 2. 이메일 전송
        googleEmailService.sendMail(request.getRecipientEmail(),
                                    UserUtil.getLoginUser().getUserEmail(),
                                    UserUtil.getLoginUser().getUserNm(),
                                    request.getSubject(),
                                    request.getContent());
    }

    /**
     * @method      saveEmailLogAndSendAlertAlarm
     * @author      work
     * @date        2025-04-07
     * @deacription 이메일 발송 정보 저장
     *              => 예산 초과 알람 발송 시점에 이용
    **/
    @Transactional
    public void saveEmailLogAndSendAlertAlarm(EmailLogSaveAlarm request) {
        // 1. 이메일 전송 정보 저장
        CgEmailLog cgEmailLog = new CgEmailLog(request);
        mapper.insert(cgEmailLog);

        // 2. 이메일 전송
        googleEmailService.sendAlertEmail(request.getRecipientEmail(), request.getSubject(), request.getContent());

        // 3. 이메일 읽음 여부 자동 변경
        mapper.changeIsRead(cgEmailLog.getUid());
    }


    /**
     * @method      isValidToken
     * @author      work
     * @date        2025-04-03
     * @deacription {token} 값이 유효한 토큰인지 체크
     *              -> is_read 값이 'N'이어야 한다.
    **/
    @Transactional(readOnly = true)
    public boolean isValidToken(String token) {
        return mapper.isInvalidToken(token);
    }


    /**
     * @method      findEmailLogByUid
     * @author      work
     * @date        2025-04-03
     * @deacription {token} 값을 통해 email_log 정보 찾기
    **/
    @Transactional(readOnly = true)
    public CgEmailLog findEmailLogByUid(String token) {
        return mapper.view(token);
    }


    /**
     * @method      changeTokenIsRead
     * @author      work
     * @date        2025-04-03
     * @deacription 사용자가 초대링크를 통해 접근을 하고 나면, 해당 토큰의 유효성 없애기
     *              => is_read 값을 'Y'로 변경
    **/
    @Transactional
    public void changeTokenIsRead(String token) {
        mapper.updateTokenIsRead(token);
    }

}
