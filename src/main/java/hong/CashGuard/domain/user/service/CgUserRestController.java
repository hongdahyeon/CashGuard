package hong.CashGuard.domain.user.service;

import hong.CashGuard.domain.user.dto.request.CgUserChange;
import hong.CashGuard.domain.user.dto.request.CgUserParam;
import hong.CashGuard.domain.user.dto.request.CgUserPassword;
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
 * 2025-04-01        work       update API 추가
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/user")
public class CgUserRestController {

    private final CgUserService service;

    /**
     *
     * 사용자 정보를 저장
     *
     * @api         [POST] /cguard/api/user
     * @author      work
     * @date        2025-03-27
     * @param       request 사용자 정보 저장 DTO
    **/
    @PostMapping
    public ResponseEntity saveUser(@RequestBody @Valid CgUserSave request) {
        service.saveUser(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 사용자 정보 수정
     *
     * @api         [PUT] /cguard/api/user/{uid}
     * @author      work
     * @date        2025-04-01
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeUser(@PathVariable("uid") Long uid, @RequestBody @Valid CgUserChange request) {
        service.changeUser(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 사용자 목록 조회 (페이징)
     *
     * @api         [GET] /cguard/api/user
     * @author      home
     * @date        2025-03-31
    **/
    @GetMapping
    public ResponseEntity findAllUserPage(@Valid CgUserParam param, Pageable pageable) {
        Page<CgUserList> allUserPage = service.findAllUserPage(param, pageable);
        return ResponseEntity.ok(allUserPage);
    }


    /**
     *
     * 사용자 목록 조회 (리스트)
     *
     * @api         [GET] /cguard/api/user/list
     * @author      home
     * @date        2025-03-31
    **/
    @GetMapping("/list")
    public ResponseEntity findAllUserList(@Valid CgUserParam request) {
        List<CgUserList> allUser = service.findAllUserList(request);
        return ResponseEntity.ok(allUser);
    }


    /**
     *
     * 사용자 비밀번호 변경 + 비밀번호 마지막 변경일자 90일 연장
     *
     * @api         [PUT] /cguard/aip/user/change-password
     * @author      work
     * @date        2025-04-01
    **/
    @PutMapping("/change-password")
    public ResponseEntity changePassword(@RequestBody @Valid CgUserPassword request) {
        service.changePassword(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 사용자 잠김 해제
     *
     * @api         [PUT] /cguard/api/user/unlock/{uid}
     * @author      work
     * @date        2025-04-01
    **/
    @PutMapping("/unlock/{uid}")
    public ResponseEntity unLockUser(@PathVariable("uid") Long uid) {
        service.unLockUser(uid);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 사용자 계정 비활성화/활성화
     * => status 활성화(enable)
     * =>        비활성화(disable)
     *
     * @api         [PUT] /cguard/api/user/{status}/{uid}
     * @author      work
     * @date        2025-04-01
    **/
    @PutMapping("/{status}/{uid}")
    public ResponseEntity disableUser(@PathVariable("status") String status,
                                      @PathVariable("uid") Long uid) {
        boolean isEnable = "enable".equalsIgnoreCase(status);
        service.enableDisableUser(uid, isEnable);
        return ResponseEntity.ok().build();
    }
}
