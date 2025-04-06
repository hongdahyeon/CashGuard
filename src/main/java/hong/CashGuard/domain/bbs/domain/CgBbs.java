package hong.CashGuard.domain.bbs.domain;

import hong.CashGuard.domain.bbs.dto.request.CgBbsChange;
import hong.CashGuard.domain.bbs.dto.request.CgBbsSave;
import hong.CashGuard.global.bean.audit.AuditBean;
import hong.CashGuard.global.util.StringUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * packageName    : hong.CashGuard.domain.bbs.domain
 * fileName       : CgBbs
 * author         : work
 * date           : 2025-04-04
 * description    : 게시판 엔티티
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CgBbs extends AuditBean {

    private Long uid;
    private String bbsTpCd;     // [BbsTpCd]
    private String bbsNm;
    private String useAt;
    private String deleteAt;

    /**
     * @method      CgBbs 생성자 1
     * @author      work
     * @date        2025-04-04
     * @deacription 게시판 저장용 생성자
    **/
    public CgBbs(CgBbsSave request) {
        this.bbsTpCd = request.getBbsTpCd();
        this.bbsNm = request.getBbsNm();
        this.useAt = request.getUseAt();
    }

    /**
     * @method      CgBbs 생성자 2
     * @author      work
     * @date        2025-04-04
     * @deacription 게시판 수정용 생성자
    **/
    public CgBbs changeBbs(Long uid, CgBbsChange request) {
        this.uid = uid;
        this.bbsTpCd = StringUtil.getOrDefault(request.getBbsTpCd(), this.bbsTpCd);
        this.bbsNm = StringUtil.getOrDefault(request.getBbsNm(), this.bbsNm);
        this.useAt = StringUtil.getOrDefault(request.getUseAt(), this.useAt);
        return this;
    }

    /**
     * @method      CgBbs 생성자 3
     * @author      work
     * @date        2025-04-04
     * @deacription 게시판 단건 삭제용 생성자
    **/
    public CgBbs(Long uid) {
        this.uid = uid;
    }
}
