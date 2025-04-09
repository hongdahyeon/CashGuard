package hong.CashGuard.domain.file.domain;

import hong.CashGuard.global.bean.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : hong.CashGuard.domain.file.domain
 * fileName       : CgFileMapper
 * author         : home
 * date           : 2025-04-03
 * description    : 첨부파일 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        home       최초 생성
 * 2025-04-09        work       {updateDownloadCnt} 추가 -> {fileId} 값을 통해 다운로드 횟수 증가
 */
@Mapper
public interface CgFileMapper extends BaseMapper<CgFile> {

    Long generateKey();

    int updateDownloadCnt(CgFile bean);
}
