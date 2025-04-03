package hong.CashGuard.domain.code;

import lombok.Getter;

/**
 * packageName    : hong.CashGuard.domain.code
 * fileName       : FileSaved
 * author         : home
 * date           : 2025-04-03
 * description    : 첨부파일 관련 Enum
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        home       최초 생성
 */

@Getter
public enum FileSaved {

    SAVED("saved"),
    DELETE("delete"),
    TEMP("temp");

    public String summary;

    FileSaved(String summary) {
        this.summary = summary;
    }
}
