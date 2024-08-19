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
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import practice.hhplusecommerce.order.application.OrderFacade;
import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto;
import practice.hhplusecommerce.order.business.event.DataPlatformEvent;

@Slf4j
@Component
@Getter
@RequiredArgsConstructor
public class DataPlatformConsumer {

  private ObjectMapper objectMapper = new ObjectMapper();
  private final BlockingQueue<DataPlatformEvent> records = new LinkedBlockingQueue<>();
  private final OrderFacade orderFacade;

  @KafkaListener(topics = "order_topic", containerFactory = "dataPlatformListenerFactory", groupId = "data_platform_group")
  private void dataPlatformListener(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
    try {
      DataPlatformEvent dataPlatformEvent = objectMapper.readValue(consumerRecord.value(), DataPlatformEvent.class);
      log.info("dataPlatformOutboxListener message : {}", dataPlatformEvent.toString());
      records.add(dataPlatformEvent); // 메시지를 BlockingQueue에 저장
      orderFacade.sendDataPlatform(new OrderFacadeRequestDto.SendDataPlatform(dataPlatformEvent.getOrderId(), dataPlatformEvent.getUserId(), dataPlatformEvent.getOrderTotalPrice()));
    } catch (JsonProcessingException jpe) {
      log.error("dataPlatformOutboxListener error : {}", jpe.getMessage());
      jpe.printStackTrace();
    }
  }
}
