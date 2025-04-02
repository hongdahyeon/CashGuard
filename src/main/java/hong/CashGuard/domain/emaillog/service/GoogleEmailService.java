package hong.CashGuard.domain.emaillog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.CashGuard.domain.emaillog.service
 * fileName       : GoogleEmailService
 * author         : work
 * date           : 2025-04-02
 * description    : 구글 SMTP 이메일 발송 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class GoogleEmailService {

    /*private final JavaMailSender javaMailSender;*/

    /*@Value("${spring.mail.username}")
    private String smtpSender;*/
    private String smtpSender = "";

    public void sendMail(String to, String fromEmail, String fromName, String subject, String content) {
        /*MimeMessage message = javaMailSender.createMimeMessage();
        try{
            // TODO 메일 발송 구현
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            *//*
            * @ param1 : smtp 이메일 계정
            * @ param2 : 대리 발신자명
            * *//*
            helper.setFrom(smtpSender, fromName);

            *//*
            * 사용자가 해당 이메일에 [답장]을 하게 되면 전송되는 이메일 주소
            * *//*
            helper.setReplyTo(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch(MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }*/
    }
}
