package hong.CashGuard.global.validator;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * packageName    : hong.CashGuard.global.validator
 * fileName       : ValidationErrorProcessor
 * author         : work
 * date           : 2025-04-02
 * description    : @Valid 오류에 대한 핸들러 프로세서
 *                  > 해당 영역에 들어와서 핸들린 된 다음 리턴된다
 *                  > 아래와 같이 객체, 배열 형식을 유지하여 리턴한다
 *                  (ex) code merge
 *                    parent: {
 *                        code: "공백일 수 없습니다.",
 *                        useAt: "값은 'Y' 또는 'N'이어야 합니다."
 *                    },
 *                    addChild: [
 *                       {
 *                          code: "공백일 수 없습니다.",
 *                       },
 *                       {
 *                          code: "공백일 수 없습니다.",
 *                          useAt: "값은 'Y' 또는 'N'이어야 합니다."
 *                       }
 *                    ]
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */
public class ValidationErrorProcessor {

    public static Map<String, Object> processErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new LinkedHashMap<>();

        for( FieldError error : ex.getBindingResult().getFieldErrors() ) {
            String field = error.getField();
            String errorMessage = error.getDefaultMessage();

            // 배열 형식 혹은 객체 필드 이름을 갖는 경우
            if( field.contains(".") ) {
                String[] parts = field.split("\\.");
                Map<String, Object> currentMap = errors;

                for( int i = 0; i < parts.length-1; i++ ) {
                    String part = parts[i];

                    // 요소 값이 배열인 경우
                    // (ex) addChild[0], addChild[1] ...
                    if( part.contains("[") ) {

                        String key = part.substring(0, part.indexOf("["));                                          // (ex) key = addChild
                        int index = Integer.parseInt(part.substring(part.indexOf("[") + 1, part.indexOf("]")));     // (ex) index = 0, 1, 2, ...

                        if ( !currentMap.containsKey(key) ) {
                            currentMap.put(key, new ArrayList<Map<String, Object>>());
                        }

                        List<Map<String, Object>> arrayList = (List<Map<String, Object>>) currentMap.get(key);

                        while (arrayList.size() <= index) {
                            arrayList.add(new LinkedHashMap<String, Object>());
                        }
                        currentMap = arrayList.get(index);

                    } else {

                        // 요소 값이 배열이 아닌 경우
                        if( !currentMap.containsKey(part) ) {
                            currentMap.put(part, new LinkedHashMap<String, Object>());
                        }

                        currentMap = (Map<String, Object>) currentMap.get(part);
                    }
                }

                // 빈 객체가 아닌 경우에만 담기
                currentMap.put(parts[parts.length - 1], errorMessage);

            } else {

                // 단일 필드인 경우
                if (!errors.containsKey(field)) {
                    errors.put(field, errorMessage);
                }

            }
        }

        // 배열 형태로 담긴 에러의 경우 > 빈 value 값은 제거
        /* (ex) code merge
         *      addChild: [
         *          {}, // 이런 값을 제거
         *          {
         *              "code: "공백일 수 없습니다."
         *          }
         *      ]
         */
        cleanEmptyObjects(errors);

        return errors;
    }

    public static void cleanEmptyObjects(Map<String, Object> errors) {
        errors.forEach((key, value) -> {
            if(value instanceof  List) {
                List<Map<String, Object>> list = (List<Map<String, Object>>) value;
                list.removeIf(Map::isEmpty);
                if (list.isEmpty()) errors.remove(key);
            }
        });
    }
}
