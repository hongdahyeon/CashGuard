package hong.CashGuard.domain.file.domain;

import hong.CashGuard.domain.code.FileSaved;
import hong.CashGuard.global.bean.audit.AuditBean;
import hong.CashGuard.global.bean.file.FileDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.file.domain
 * fileName       : CgFile
 * author         : home
 * date           : 2025-04-03
 * description    : 첨부파일 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        home       최초 생성
 * 2025-04-04        work       {AuditBean} 위치 변경
 */

@Getter @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CgFile extends AuditBean {

    private Long uid;
    private String fileUrl;
    private String fileId;
    private String fileNm;
    private String fileType;
    private String fileSize;
    private String extension;
    private int downloadCnt;
    private String deleteAt;
    private String saved;

    /**
     * @method      CgFile 생성자 1
     * @author      home
     * @date        2025-04-03
     * @deacription 파일 저장 생성자
    **/
    public CgFile(Long fileUid, FileDto dto) {
        this.uid = fileUid;
        this.fileUrl = dto.getFileUrl();
        this.fileId = dto.getFileId();
        this.fileNm = dto.getFileNm();
        this.fileType = dto.getFileType();
        this.fileSize = dto.getFileSize();
        this.extension = dto.getExtension();
        this.saved = dto.getSaved();
    }

    /**
     * @method      CgFile 생성자 2
     * @author      home
     * @date        2025-04-03
     * @deacription 파일 삭제 생성자
    **/
    public CgFile(String fileUrl) {
        this.fileUrl = fileUrl;
        this.saved = FileSaved.DELETE.name();
    }

    /**
     * @method      CgFile 생성자 3
     * @author      work
     * @date        2025-04-04
     * @deacription 파일 목록 조회 생성자
    **/
    public CgFile(Long uid, String saved) {
        this.uid = uid;
        this.saved = saved;
    }
}
