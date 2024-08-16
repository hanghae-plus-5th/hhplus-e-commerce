package practice.hhplusecommerce.order.presentation.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.order.business.event.DataPlatformEvent;
import practice.hhplusecommerce.outbox.business.entity.Outbox;
import practice.hhplusecommerce.outbox.business.repository.OutboxRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderScheduler {

  private final OutboxRepository outboxRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Scheduled(fixedDelay = 10000)
  @Transactional
  public void retryDataPlatformEvent() throws JsonProcessingException {
    LocalDateTime localDateTimeOf5MinutesAgo = LocalDateTime.now().minusMinutes(5);
    List<Outbox> outboxList = outboxRepository.findAllByFailEvent(localDateTimeOf5MinutesAgo);

    ObjectMapper objectMapper = new ObjectMapper();
    for (Outbox outbox : outboxList) {
      outbox.retry();
      DataPlatformEvent dataPlatformEvent = objectMapper.readValue(outbox.getMessage(), DataPlatformEvent.class);
      applicationEventPublisher.publishEvent(dataPlatformEvent);
    }
  }

}
