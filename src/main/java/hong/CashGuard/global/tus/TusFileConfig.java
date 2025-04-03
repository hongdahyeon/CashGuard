package hong.CashGuard.global.tus;

import jakarta.annotation.PostConstruct;
import me.desair.tus.server.TusFileUploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * packageName    : hong.CashGuard.global.tus
 * fileName       : TusFileConfig
 * author         : work
 * date           : 2025-04-03
 * description    : Tus 프로토콜 기반의 파일 업로드를 관리하는 설정 파일
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-04-03        work       최초 생성
 */

@Configuration
public class TusFileConfig {

    @Value("${hong.tus.file.root}")
    private String tusStoragePath;

    @Value("${hong.tus.file.expiration}")
    private Long expiration;

    @Value("${hong.tus.file.uri}")
    private String tusURI;

    /**
     * @method      exit
     * @author      work
     * @date        2025-04-03
     * @deacription 서버 시작 시점에 기존 업로드된 파일 정리
     *              => 미완료된 파일을 정리
    **/
    @PostConstruct
    public void exit() throws IOException{
        tus().cleanup();
    }

    @Bean
    public TusFileUploadService tus() {
        return new TusFileUploadService()
                .withStoragePath(tusStoragePath)
                .withDownloadFeature()                  // 다운로드 기능 활성화
                .withUploadExpirationPeriod(expiration) // 업로드 세션의 유효 기간
                .withUploadUri(tusURI);                 // Tus 업로드 요청을 처리한 엔드포인트
    }
}
