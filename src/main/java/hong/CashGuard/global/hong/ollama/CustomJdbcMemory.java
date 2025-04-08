package hong.CashGuard.global.hong.ollama;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * packageName    : hong.CashGuard.global.hong.ollama
 * fileName       : CustomJdbcMemory
 * author         : work
 * date           : 2025-04-08
 * description    : Spring AI 대화 문맥 저장소
 *                  => {ChatMemory}를 그대로 이용하려면 테이블 명을 "chat_memory"로 해야 한다.
 *                     여기서는 "cg_" 접두어를 붙이기로 하였기에 다음과 같이 인터페이스를 직접 오버라이딩하여 구현해야 한다.
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */
@Component
public class CustomJdbcMemory implements ChatMemory {

    private String sessionId;
    private final JdbcTemplate jdbcTemplate;

    public CustomJdbcMemory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @method      add
     * @author      work
     * @date        2025-04-08
     * @deacription 대화 내용을 저장
    **/
    @Override
    public void add(String conversationId, List<Message> messages) {
        for (Message message : messages) {
            int index = jdbcTemplate.queryForObject(
                    "SELECT COALESCE(MAX(message_index), -1) + 1 FROM cash_guard.cg_chat_memory WHERE session_id = ?",
                    Integer.class,
                    this.sessionId
            );
            jdbcTemplate.update(
                    "INSERT INTO cash_guard.cg_chat_memory(session_id, message_index, role, content) VALUES (?, ?, ?, ?)",
                    this.sessionId,
                    index,
                    message.getMessageType().getValue(), // "user", "assistant", etc.
                    message.getText()
            );
        }
    }

    /**
     * @method      get
     * @author      work
     * @date        2025-04-08
     * @deacription {sessionId} 값을 통해 이전 대화 내용 가져오기
    **/
    @Override
    public List<Message> get(String conversationId, int lastN) {
        String sql = """
            SELECT role, content
              FROM cash_guard.cg_chat_memory
             WHERE session_id = ?
             ORDER BY message_index DESC
             LIMIT ?
            """;

        List<Message> messages = jdbcTemplate.query(sql, new Object[]{this.sessionId, lastN}, (rs, rowNum) -> {
            String role = rs.getString("role");
            String content = rs.getString("content");

            MessageType messageType = MessageType.fromValue(role);  // USER, ASSISTANT, TOOL, SYSTEM, TOOL
            switch (messageType) {
                case USER:
                    return new UserMessage(content);
                case ASSISTANT:
                    return new AssistantMessage(content);
                case SYSTEM:
                    return new SystemMessage(content);
                default:
                    throw new IllegalArgumentException("Unsupported message type: " + role);
            }
        });

        Collections.reverse(messages);
        return messages;
    }

    /**
     * @method      clear
     * @author      work
     * @date        2025-04-08
     * @deacription {sessionId}에 해당하는 대화 내용 삭제
    **/
    @Override
    public void clear(String conversationId) {
        jdbcTemplate.update("DELETE FROM cash_guard.cg_chat_memory WHERE session_id = ?", this.sessionId);
    }
}
