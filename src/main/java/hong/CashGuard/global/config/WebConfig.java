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
 * description    :
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /*registry
                .addResourceHandler(ckImgPattern, summerNotePattern)
                .addResourceLocations("file:///" + ckImagePath, "file:///" + summerNoteImagePath);*/

        registry
                .addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/static/assets/")
                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(true)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/**"));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor()).excludePathPatterns("/assets/**", "/error");
        registry.addInterceptor(userInterceptor()).excludePathPatterns("/assets/**", "/error");
    }
}
