package practice.hhplusecommerce.order.presentation.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import practice.hhplusecommerce.order.business.event.DataPlatformEvent;
import practice.hhplusecommerce.outbox.business.service.OutboxService;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
public class OutboxConsumer {

  private final OutboxService outboxService;
  private ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(topics = "order_topic", containerFactory = "dataPlatformOutBoxListenerFactory", groupId = "outbox_group")
  private void dataPlatformOutboxListener(ConsumerRecord<String, String> consumerRecord) {
    try {
      DataPlatformEvent dataPlatformEvent = objectMapper.readValue(consumerRecord.value(), DataPlatformEvent.class);
      log.info("message {}", dataPlatformEvent.getOutboxId());
      outboxService.updateState(dataPlatformEvent.getOutboxId());
    } catch (JsonProcessingException jpe) {
      log.error("dataPlatformOutboxListener error : {}", jpe.getMessage());
      jpe.printStackTrace();
    }
  }
}
