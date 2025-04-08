package hong.CashGuard.global.hong.ollama.domain;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.global.hong.ollama.domain
 * fileName       : CustomJdbcMapper
 * author         : work
 * date           : 2025-04-08
 * description    : Ollama > jdbc mapper
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */
@Mapper
public interface CustomJdbcMapper {

    int getIndex(String sessionId);

    int insertConversation(CgChatMemory bean);

    List<CgChatMemory> getChatList(Map<String, Object> params);

    int clearBySessionId(String sessionId);

    int clearByUserId(String userId);
}
