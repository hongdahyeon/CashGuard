package hong.CashGuard.global.hong.ollama;

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
public class OllamaChatService {

    private final ChatClient chatClient;

    public OllamaChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String ask(String prompt) {
        return chatClient.prompt().user(prompt).call().content();
    }
}
