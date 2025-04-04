package hong.CashGuard.domain.category.service;

import hong.CashGuard.domain.category.dto.request.CgCategoryChange;
import hong.CashGuard.domain.category.dto.request.CgCategoryParam;
import hong.CashGuard.domain.category.dto.request.CgCategorySave;
import hong.CashGuard.domain.category.dto.response.CgCategoryList;
import hong.CashGuard.domain.code.Category;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import hong.CashGuard.global.bean.error.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.domain.category.service
 * fileName       : CgCategoryRestController
 * author         : work
 * date           : 2025-04-02
 * description    : 카테고리 관련 API
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-04        work       {isExistCategoryCode} 리턴 값 변경 -> 로직 수정
 *                              * 기존에는 {isExistCategoryCode}메소드명과 반대로 리턴을 해줘서 헷갈리는 문제 발생
 *                                => 값이 중복(존재)한다면 true, 없다면 false로 리턴해주도록 변경
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/cguard/api/category")
public class CgCategoryRestController {

    private final CgCategoryService service;

    /**
     * @method      checkIsValidCategory
     * @author      work
     * @date        2025-04-02
     * @deacription 카테고리 유형 값이 enum 유형에 속하는지 체크
    **/
    private ResponseEntity checkIsValidCategory(String categoryTp) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_ACCEPTABLE, "없는 카테고리 유형입니다.", "Not Acceptable");
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(errorResponse);
    }

    /**
     * @method      checkIsDuplicateCategory
     * @author      work
     * @date        2025-04-02
     * @deacription 카테고리 코드 값이 중복되는지 체크
    **/
    private ResponseEntity checkIsDuplicateCategory(String categoryCd) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.CONFLICT, categoryCd + " 코드 값은 중복된 코드입니다.", "conflict");
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

    /**
     *
     * 카테고리 코드 저장
     *
     * @api         [POST] /cguard/api/category
     * @author      work
     * @date        2025-04-02
    **/
    @PostMapping
    public ResponseEntity saveCategory(@RequestBody @Valid CgCategorySave request) {
        // { Category } enum 타입에 명시된 코드인지 체크
        if( !Category.isValidCategory(request.getCategoryTp()) ) {
            return this.checkIsValidCategory(request.getCategoryTp());
        }

        // { 카테고리 코드 } 값이 중복되는지 더블 체크
        if( service.isExistCategoryCode(request.getCategoryCd()) ) {
            return this.checkIsDuplicateCategory(request.getCategoryCd());
        }

        service.saveCategory(request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 카테고리 코드 중복 체크
     *
     * @api         [GET] /cguard/api/category/duplicate-check
     * @author      work
     * @date        2025-04-02
    **/
    @GetMapping("/duplicate-check")
    public ResponseEntity checkDuplicateCode(@RequestParam("code") String code) {
        boolean isExists = service.isExistCategoryCode(code);
        String message = "사용 가능한 카테고리 코드 값입니다.";
        Map<String, Object> map = new HashMap<>();
        if( isExists ) {
            message = "중복된 카테고리 코드 값 입니다.";
        }
        map.put("checkCanUse", !isExists);
        map.put("message", message);

        return ResponseEntity.ok(map);
    }

    /**
     *
     * 카테고리 코드 정보 수정
     *
     * @api         [PUT] /cguard/api/category/{uid}
     * @author      work
     * @date        2025-04-02
    **/
    @PutMapping("/{uid}")
    public ResponseEntity changeCategory(@PathVariable("uid") Long uid, @RequestBody @Valid CgCategoryChange request) {
        service.changeCategory(uid, request);
        return ResponseEntity.ok().build();
    }

    /**
     *
     * 카테고리 목록 조회 (페이징)
     *
     * @api         [GET] /cguard/api/category/page
     * @author      work
     * @date        2025-04-02
    **/
    @GetMapping("/page")
    public ResponseEntity findAllPage(@Valid CgCategoryParam param, Pageable pageable) {
        Page<CgCategoryList> allPage = service.findAllPage(param, pageable);
        return ResponseEntity.ok(allPage);
    }

    /**
     *
     * 카테고리 목록 조회 (리스트)
     *
     * @api         [GET] /cguard/api/category/list
     * @author      work
     * @date        2025-04-02
    **/
    @GetMapping("/list")
    public ResponseEntity findAllList(@Valid CgCategoryParam param) {
        List<CgCategoryList> allList = service.findAllList(param);
        return ResponseEntity.ok(allList);
    }
}
