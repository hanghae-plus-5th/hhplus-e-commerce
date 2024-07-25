package practice.hhplusecommerce.common.handler;

import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalHandler {

  @Transactional
  public <T> T runWithTransaction(Supplier<T> supplier) {
    return supplier.get();
  }

  @Transactional
  public void runWithTransaction(Runnable runnable) {
     runnable.run();
  }
}
