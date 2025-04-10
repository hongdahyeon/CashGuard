package hong.CashGuard.domain.category.service;

import hong.CashGuard.domain.category.domain.CgCategory;
import hong.CashGuard.domain.category.domain.CgCategoryMapper;
import hong.CashGuard.domain.category.dto.request.CgCategoryChange;
import hong.CashGuard.domain.category.dto.request.CgCategoryParam;
import hong.CashGuard.domain.category.dto.request.CgCategorySave;
import hong.CashGuard.domain.category.dto.response.CgCategoryList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.category.service
 * fileName       : CgCategoryService
 * author         : work
 * date           : 2025-04-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-04        work       카테고리 코드 중복 체크 메소드(isExistCategoryCode)가 메소드명과 반대로 리턴을 해줌
 *                              => 헷갈리는 문제 발생
 *                              => isExist(존재, 중복)한다면 true / 없다면 false 로 리턴해줌
 * 2025-04-06        note       ifHasCategory 추가 (uid 값으로 카테고리 정보 있는지 체크)
 */

@Service
@RequiredArgsConstructor
public class CgCategoryService {

    private final CgCategoryMapper mapper;

    /**
     * @method      saveCategory
     * @author      work
     * @date        2025-04-02
     * @deacription 카테고리 정보 저장
    **/
    @Transactional
    public void saveCategory(CgCategorySave request) {
        mapper.insert(new CgCategory(request));
    }

    /**
     * @method      isExistCategoryCode
     * @author      work
     * @date        2025-04-02
     * @deacription 카테고리 코드 중복 체크
     *              -> 있다면 : true  (중복되는 code)
     *              -> 없다면 : false (사용 가능한 code)
    **/
    @Transactional(readOnly = true)
    public boolean isExistCategoryCode(String code) {
        Integer findCodeCnt = mapper.countByCategoryCd(code);
        return findCodeCnt == 1;
    }

    /**
     * @method      changeCategory
     * @author      work
     * @date        2025-04-02
     * @deacription 카테고리 정보 수정
    **/
    @Transactional
    public void changeCategory(Long categoryUid, CgCategoryChange request) {
        CgCategory myView = mapper.view(categoryUid);
        mapper.update(myView.changeCategory(categoryUid, request));
    }

    /**
     * @method      findAllPage
     * @author      work
     * @date        2025-04-02
     * @deacription 카테고리 목록 조회 (페이징)
    **/
    @Transactional(readOnly = true)
    public Page<CgCategoryList> findAllPage(CgCategoryParam param, Pageable pageable) {
        List<CgCategoryList> list = mapper.page(pageable.generateMap(param));
        int count = mapper.count(param);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      findAllList
     * @author      work
     * @date        2025-04-02
     * @deacription 카테고리 목록 조회 (리스트)
    **/
    @Transactional(readOnly = true)
    public List<CgCategoryList> findAllList(CgCategoryParam param) {
        return mapper.list(param);
    }


    /**
     * @method      ifHasCategory
     * @author      note
     * @date        2025-04-06
     * @deacription {categoryUid} 값에 해당하는 카테고리 정보가 있는지 체크
    **/
    @Transactional(readOnly = true)
    public boolean ifHasCategory(Long categoryUid) {
        int count = mapper.countAllByLCategoryUid(categoryUid);
        return count != 0;
    }
}
