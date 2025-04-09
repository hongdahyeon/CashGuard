package hong.CashGuard.global.bean.file;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.global.bean.file
 * fileName       : FileDto
 * author         : home
 * date           : 2025-04-03
 * description    : { FileBean }에서 사용되는 파일 업로드 요청에 대한 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        home       최초 생성
 * 2025-04-09        work       {filePath} 필드 추가
 */

@Getter @NoArgsConstructor
public class FileDto {

    private Long uid;
    private String fileUrl;
    private String fileId;
    private String fileNm;
    private String fileType;
    private String fileSize;
    private String extension;
    private String saved;

    @Builder(builderMethodName = "insertFileDto")
    public FileDto(String fileUrl, String fileId, String fileNm, String fileType,
                   String fileSize, String extension, String saved) {
        this.fileUrl = fileUrl;
        this.fileId = fileId;
        this.fileNm = fileNm;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.extension = extension;
        this.saved = saved;
    }
}
