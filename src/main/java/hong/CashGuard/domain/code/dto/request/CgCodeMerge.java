package hong.CashGuard.domain.code.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.code.dto.request
 * fileName       : CgCodeMerge
 * author         : work
 * date           : 2025-04-02
 * description    : 부모 코드 수정 + 자식 코드 수정/저장
 *                  > 자식 코드 수정/저장 list 값은 empty 가능
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgCodeMerge {

    @NotNull @Valid
    private CgCodeObj parent;

    @Valid
    private List<CgCodeObj> addChild;

    @Valid
    private List<CgCodeObj> updtChild;
}