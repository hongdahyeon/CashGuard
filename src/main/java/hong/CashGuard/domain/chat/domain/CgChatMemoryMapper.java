package hong.CashGuard.domain.chat.domain;

import hong.CashGuard.domain.chat.dto.response.ChatList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.chat.domain
 * fileName       : CgChatMemoryMapper
 * author         : work
 * date           : 2025-04-08
 * description    : Ollama > jdbc mapper
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */
@Mapper
public interface CgChatMemoryMapper {

    int getIndex(String sessionId);

    int insertConversation(CgChatMemory bean);

    List<CgChatMemory> getChatList(Map<String, Object> params);

    int clearBySessionId(String sessionId);

    int clearByUserId(String userId);

    List<ChatList> getAllChatByUserId(String userId);
}
