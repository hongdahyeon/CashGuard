package hong.CashGuard.global.bean;

import hong.CashGuard.global.util.ReflectorUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * packageName    : hong.CashGuard.global.bean
 * fileName       : Pageable
 * author         : home
 * date           : 2025-03-31
 * description    : 페이징 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 */


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Pageable {

    private Integer countPage = 10;
    private Integer pageNumber = 1;
    private Integer size = 10;

    public Pageable(Integer size) {
        this.size = size;
    }

    public void setCountPage(Integer countPage) {
        this.countPage = countPage;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSize(Integer size){
        this.size = size;
    }

    public final Map<String, Object> generateMap(Object bean) {
        Map<String, Object> map = ReflectorUtil.getGetterMap(bean);
        map.put("startNumber", (this.pageNumber - 1) * this.size);      // 시작 번호
        map.put("endNumber", this.size);                                // 끝 번호
        return map;
    }
}
