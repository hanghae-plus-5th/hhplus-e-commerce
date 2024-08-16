package practice.hhplusecommerce.order.infrastructure.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import practice.hhplusecommerce.order.business.event.DataPlatformEvent;
import practice.hhplusecommerce.order.business.publisher.OrderMessagePublisher;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderMessageProducer implements OrderMessagePublisher {

  private final KafkaTemplate<String, DataPlatformEvent> kafkaTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public void publish(DataPlatformEvent dataPlatformEvent) {
    kafkaTemplate.send("order_topic", dataPlatformEvent);
  }
}
