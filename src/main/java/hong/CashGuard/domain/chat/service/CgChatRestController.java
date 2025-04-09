package hong.CashGuard.domain.chat.service;

import hong.CashGuard.domain.chat.dto.request.ChatRequest;
import hong.CashGuard.domain.chat.dto.response.ChatList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.chat.service
 * fileName       : CgChatRestController
 * author         : work
 * date           : 2025-04-08
 * description    : AI 채팅 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-08        work       최초 생성
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/chat")
public class CgChatRestController {

    private final CgChatService service;

    @PostMapping("/ask")
    public ResponseEntity ask(@RequestBody @Valid ChatRequest request) {
        String response = service.askAI(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear-conversation")
    public ResponseEntity clear() {
        service.clearConversation();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public ResponseEntity findAllChatListByUserId() {
        List<ChatList> chatList = service.findAllChatListByUserId();
        return ResponseEntity.ok(chatList);
    }

}
