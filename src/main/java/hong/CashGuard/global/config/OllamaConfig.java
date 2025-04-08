package hong.CashGuard.global.config;

import hong.CashGuard.global.hong.ollama.CustomJdbcMemory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * packageName    : hong.CashGuard.global.config
 * fileName       : OllamaConfig
 * author         : work
 * date           : 2025-04-08
 * description    : [Spring AI]의 [ChatClient]에 커스텀 JDBC 기반 메모리를 붙이기 위한 설정 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */

@Configuration
public class OllamaConfig {

    /**
     * @method      chatClient
     * @author      work
     * @date        2025-04-08
     * @deacription {ChatClient} 생성 빈
    **/
    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, CustomJdbcMemory chatMemory) {
        return builder
                .defaultAdvisors(new MessageChatMemoryAdvisor(chatMemory))  // 채팅 문맥 저장 기능을 이용한다
                .defaultSystem("""
                    너는 친절하고 기억력이 좋은 한국어 챗봇이야.
                    사용자가 이전에 말한 내용을 기억하고, 관련된 대화가 이어지도록 답변해.
                    모르는 경우 추측하지 말고, 이전 문맥과 관련해서 대답하도록 해.
                """)
                .build();
    }

    /**
     * @method      customJdbcMemory
     * @author      work
     * @date        2025-04-08
     * @deacription {CustomJdbcMemory} 생성 빈
     *              => Spring AI가 문맥 저장소로 인식한다
    **/
    @Bean
    public CustomJdbcMemory customJdbcMemory(JdbcTemplate jdbcTemplate) {
        return new CustomJdbcMemory(jdbcTemplate);
    }
}
