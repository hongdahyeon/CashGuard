package hong.CashGuard.domain.code.service;

import hong.CashGuard.domain.code.dto.request.CgCodeMerge;
import hong.CashGuard.domain.code.dto.request.CgCodeObj;
import hong.CashGuard.domain.code.dto.request.CgCodeParam;
import hong.CashGuard.domain.code.dto.request.CgCodeSave;
import hong.CashGuard.domain.code.dto.response.CgCodeList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.bean.error.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.code.service
 * fileName       : CgCodeRestController
 * author         : home
 * date           : 2025-03-31
 * description    : 코드 API (접근 권한: super)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 * 2025-04-02        work       * 부모 코드 저장 API 수정 : /cguard/api/code/super/parent
 *                              * 코드 페이징 API 수정 : /cguard/api/code/super/page
 *                              * 부모 코드 수정 및 자식 코드 수정/저장 API 추가
 */

@RestController
@RequestMapping("/cguard/api/code/super")
@RequiredArgsConstructor
public class CgCodeRestController {

    private final CgCodeService service;

    /**
     *
     * 부모 코드 저장
     *
     * @api         [POST] /cguard/api/code/super/parent
     * @author      home
     * @date        2025-03-31
    **/
    @PostMapping("/parent")
    public ResponseEntity saveParentCode(@RequestBody @Valid CgCodeSave request) {
        if( !service.isExistCode(request.getCode()) ) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT,
                                                    request.getCode() + " 코드 값은 중복된 코드입니다.",
                                                       "conflict");
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errorResponse);
        }
        service.saveParentCode(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 코드 중복 체크
     *
     * @api         [GET] /cguard/api/code/super/duplicate-check
     * @author      home
     * @date        2025-03-31
    **/
    @GetMapping("/duplicate-check")
    public ResponseEntity checkDuplicateCode(@RequestParam("code") String code) {
        boolean checkCanUse = service.isExistCode(code);
        String message = "사용 가능한 코드ID 값입니다.";
        Map<String, Object> map = new HashMap<>();
        if( !checkCanUse ) {
            message = "중복된 코드ID 값 입니다.";
        }
        map.put("checkCanUse", checkCanUse);
        map.put("message", message);

        return ResponseEntity.ok(map);
    }

    /**
     *
     * 부모 코드 하위의 자식 코드 목록 조회 (리스트)
     *
     * @api         [GET] /cguard/api/code/super/all-children
     * @author      home
     * @date        2025-03-31
    **/
    @GetMapping("/all-children")
    public ResponseEntity findAllChildren(@RequestParam("code") String code) {
        List<CgCodeList> childrenCodeList = service.findAllChildren(code);
        return ResponseEntity.ok(childrenCodeList);
    }


    /**
     *
     * 코드 목록 조회 (페이징)
     *
     * @api         [GET] /cguard/api/code/super/page
     * @author      home
     * @date        2025-03-31
    **/
    @GetMapping("/page")
    public ResponseEntity findAllCodePage(@Valid CgCodeParam request, Pageable pageable) {
        Page<CgCodeList> allCodePage = service.findAllCodePage(request, pageable);
        return ResponseEntity.ok(allCodePage);
    }


    /**
     *
     * 부모 코드 수정 및 자식 코드 수정/저장
     *  > 추가되는 자식 코드 중복 체크
     *
     * @api         [PUT] /cguard/api/code/super/all-process
     * @author      work
     * @date        2025-04-02
    **/
    @PostMapping("/all-process")
    public ResponseEntity saveAllProcess(@RequestBody @Valid CgCodeMerge request) {

        List<String> duplicateCodes = new ArrayList<>();
        List<CgCodeObj> addChild = request.getAddChild();
        if( addChild != null && !addChild.isEmpty() ) {
            for (CgCodeObj child : request.getAddChild()) {
                if (!service.isExistCode(child.getCode())) {
                    duplicateCodes.add(child.getCode());
                }
            }
            if (!duplicateCodes.isEmpty()) {
                String errorMessage = String.join(", ", duplicateCodes) + "\n코드 값은 중복된 코드입니다.";
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, errorMessage, "conflict");
                return ResponseEntity
                        .status(HttpStatus.CONFLICT)
                        .body(errorResponse);
            }
        }

        service.saveAllProcess(request);
        return ResponseEntity.ok().build();
    }

}