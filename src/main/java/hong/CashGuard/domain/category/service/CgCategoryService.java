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
    **/
    @Transactional(readOnly = true)
    public boolean isExistCategoryCode(String code) {
        Integer findCodeCnt = mapper.isExistCategoryCode(code);
        return findCodeCnt != 1;
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
}
