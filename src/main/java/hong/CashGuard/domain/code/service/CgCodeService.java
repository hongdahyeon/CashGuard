package hong.CashGuard.domain.code.service;

import hong.CashGuard.domain.code.domain.CgCode;
import hong.CashGuard.domain.code.domain.CgCodeMapper;
import hong.CashGuard.domain.code.dto.request.CgCodeSave;
import hong.CashGuard.domain.code.dto.response.CgCodeList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * packageName    : hong.CashGuard.domain.code.service
 * fileName       : CgCodeService
 * author         : home
 * date           : 2025-03-31
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-31        home       최초 생성
 */

@Service
@RequiredArgsConstructor
public class CgCodeService {

    private final CgCodeMapper mapper;

    /**
     * @method      saveParentCode
     * @author      home
     * @date        2025-03-31
     * @deacription 상위 코드 저장
    **/
    @Transactional
    public void saveParentCode(CgCodeSave request) {
        mapper.insert(new CgCode(request));
    }

    /**
     * @method      isExistCode
     * @author      home
     * @date        2025-03-31
     * @deacription {code} 중복 체크
     *              > 같은 {code} 값을 갖는 row 있다면 '중복', 없다면 해당 코드 사용 가능
    **/
    @Transactional(readOnly = true)
    public boolean isExistCode(String code) {
        Integer findCodeCnt = mapper.isExistCode(code);
        return findCodeCnt != 1;
    }


    /**
     * @method      findAllChildren
     * @author      home
     * @date        2025-03-31
     * @deacription 부모 코드 값으로 자식 코드 정보 조회
    **/
    @Transactional(readOnly = true)
    public List<CgCodeList> findAllChildren(String upperCode) {
        return mapper.getAllChildren(upperCode);
    }
}