package hong.CashGuard.domain.emaillog.domain;

import hong.CashGuard.domain.emaillog.dto.request.EmailLogSave;
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
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgEmailLog extends AuditBean {

    private Long uid;
    private String emailToken;
    private String recipientEmail;
    private String senderEmail;
    private String subject;
    private String content;
    private String reasonCode;
    private String sentDt;
    private String isRead;
    private String readDt;

    public CgEmailLog(CgSessionUser user, EmailLogSave request) {
        this.emailToken = request.getEmailToken();
        this.recipientEmail = request.getRecipientEmail();
        this.senderEmail = user.getUserEmail();
        this.subject = request.getSubject();
        this.content = request.getContent();
        this.reasonCode = request.getReasonCode();
        this.isRead = "N";
    }
}