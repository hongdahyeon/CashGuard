package hong.CashGuard.domain.emaillog.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.emaillog.dto.request
 * fileName       : EmailLogSave
 * author         : work
 * date           : 2025-04-02
 * description    : 이메일 발송 로그 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor
public class EmailLogSave {

    private String emailToken;
    private String recipientEmail;
    private String subject;
    private String content;
    private String reasonCode;

    @Builder(builderMethodName = "saveEmailLog")
    public EmailLogSave(String emailToken,String recipientEmail,
                        String subject, String content, String reasonCode) {
        this.emailToken = emailToken;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.content = content;
        this.reasonCode = reasonCode;
    }
}
