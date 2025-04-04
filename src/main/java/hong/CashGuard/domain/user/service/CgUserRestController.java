package hong.CashGuard.domain.user.service;

import hong.CashGuard.domain.user.dto.request.CgUserChange;
import hong.CashGuard.domain.user.dto.request.CgUserParam;
import hong.CashGuard.domain.user.dto.request.CgUserPassword;
import hong.CashGuard.domain.user.dto.request.CgUserSave;
import hong.CashGuard.domain.user.dto.response.CgUserList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.exception.CGException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * packageName    : hong.CashGuard.domain.user.service
 * fileName       : CgUserRestController
 * author         : work
 * date           : 2025-03-27
 * description    : 사용자 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-03-31        home       list, page API 추가
 * 2025-04-01        work       update API 추가
 * 2025-04-02        work       * 유저 페이징 조회 API 수정 : /cguard/api/user/page
 * 2025-04-04        work       이메일/유저ID 중복 체크 로직 및 API 추가
 *                              => 유저 저장 이전에, 이메일 및 ID 중복 더블 체크 + 이메일 유효성 더블 체크
 *                              => 유저 수정 이전에, 이메일 유효성 더블 체크
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/user")
public class CgUserRestController {

    private final CgUserService service;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     *
     * 사용자 아이디, 이메일 중복 체크
     * > code : id / email
     * -> value : id(id값) / email(email값)
     *
     * @api         [GET] /cguard/api/user/{code}/duplicate-check
     * @author      work
     * @date        2025-04-04
    **/
    @GetMapping("/{code}/duplicate-check")
    public ResponseEntity duplicateCheck(@PathVariable("code") String code, @RequestParam(name = "value") String value) {
        Map<String, Object> map = new HashMap<>();
        boolean isExists = false;
        String message = "";
        if("id".equals(code)) {

            isExists = service.isExistUserId(value);
            if(isExists) message = "중복되는 사용자ID 입니다.";
            else message = "사용 가능한 사용자ID 입니다.";
            map.put("checkCanUse", !isExists);

        } else if("email".equals(code)) {

            boolean validEmail = isValidEmail(value);
            map.put("invalid", (!validEmail));
            if(!validEmail) {
                message = "유효하지 않은 이메일 형식입니다.";
                map.put("checkCanUse", false);
            } else {
                isExists = service.isExistUserEmail(value);
                if(isExists) message = "중복되는 이메일입니다.";
                else message = "사용 가능한 이메일입니다.";
                map.put("checkCanUse", !isExists);
            }
        }
        map.put("message", message);
        return ResponseEntity.ok(map);
    }

    /**
     *
     * 사용자 정보를 저장
     *
     * @api         [POST] /cguard/api/user
     * @author      work
     * @date        2025-03-27
    **/
    @PostMapping
    public ResponseEntity saveUser(@RequestBody @Valid CgUserSave request) {
        if(!this.isValidEmail(request.getUserEmail())) {
            throw new CGException("유효하지 않은 이메일 형식입니다.", HttpStatus.BAD_REQUEST);
        }
        
        if( service.isExistUserId(request.getUserId()) ) {
            throw new CGException("중복되는 유저ID 입니다.", HttpStatus.CONFLICT);
        }

        if( service.isExistUserEmail(request.getUserEmail()) ) {
            throw new CGException("중복되는 이메일 입니다.", HttpStatus.CONFLICT);
        }
        
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
        if(!this.isValidEmail(request.getUserEmail())) {
            throw new CGException("유효하지 않은 이메일 형식입니다.", HttpStatus.BAD_REQUEST);
        }
        service.changeUser(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 사용자 목록 조회 (페이징)
     *
     * @api         [GET] /cguard/api/user/page
     * @author      home
     * @date        2025-03-31
    **/
    @GetMapping("/page")
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
