package hong.CashGuard.domain.file.service;

import hong.CashGuard.domain.file.domain.CgFile;
import hong.CashGuard.domain.file.domain.CgFileMapper;
import hong.CashGuard.global.bean.file.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : hong.CashGuard.domain.file.service
 * fileName       : CgFileService
 * author         : work
 * date           : 2025-04-03
 * description    : 첨부파일 관련 서비스 로직
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgFileService {

    private final CgFileMapper mapper;

    /**
     * @method      generateKey
     * @author      home
     * @date        2025-04-03
     * @deacription PK 생성
    **/
    private Long generateKey(){
        return mapper.generateKey();
    }

    /**
     * @method      saveAndDelFile
     * @author      home
     * @date        2025-04-03
     * @deacription 첨부파일 저장 및 삭제
    **/
    @Transactional
    public Long saveAndDelFile(Long fileUid, FileDto[] addFile, String[] delFile){

        if( fileUid == null ) {
            fileUid = this.generateKey();
        }

        if( addFile != null && addFile.length > 0 ) {
            for ( FileDto dto : addFile ){
                mapper.insert(new CgFile(fileUid, dto));
            }
        }

        if( delFile != null && delFile.length > 0 ) {
            for ( String fileUrl : delFile ){
                mapper.delete(new CgFile(fileUrl));
            }
        }

        return fileUid;
    }

}
