package hong.CashGuard.global.hong.ollama;

import hong.CashGuard.domain.chat.dto.request.ChatRequest;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.CashGuard.global.hong.ollama
 * fileName       : OllamaChatService
 * author         : work
 * date           : 2025-04-08
 * description    : Spring AI 기반 Ollama 서비스
 *                  => Ollama : LLM 실행 가능한 오픈 소스 플랫폼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class OllamaChatService {

   private final ChatClient chatClient;
   private final CustomJdbcMemory jdbcMemory;

    public String ask(ChatRequest request) {
        jdbcMemory.setSessionId(UserUtil.getLoginUser().getSessionId(), UserUtil.getLoginUser().getUserId());
        return chatClient.prompt()
                .user(request.getQ())
                .call().content();
    }

    public void deleteConversation() {
        jdbcMemory.clearUserConversation(UserUtil.getLoginUser().getUserId());
    }
}
