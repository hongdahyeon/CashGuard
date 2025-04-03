package hong.CashGuard.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.domain.code
 * fileName       : EmailSendReason
 * author         : work
 * date           : 2025-04-02
 * description    : 이메일 발송 이유 Enum Code
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 * 2025-04-03        work       {reason} 값이 valid 한 값인지 체크 메소드 추가
 */

@Getter
public enum EmailSendReason {

    INVITE_GROUP("그룹 초대");

    private String description;

    EmailSendReason(String description) {
        this.description = description;
    }

    public static boolean isValidReason(String reason) {
        for( EmailSendReason sendReason : EmailSendReason.values() ) {
            if(sendReason.name().equals(reason)) {
                return true;
            }
        }
        return  false;
    }
}
