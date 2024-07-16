package practice.hhplusecommerce.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@WebFilter
@Log4j2
public class RequestResponseLoggingFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) {
    log.info("RequestResponseLoggingFilter initialized");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

    try {
      log.info("Request Method: {}", requestWrapper.getMethod());
      log.info("Request URI: {}", requestWrapper.getRequestURI());

      requestWrapper.getHeaderNames().asIterator().forEachRemaining(header ->
          log.info("Request Header {}: {}", header, requestWrapper.getHeader(header))
      );

      requestWrapper.getParameterNames().asIterator().forEachRemaining(param ->
          log.info("Request Parameter {}: {}", param, requestWrapper.getParameter(param))
      );

      long start = System.currentTimeMillis();
      filterChain.doFilter(requestWrapper, responseWrapper);
      long end = System.currentTimeMillis();

      String requestBody = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
      log.info("Request Body: {}", requestBody);

      String responseBody = new String(responseWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
      log.info("Response Body: {}", responseBody);

      responseWrapper.copyBodyToResponse();

      log.info("Response Status: {}", HttpStatus.valueOf(responseWrapper.getStatus()));
      log.info("Response Time: {} ms", (end - start));
    } catch (Exception e) {
      log.error("Failed to log request/response", e);
    }
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }
}
