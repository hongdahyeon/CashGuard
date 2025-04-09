package hong.CashGuard.global.config;

import hong.CashGuard.global.hong.ollama.CustomJdbcMemory;
import hong.CashGuard.domain.chat.domain.CgChatMemoryMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
 * 2025-04-08        work       defaultSystem 제거 , jdbcTemplate -> 매퍼 이용 변경
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
    public CustomJdbcMemory customJdbcMemory(CgChatMemoryMapper mapper) {
        return new CustomJdbcMemory(mapper);
    }
}
