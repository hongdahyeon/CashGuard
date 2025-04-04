package hong.CashGuard.domain.filelog.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.filelog.dto.response
 * fileName       : CgFileLogList
 * author         : work
 * date           : 2025-04-04
 * description    : 파일 목록 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgFileLogList {

    private Long uid;
    private Long fileUid;
    private String fileUrl;
    private Long regUid;
    private String regDt;
    private String regNm;
}
