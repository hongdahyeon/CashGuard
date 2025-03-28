package hong.CashGuard.domain.user.service;

import hong.CashGuard.domain.user.dto.request.CgUserSave;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
