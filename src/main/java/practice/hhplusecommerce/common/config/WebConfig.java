package practice.hhplusecommerce.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import practice.hhplusecommerce.filter.RequestResponseLoggingFilter;

@Configuration
public class WebConfig {

  @Bean
  public FilterRegistrationBean<RequestResponseLoggingFilter> filterFilterRegistrationBean() {
    FilterRegistrationBean<RequestResponseLoggingFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>(new RequestResponseLoggingFilter());
    filterFilterRegistrationBean.setOrder(1);
    return filterFilterRegistrationBean;
  }

}
