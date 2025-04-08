package hong.CashGuard.domain.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : hong.CashGuard.domain.chat.dto.request
 * fileName       : ChatRequest
 * author         : work
 * date           : 2025-04-08
 * description    : 채팅 질문 요청용 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */

@Getter @Setter
public class ChatRequest {

    @NotBlank
    private String q;
}
