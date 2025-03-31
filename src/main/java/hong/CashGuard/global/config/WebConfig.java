package hong.CashGuard.global.config;

import hong.CashGuard.global.interceptor.LoggingInterceptor;
import hong.CashGuard.global.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

import java.util.concurrent.TimeUnit;

/**
 * packageName    : hong.CashGuard.global.config
 * fileName       : WebConfig
 * author         : work
 * date           : 2025-03-27
 * description    : Spring 설정 파일
 *                  > 빈 등록 및 정적 리소스에 대한 설정
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public LoggingInterceptor loggingInterceptor(){
        return new LoggingInterceptor();
    }

    @Bean
    public UserInterceptor userInterceptor() {
        return new UserInterceptor();
    }

    /**
     * @method      addResourceHandlers
     * @author      work
     * @date        2025-03-28
     * @deacription 정적 리소스에 대한 설정
     *              * { addResourceHandler } : "/asstes/**"로 시작하는 요청을 처리하는 핸들러 등록
     *              * { addResourceLocations } : "/src/main/resources/static/assets/**" 아래의 파일을 정적 리소스로 제공
     *              * { setCacheControl } : 캐시 유지 : 1년(365일)
     *              * { resourceChain } : 리소스 최적화
     *              * { addResolver } : 파일 변경 감지를 위한 버전 관리 (파일이 변경되면 브라우저가 새롭게 파일을 가져오도록 설정)
    **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*registry
                .addResourceHandler(ckImgPattern, summerNotePattern)
                .addResourceLocations("file:///" + ckImagePath, "file:///" + summerNoteImagePath);*/

        registry
            .addResourceHandler("/assets/**")
            .addResourceLocations("classpath:/static/assets/")
            .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
            .resourceChain(true) //
            .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    /**
     * @method      addInterceptors
     * @author      work
     * @date        2025-03-28
     * @deacription 인터셉터 설정 -> "정적 파일"과 "/error"경로에 대해서는 제외
    **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor()).excludePathPatterns("/assets/**/*");
        registry.addInterceptor(userInterceptor()).excludePathPatterns("/assets/**/*", "/login", "/loginProc", "/logout", "/error/**/*");
    }
}
