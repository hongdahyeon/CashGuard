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
 * 2025-04-09        work       저장 생성자 추가
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgFileLogSave {

    @NotNull
    private Long fileUid;

    @NotBlank
    private String fileUrl;

    /**
     * @method      CgFileLogSave 생성자
     * @author      work
     * @date        2025-04-09
     * @deacription 파일 다운로드 시점에 사용되는 로그 저장용 DTO 생성자
    **/
    public CgFileLogSave(Long fileUid, String fileUrl) {
        this.fileUid = fileUid;
        this.fileUrl = fileUrl;
    }
}
