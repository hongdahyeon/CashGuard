package hong.CashGuard.global.core;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * packageName    : hong.CashGuard.global.core
 * fileName       : CGException
 * author         : work
 * date           : 2025-03-28
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-28        work       최초 생성
 */

@Getter
public class CGException extends RuntimeException {

    private final HttpStatus status;

    public CGException() {
        this("내부 서부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public CGException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public CGException(HttpStatus status) {
        this.status = status;
    }

    public CGException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
