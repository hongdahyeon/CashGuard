package hong.CashGuard.domain.chat.service;

import hong.CashGuard.domain.chat.dto.request.ChatRequest;
import hong.CashGuard.global.hong.ollama.OllamaChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.CashGuard.domain.chat.service
 * fileName       : CgChatService
 * author         : work
 * date           : 2025-04-08
 * description    : AI 채팅 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgChatService {

    private final OllamaChatService chatService;

    public String askAI(ChatRequest request) {
        return chatService.ask(request);
    }

    public void clearConversation() {
        chatService.deleteConversation();;
    }
}
