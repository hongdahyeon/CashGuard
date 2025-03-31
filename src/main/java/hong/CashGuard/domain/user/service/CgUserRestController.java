package hong.CashGuard.domain.user.service;

import hong.CashGuard.domain.user.dto.request.CgUserParam;
import hong.CashGuard.domain.user.dto.request.CgUserSave;
import hong.CashGuard.domain.user.dto.response.CgUserList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.user.service
 * fileName       : CgUserRestController
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-03-31        home       list, page API 추가
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/user")
public class CgUserRestController {

    private final CgUserService service;

    @PostMapping
    public ResponseEntity saveUser(@RequestBody @Valid CgUserSave request) {
        service.saveUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity findAllUserPage(@Valid CgUserParam param, Pageable pageable) {
        Page<CgUserList> allUserPage = service.findAllUserPage(param, pageable);
        return ResponseEntity.ok(allUserPage);
    }

    @GetMapping("/list")
    public ResponseEntity findAllUserList(@Valid CgUserParam request) {
        List<CgUserList> allUser = service.findAllUserList(request);
        return ResponseEntity.ok(allUser);
    }
}
