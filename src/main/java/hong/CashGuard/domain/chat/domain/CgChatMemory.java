package hong.CashGuard.domain.chat.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.chat.domain
 * fileName       : CgChatMemory
 * author         : work
 * date           : 2025-04-08
 * description    : AI 채팅 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgChatMemory {

    private String sessionId;
    private int messageIndex;
    private String role;
    private String content;
    private String userId;
    private String deleteAt;

    @Builder(builderMethodName = "insertChatBuilder")
    public CgChatMemory(String sessionId, int messageIndex, String role, String content, String userId) {
        this.sessionId = sessionId;
        this.messageIndex = messageIndex;
        this.role = role;
        this.content = content;
        this.userId = userId;
    }
}
