package hong.CashGuard.global.config;

import com.google.cloud.vision.v1.ImageAnnotatorClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * packageName    : hong.CashGuard.global.config
 * fileName       : GoogleVisionConfig
 * author         : work
 * date           : 2025-04-07
 * description    : Google Vision API 호출을 위한 빈 등록
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-07        work       최초 생성
 */
@Configuration
public class GoogleVisionConfig {

    @Bean
    public ImageAnnotatorClient imageAnnotatorClient() throws IOException {
        return ImageAnnotatorClient.create();
    }
}
