package hong.CashGuard.domain.chat.service;

import hong.CashGuard.domain.chat.domain.CgChatMemoryMapper;
import hong.CashGuard.domain.chat.dto.request.ChatRequest;
import hong.CashGuard.domain.chat.dto.response.ChatList;
import hong.CashGuard.global.hong.ollama.OllamaChatService;
import hong.CashGuard.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private final CgChatMemoryMapper mapper;

    /**
     * @method      askAI
     * @author      work
     * @date        2025-04-09
     * @deacription AI 질문 -> 응답
    **/
    public String askAI(ChatRequest request) {
        return chatService.ask(request);
    }

    /**
     * @method      clearConversation
     * @author      work
     * @date        2025-04-09
     * @deacription 대화 삭제
    **/
    @Transactional
    public void clearConversation() {
        chatService.deleteConversation();;
    }

    /**
     * @method      findAllChatListByUserId
     * @author      work
     * @date        2025-04-09
     * @deacription 로그인한 유저의 AI 대화 기록 가져오기
    **/
    @Transactional(readOnly = true)
    public List<ChatList> findAllChatListByUserId() {
        return mapper.getAllChatByUserId(UserUtil.getLoginUser().getUserId());
    }
}
