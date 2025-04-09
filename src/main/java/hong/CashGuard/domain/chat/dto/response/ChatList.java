package hong.CashGuard.domain.chat.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.chat.dto.response
 * fileName       : ChatList
 * author         : work
 * date           : 2025-04-09
 * description    : 대화 내용 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-09        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatList {

    private String sessionId;
    private int messageIndex;
    private String role;
    private String content;
    private String userId;
    private String deleteAt;
}
