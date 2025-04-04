package hong.CashGuard.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.domain.code
 * fileName       : BbsTpCd
 * author         : work
 * date           : 2025-04-04
 * description    : 게시판 유형 코드 Enum
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-04        work       최초 생성
 */

@Getter
public enum BbsTpCd {

    NOTICE("공지사항"),
    BOARD("자유 게시판"),
    QNA("질의 응답"),
    FAQ("faq");

    private String name;

    BbsTpCd(String name){
        this.name = name;
    }

    public static boolean isValidBbsTpCd(String code) {
        for( BbsTpCd bbsTpCd : BbsTpCd.values() ) {
            if(bbsTpCd.name().equals(code)) {
                return true;
            }
        }
        return  false;
    }
}
