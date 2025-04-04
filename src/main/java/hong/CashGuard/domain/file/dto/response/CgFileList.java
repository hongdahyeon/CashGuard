package hong.CashGuard.domain.file.dto.response;

import hong.CashGuard.global.bean.audit.AuditMetaData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.file.dto.response
 * fileName       : CgFileList
 * author         : work
 * date           : 2025-04-04
 * description    : 파일 리스트 조회 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgFileList extends AuditMetaData {

    private Long uid;
    private String fileUrl;
    private String fileId;
    private String fileNm;
    private String fileType;
    private String fileSize;
    private String extension;
    private String downloadCnt;
    private String saved;

}
