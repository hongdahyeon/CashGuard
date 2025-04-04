package hong.CashGuard.domain.filelog.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.filelog.dto.request
 * fileName       : CgFileLogSave
 * author         : work
 * date           : 2025-04-04
 * description    : 파일 로그 저장 요청 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgFileLogSave {

    @NotNull
    private Long fileUid;

    @NotBlank
    private String fileUrl;
}
