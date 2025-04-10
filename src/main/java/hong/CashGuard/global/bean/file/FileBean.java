package hong.CashGuard.global.bean.file;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.global.bean.file
 * fileName       : FileBean
 * author         : home
 * date           : 2025-04-03
 * description    : 첨부파일 빈
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        home       최초 생성
 * 2025-04-04        work       {thumbnail} 관련 필드 추가
 */
@Getter
public class FileBean {

    private FileDto[] addFile;
    private String[] delFile;

    private FileDto addThumbnail;
    private String delThumbnail;
}
