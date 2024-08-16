package practice.hhplusecommerce.outbox.business.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.common.exception.NotFoundException;
import practice.hhplusecommerce.outbox.business.entity.Outbox;
import practice.hhplusecommerce.outbox.business.entity.enums.OutboxType;
import practice.hhplusecommerce.outbox.business.repository.OutboxRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxService {

  private final OutboxRepository outboxRepository;

  @Transactional
  public Outbox create(OutboxType type) {
    return outboxRepository.save(new Outbox(type));
  }

  @Transactional
  public void updateState(Long outboxId) {
    Outbox outbox = outboxRepository.findById(outboxId).orElseThrow(() -> {
      log.error("outbox get Error id :{}", outboxId);
      return new NotFoundException("아웃박스", true);
    });

    outbox.publish();
  }
}
