package hong.CashGuard.domain.file.service;

import hong.CashGuard.domain.code.FileSaved;
import hong.CashGuard.domain.file.domain.CgFile;
import hong.CashGuard.domain.file.domain.CgFileMapper;
import hong.CashGuard.domain.file.dto.response.CgFileList;
import hong.CashGuard.global.bean.file.FileDto;
import hong.CashGuard.global.exception.CGException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
 * 2025-04-04        work       파일 목록/썸네일 조회
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
    public Long saveAndDelFile(Long fileUid, FileDto[] addFile, String[] delFile) {

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

    /**
     * @method      findAllList
     * @author      work
     * @date        2025-04-04
     * @deacription 파일 목록 조회
    **/
    @Transactional(readOnly = true)
    public List<CgFileList> findAllList(Long uid) {
        return mapper.list(new CgFile(uid, FileSaved.SAVED.name()));
    }

    /**
     * @method      findThumbnail
     * @author      work
     * @date        2025-04-04
     * @deacription 썸네일 이미지 조회
     *              > 썸네일 이미지의 경우 최대 1개만 갖을 수 있다.
    **/
    @Transactional(readOnly = true)
    public CgFileList findThumbnail(Long uid) {
        List<CgFileList> list = mapper.list(new CgFile(uid, FileSaved.SAVED.name()));
        if(list.isEmpty()) return null;

        if(list.size() > 1) {
            throw new CGException("썸네일 이미지는 최대 1개입니다.", HttpStatus.BAD_REQUEST);
        }
        return list.get(0);
    }
}
