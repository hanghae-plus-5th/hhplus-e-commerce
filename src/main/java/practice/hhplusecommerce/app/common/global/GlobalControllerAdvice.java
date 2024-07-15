package practice.hhplusecommerce.app.common.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

  @ExceptionHandler(Exception.class)
  public void handleException(Exception ex) throws Exception {
    ex.printStackTrace();
    throw ex;
  }
}
