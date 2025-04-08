package hong.CashGuard.global.hong.ollama;

import hong.CashGuard.global.hong.ollama.domain.CgChatMemory;
import hong.CashGuard.global.hong.ollama.domain.CustomJdbcMapper;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
 * 2025-04-08        work       JdbcTemplate 이용에서 매퍼 mybatis 이용으로 변경
 */
@Component
public class CustomJdbcMemory implements ChatMemory {

    private String sessionId;
    private String userId;
    private final CustomJdbcMapper mapper;

    public CustomJdbcMemory(CustomJdbcMapper mapper) {
        this.mapper = mapper;
    }

    public void setSessionId(String sessionId, String userId) {
        this.sessionId = sessionId;
        this.userId = userId;
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
            int index = mapper.getIndex(this.sessionId);
            CgChatMemory bean = CgChatMemory.insertChatBuilder()
                    .sessionId(this.sessionId)
                    .messageIndex(index)
                    .role(message.getMessageType().getValue())
                    .content(message.getText())
                    .userId(this.userId)
                    .build();
            mapper.insertConversation(bean);
        }
    }

    /**
     * @method      get
     * @author      work
     * @date        2025-04-08
     * @deacription {sessionId},{lastN} 값을 통해 이전 대화 내용 가져오기
    **/
    @Override
    public List<Message> get(String conversationId, int lastN) {

        Map<String, Object> params = Map.of(
          "sessionId", this.sessionId,
          "limit", lastN
        );
        List<CgChatMemory> chatList = mapper.getChatList(params);

        List<Message> messages = new ArrayList<>();
        for (CgChatMemory chatMemory : chatList) {
            Message message;
            String role = chatMemory.getRole();
            String content = chatMemory.getContent();
            MessageType messageType = MessageType.fromValue(role); // USER, ASSISTANT, TOOL, SYSTEM

            switch (messageType) {
                case USER:
                    message = new UserMessage(content);
                    break;
                case ASSISTANT:
                    message = new AssistantMessage(content);
                    break;
                case SYSTEM:
                    message = new SystemMessage(content);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported message type: " + role);
            }
            messages.add(message);
        }
        Collections.reverse(messages);
        return messages;
    }

    /**
     * @method      clear
     * @author      work
     * @date        2025-04-08
     * @deacription {sessionId} 값으로 대화 내용 삭제
    **/
    @Override
    public void clear(String conversationId) {
        mapper.clearBySessionId(this.sessionId);
    }


    /**
     * @method      clearUserConversation
     * @author      work
     * @date        2025-04-08
     * @deacription {userId} 값으로 대화 내용 삭제
    **/
    public void clearUserConversation(String userId) {
        mapper.clearByUserId(userId);
    }
}
