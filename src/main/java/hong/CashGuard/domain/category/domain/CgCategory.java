package hong.CashGuard.domain.category.domain;

import hong.CashGuard.domain.category.dto.request.CgCategoryChange;
import hong.CashGuard.domain.category.dto.request.CgCategorySave;
import hong.CashGuard.global.bean.audit.AuditBean;
import hong.CashGuard.global.util.StringUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.category.domain
 * fileName       : CgCategory
 * author         : work
 * date           : 2025-04-02
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-04        work       {AuditBean} 위치 변경
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCategory extends AuditBean {

    private Long uid;
    private String categoryCd;
    private String categoryNm;
    private String categoryTp;  // [Category]
    private String useAt;

    /**
     * @method      CgCategory  생성자 1
     * @author      work
     * @date        2025-04-02
     * @deacription 카테고리 저장 생성자
    **/
    public CgCategory(CgCategorySave request) {
        this.categoryCd = request.getCategoryCd();
        this.categoryNm = request.getCategoryNm();
        this.categoryTp = request.getCategoryTp();
        this.useAt = request.getUseAt();
    }

    /**
     * @method      CgCategory  생성자 2
     * @author      work
     * @date        2025-04-02
     * @deacription 카테고리 수정 생성자
    **/
    public CgCategory changeCategory(Long categoryUid, CgCategoryChange request) {
        this.uid = categoryUid;
        this.categoryNm = StringUtil.getOrDefault(request.getCategoryNm(), this.categoryNm);
        this.useAt = StringUtil.getOrDefault(request.getUseAt(), this.useAt);
        return this;
    }
}