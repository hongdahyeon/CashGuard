package hong.CashGuard.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.domain.code
 * fileName       : Category
 * author         : work
 * date           : 2025-04-02
 * description    : 카테고리 유형 enum
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-07        work       EXPEND -> EXPENSE
 */

@Getter
public enum Category {

    INCOME("수입 카테고리", true),
    EXPENSE("지출 카테고리", false),
    SAVINGS("저축/투자 카테고리", false);

    private String categoryNm;
    private boolean isAddTp;

    Category(String categoryNm, boolean isAddTp) {
        this.categoryNm = categoryNm;
        this.isAddTp = isAddTp;
    }

    public static boolean isValidCategory(String categoryTp) {
        for( Category category : Category.values() ) {
            if(category.name().equals(categoryTp)) {
                return true;
            }
        }
        return  false;
    }
}