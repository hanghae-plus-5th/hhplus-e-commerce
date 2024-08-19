package practice.hhplusecommerce.order.infrastrure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.common.handler.TransactionalHandler;
import practice.hhplusecommerce.order.business.command.OrderCommand;
import practice.hhplusecommerce.order.business.command.OrderCommand.SendDataPlatform;
import practice.hhplusecommerce.order.business.event.DataPlatformEvent;
import practice.hhplusecommerce.order.infrastructure.kafka.OrderMessageProducer;
import practice.hhplusecommerce.order.presentation.consumer.DataPlatformConsumer;
import practice.hhplusecommerce.order.presentation.consumer.OutboxConsumer;
import practice.hhplusecommerce.outbox.business.entity.Outbox;
import practice.hhplusecommerce.outbox.business.entity.enums.OutboxState;
import practice.hhplusecommerce.outbox.business.entity.enums.OutboxType;
import practice.hhplusecommerce.outbox.business.repository.OutboxRepository;
import practice.hhplusecommerce.outbox.business.service.OutboxService;

@SpringBootTest
public class OrderMessageProducerTest {

  @Autowired
  private OrderMessageProducer orderMessageProducer;

  @Autowired
  private OutboxService outboxService;

  @Autowired
  private DataPlatformConsumer dataPlatformConsumer;

  @Autowired
  private OutboxRepository outboxRepository;

  @Autowired
  private OutboxConsumer outboxConsumer;

  @Autowired
  private TransactionalHandler transactionalHandler;

  @Test
  @DisplayName("주문플랫폼 카프카 메세지 전송과 발행 정상 작동 확인")
  public void orderPlatformPublish() throws InterruptedException {
    //given
    Long testId = 1L;
    Integer testPrice = 1500;

    Long outBoxId = transactionalHandler.runWithTransaction(() -> {
      Outbox outbox = outboxService.create(OutboxType.DATA_PLATFORM);
      DataPlatformEvent dataPlatformEvent = new DataPlatformEvent(new SendDataPlatform(testId, testId, testPrice, outbox.getId()));
      ObjectMapper objectMapper = new ObjectMapper();
      try {
        outbox.saveMessage(objectMapper.writeValueAsString(dataPlatformEvent));
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
      orderMessageProducer.publish(dataPlatformEvent);
      return outbox.getId();
    });

    //when
    DataPlatformEvent whenDataPlatformEvent = dataPlatformConsumer.getRecords().poll(10, TimeUnit.SECONDS);

    Outbox whenOutbox = outboxRepository.findById(outBoxId).get();

    //then
    assertNotNull(whenDataPlatformEvent);
    assertEquals(whenDataPlatformEvent.getOrderId(), testId);
    assertEquals(whenDataPlatformEvent.getOrderTotalPrice(), testPrice);
    assertEquals(whenDataPlatformEvent.getUserId(), testId);
    assertEquals(whenDataPlatformEvent.getUserId(), testId);

    assertEquals(whenOutbox.getState(), OutboxState.PUBLISH);
  }
}
