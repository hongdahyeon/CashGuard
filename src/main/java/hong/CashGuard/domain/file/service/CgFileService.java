package hong.CashGuard.domain.file.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * packageName    : hong.CashGuard.domain.file.service
 * fileName       : CgFileService
 * author         : work
 * date           : 2025-04-03
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgFileService {

    /*<select id="generateKey" resultType="Long">
     //common-file.generateKey
    select NVL(MAX(hong_file_uid)+1, 1) as uid
    from hong_file
    </select>*/
    /*private Long generateKey(){
        return fileMapper.generateKey();
    }*/

    /*@Transactional
    public Long saveAndDelFiles(Long uid, HongSaveFileDto[] addFile, String[] delFile){
        Long fileUid = null;
        Long userUid = UserUtil.getLoginUserUid();
        if((addFile != null && addFile.length > 0) || (delFile != null && delFile.length > 0)) {
            fileUid = (uid == null) ? generateKey() : uid;
            for (HongSaveFileDto dto : addFile){
                dto.setHongFileUid(fileUid);
                dto.setRegMdfr(userUid);
                fileMapper.saveFile(dto);
            }

            for (String delFileUrl : delFile){
                fileMapper.deleteFile(new HongDeleteFileDto(delFileUrl));
            }
        }
        return fileUid;
    }*/

}
