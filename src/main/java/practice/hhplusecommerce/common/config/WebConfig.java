package practice.hhplusecommerce.common.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import practice.hhplusecommerce.common.jwt.JwtTokenProvider;
import practice.hhplusecommerce.filter.RequestResponseLoggingFilter;
import practice.hhplusecommerce.iterceptor.JwtTokenInterceptor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final JwtTokenInterceptor jwtTokenInterceptor;

  private final String[] JWT_INTERCEPTOR_URI = {
      "/swagger-ui/index.html",
      "/swagger-ui/swagger-ui-bundle.js",
      "/swagger-ui/swagger-initializer.js",
      "/api-docs/swagger-config",
      "/api-docs",
      "/api/user/login",
      "/api/user/join",
  };

  @Bean
  public FilterRegistrationBean<RequestResponseLoggingFilter> filterFilterRegistrationBean() {
    FilterRegistrationBean<RequestResponseLoggingFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>(new RequestResponseLoggingFilter());
    filterFilterRegistrationBean.setOrder(1);
    return filterFilterRegistrationBean;
  }

  //토큰을 받아야 하는 서비스 설정
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(jwtTokenInterceptor) //로그인이 필요한 서비스 요청시 Interceptor가 그 요청을 가로챔
        .excludePathPatterns(JWT_INTERCEPTOR_URI);
  }
}
