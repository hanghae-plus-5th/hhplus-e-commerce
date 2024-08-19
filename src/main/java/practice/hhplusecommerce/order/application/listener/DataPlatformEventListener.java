package practice.hhplusecommerce.order.application.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import practice.hhplusecommerce.order.business.event.DataPlatformEvent;
import practice.hhplusecommerce.order.business.publisher.OrderMessagePublisher;
import practice.hhplusecommerce.order.infrastructure.dataPlatform.DataPlatform;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataPlatformEventListener {

  private final DataPlatform dataPlatform;
  private final OrderMessagePublisher orderMessagePublisher;

  @Async
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handleSendDataPlatformEvent(DataPlatformEvent dataPlatformEvent) {
    orderMessagePublisher.publish(dataPlatformEvent);
  }
}
