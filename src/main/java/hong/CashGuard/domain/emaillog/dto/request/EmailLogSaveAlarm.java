package hong.CashGuard.domain.emaillog.dto.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.emaillog.dto.request
 * fileName       : EmailLogSaveAlarm
 * author         : work
 * date           : 2025-04-07
 * description    : 이메일 발송 저장 요청용 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailLogSaveAlarm {

    private String recipientEmail;
    private String senderEmail;
    private String subject;
    private String content;
    private String reasonCode;

    @Builder(builderMethodName = "insertEmailLogSaveAlarm")
    public EmailLogSaveAlarm(String recipientEmail, String senderEmail, String subject, String content, String reasonCode) {
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
        this.subject = subject;
        this.content = content;
        this.reasonCode = reasonCode;
    }
}
