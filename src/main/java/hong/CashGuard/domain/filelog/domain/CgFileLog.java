package hong.CashGuard.domain.filelog.domain;

import hong.CashGuard.domain.filelog.dto.request.CgFileLogSave;
import hong.CashGuard.global.bean.audit.AuditBean;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.filelog.domain
 * fileName       : CgFileLog
 * author         : work
 * date           : 2025-04-04
 * description    : 파일 로그 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 * 2025-04-09        work       {regUid, regDt} 필드는 {AuditBean} 으로 분리
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgFileLog extends AuditBean {

    private Long uid;
    private Long fileUid;
    private String fileUrl;

    public CgFileLog(CgFileLogSave request) {
        this.fileUid = request.getFileUid();
        this.fileUrl = request.getFileUrl();
    }
}
