package hong.CashGuard.global.handler.advice;

import hong.CashGuard.global.exception.CGException;
import hong.CashGuard.global.validator.ValidationErrorProcessor;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.global.handler
 * fileName       : GlobalExceptionHandler
 * author         : work
 * date           : 2025-03-28
 * description    : ** API에서 발생하는 예외를 공통적으로 처리
 *                  > RESTful API 영역에서 사용되는 핸들러
 *                  > @ResponseBody가 자동으로 적용되어 JSON 응답을 반환한다
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-28        work       최초 생성
 * 2025-03-31        home       valid, param 관련 error handle 추가
 * 2025-04-01        work       CGException handle 추가
 * 2025-04-02        work       handleValidationExceptions 따로 로직 분리
 *                              => 객체 안에 객체, 객체 배열에 대해 처리를 위함
 */

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    /**
     * @method      handleDatabaseError
     * @author      home
     * @date        2025-03-31
     * @deacription DB 에러
    **/
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity handleDatabaseError(DataAccessException ex) {
        Map<String, Object> response = new HashMap<>();
        String message = "쿼리 처리 중 오류가 발생했습니다.";
        if (ex.getClass().equals(DuplicateKeyException.class)) {
            message = "중복된 ID가 존재합니다.";
        } else if (ex.getClass().equals(ConstraintViolationException.class)) {
            message = "제약 조건 위반 오류가 발생했습니다.";
        } else if (ex.getClass().equals(DataIntegrityViolationException.class)) {
            message = "데이터 무결성 오류가 발생했습니다.";
        } else if (ex.getClass().equals(EmptyResultDataAccessException.class)) {
            message = "조회된 데이터가 없습니다.";
        } else if (ex.getClass().equals(IncorrectResultSizeDataAccessException.class)) {
            message = "조회된 데이터의 크기가 예상과 다릅니다.";
        } else {
            message = ex.getMessage();
        }
        response.put("message", message);
        response.put("error", ex.getClass().getSimpleName());
        return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(response);
    }

    /**
     * @method      handleConstraintViolationException
     * @author      home
     * @date        2025-03-31
     * @deacription @RequestParam, @PathVariable 유효성 검사 실패
    **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
                errors.put(violation.getPropertyPath().toString(), violation.getMessage())
        );
        return errors;
    }

    /**
     * @method      handleValidationExceptions
     * @author      home
     * @date        2025-03-31
     * @deacription @Valid, @Validated 에러 
    **/
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ValidationErrorProcessor.processErrors(ex);
    }

    /**
     * @method      cgException
     * @author      work
     * @date        2025-04-02
     * @deacription 기본적인 CgException 에러를 터트렸을때 타게 되는 핸들러
    **/
    @ExceptionHandler(CGException.class)
    public ResponseEntity cgException(CGException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(e.getMessage());
    }
}
