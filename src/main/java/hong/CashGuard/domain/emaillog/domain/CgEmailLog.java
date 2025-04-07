package hong.CashGuard.domain.emaillog.domain;

import hong.CashGuard.domain.emaillog.dto.request.EmailLogSave;
import hong.CashGuard.domain.emaillog.dto.request.EmailLogSaveAlarm;
import hong.CashGuard.global.auth.dto.CgSessionUser;
import hong.CashGuard.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.emaillog.domain
 * fileName       : CgEmailLog
 * author         : work
 * date           : 2025-04-02
 * description    : 이메일 발송 로그 Entity
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-04        work       {AuditBean} 위치 변경
 * 2025-04-07        work       {예산 초과에 따른 알람 발송 이메일 로그 저장용 생성자} 추가
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgEmailLog extends AuditBean {

    private Long uid;
    private String emailToken;
    private String recipientEmail;
    private String senderEmail;
    private String subject;
    private String content;
    private String reasonCode;      // [EmailSendReason]
    private String sentDt;
    private String isRead;
    private String readDt;

    /**
     * @method      CgEmailLog 생성자 1
     * @author      work
     * @date        2025-04-07
     * @deacription 그룹 초대에 따른 이메일 로그 저장용 생성자 1
    **/
    public CgEmailLog(CgSessionUser user, EmailLogSave request) {
        this.emailToken = request.getEmailToken();
        this.recipientEmail = request.getRecipientEmail();
        this.senderEmail = user.getUserEmail();
        this.subject = request.getSubject();
        this.content = request.getContent();
        this.reasonCode = request.getReasonCode();
        this.isRead = "N";
    }

    /**
     * @method      CgEmailLog 생성자 2
     * @author      work
     * @date        2025-04-07
     * @deacription 예산 초과에 따른 알람 발송 이메일 로그 저장용 생성자 2
    **/
    public CgEmailLog(EmailLogSaveAlarm request) {
        this.recipientEmail = request.getRecipientEmail();
        this.senderEmail = request.getSenderEmail();
        this.subject = request.getSubject();
        this.content = request.getContent();
        this.reasonCode = request.getReasonCode();
        this.isRead = "N";
    }
}