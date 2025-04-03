package hong.CashGuard.global.util;

import hong.CashGuard.domain.code.EmailSendReason;
import hong.CashGuard.global.exception.CGException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


/**
 * packageName    : hong.CashGuard.global.util
 * fileName       : AESUtil
 * author         : work
 * date           : 2025-04-02
 * description    : AES-256 암호화를 이용하여 uid, code(reasonCode) 값을 암호화한다.
 *                  * SALT, IV 값을 랜덤하게 생성하여 보안성을 높이고 -> Base64 인코딩하여 문자열로 변환
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-02        work       최초 생성
 */

@Slf4j
@Component
public class AESUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static String SECRET_KEY;
    public AESUtil(@Value("${hong.aes.secret-key}") String secretKeyValue) {
        SECRET_KEY = secretKeyValue;
    }

    /**
     * @method      encrypt
     * @author      work
     * @date        2025-04-03
     * @deacription AES 암호화 (IV + 암호문을 Base64 URL-safe 형태로 반환)
    **/
    public static String encrypt(Long uid, String code) {
        if (!EmailSendReason.isValidReason(code)) {
            throw new CGException("유효하지 않은 이메일 발송 이유 코드입니다.", HttpStatus.BAD_REQUEST);
        }

        String rawData = String.format("uid=%d&code=%s", uid, code);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            // (1) 16바이트 랜덤 IV 생성
            byte[] ivBytes = new byte[16];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(ivBytes);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            // (2) 암호화 수행
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), iv);
            byte[] encryptedBytes = cipher.doFinal(rawData.getBytes(StandardCharsets.UTF_8));

            // (3) IV + 암호문 결합 (복호화를 위해 IV를 앞에 붙임)
            byte[] combined = new byte[ivBytes.length + encryptedBytes.length];
            System.arraycopy(ivBytes, 0, combined, 0, ivBytes.length);
            System.arraycopy(encryptedBytes, 0, combined, ivBytes.length, encryptedBytes.length);

            // (4) Base64 URL-safe 변환 (URL에서 문제되는 `+`, `/`, `=` 제거)
            return Base64.getUrlEncoder().withoutPadding().encodeToString(combined);
        } catch (Exception e) {
            log.error("암호화 실패", e);
            return null;
        }
    }

    /**
     * @method      decrypt
     * @author      work
     * @date        2025-04-03
     * @deacription AES 복호화 (IV를 분리 후 복호화하여 Map<String, Object>로 반환)
    **/
    public static Map<String, Object> decrypt(String encryptedText) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // (1) Base64 URL-safe 디코딩
            byte[] combined = Base64.getUrlDecoder().decode(encryptedText);

            // (2) IV와 암호문 분리
            byte[] ivBytes = new byte[16];
            byte[] encryptedBytes = new byte[combined.length - 16];
            System.arraycopy(combined, 0, ivBytes, 0, 16);
            System.arraycopy(combined, 16, encryptedBytes, 0, encryptedBytes.length);
            IvParameterSpec iv = new IvParameterSpec(ivBytes);

            // (3) 복호화 수행
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), iv);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            String decryptedText = new String(decryptedBytes, StandardCharsets.UTF_8);

            // (4) "uid=123&code=ABC" 형태의 문자열을 Map으로 변환
            String[] parts = decryptedText.split("&");
            resultMap.put("uid", Long.parseLong(parts[0].split("=")[1]));
            resultMap.put("code", parts[1].split("=")[1]);

        } catch (Exception e) {
            log.error("decrypt 문제 발생", e);
            resultMap.put("uid", null);
            resultMap.put("code", null);
        }

        return resultMap;
    }

    /**
     * @method      getSecretKey
     * @author      work
     * @date        2025-04-03
     * @deacription AES SecretKey 반환 (16바이트 고정)
    **/
    public static SecretKeySpec getSecretKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, 0, 16, "AES"); // 16바이트 키 사용
    }
}
