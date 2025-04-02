package hong.CashGuard.domain.code.service;

import hong.CashGuard.domain.code.domain.CgCode;
import hong.CashGuard.domain.code.domain.CgCodeMapper;
import hong.CashGuard.domain.code.dto.request.CgCodeMerge;
import hong.CashGuard.domain.code.dto.request.CgCodeObj;
import hong.CashGuard.domain.code.dto.request.CgCodeParam;
import hong.CashGuard.domain.code.dto.request.CgCodeSave;
import hong.CashGuard.domain.code.dto.response.CgCodeList;
import hong.CashGuard.global.bean.Page;
import hong.CashGuard.global.bean.Pageable;
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
 * 2025-04-02        work       부모 코드 수정 + 자식 코드 수정/저장 로직 추가
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


    /**
     * @method      findAllCodePage
     * @author      home
     * @date        2025-03-31
     * @deacription 코드 페이징 조회
    **/
    @Transactional(readOnly = true)
    public Page<CgCodeList> findAllCodePage(CgCodeParam request, Pageable pageable) {
        List<CgCodeList> list = mapper.page(pageable.generateMap(request));
        int count = mapper.count(request);
        return new Page<>(list, count, pageable);
    }

    /**
     * @method      saveAllProcess
     * @author      work
     * @date        2025-04-02
     * @deacription 부모 코드 수정 + 자식 코드 수정/저장
    **/
    @Transactional
    public void saveAllProcess(CgCodeMerge request) {

        // 1. 부모 코드 수정
        String parentCode = request.getParent().getCode();
        CgCode parentView = mapper.view(parentCode);
        mapper.update(parentView.changeCode(parentCode, request.getParent()));

        // 2. 자식 코드 수정
        if( request.getAddChild() != null && !request.getAddChild().isEmpty() ) {
            for (CgCodeObj child : request.getAddChild()) {
                mapper.insert(new CgCode(parentCode, child));
            }
        }

        // 3. 자식 코드 저장
        if( request.getUpdtChild() != null && !request.getUpdtChild().isEmpty() ) {
            for ( CgCodeObj child : request.getUpdtChild() ) {
                String childCode = child.getCode();
                CgCode childView = mapper.view(childCode);
                mapper.update(childView.changeCode(childCode, child));
            }
        }
    }
}