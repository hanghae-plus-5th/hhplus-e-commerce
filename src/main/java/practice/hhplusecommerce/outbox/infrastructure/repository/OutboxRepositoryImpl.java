package practice.hhplusecommerce.outbox.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.outbox.business.entity.Outbox;
import practice.hhplusecommerce.outbox.business.repository.OutboxRepository;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {

  private final OutboxJpaRepository outboxJpaRepository;

  @Override
  public Outbox save(Outbox outbox) {
    return outboxJpaRepository.save(outbox);
  }

  @Override
  public Optional<Outbox> findById(Long outboxId) {
    return outboxJpaRepository.findById(outboxId);
  }

  @Override
  public List<Outbox> findAllByFailEvent(LocalDateTime localDateTime) {
    return outboxJpaRepository.findAllByFailEvent(localDateTime);
  }

  @Override
  public void deleteAllOutboxStaleData(LocalDateTime localDateTime) {
    outboxJpaRepository.deleteAllOutboxStaleData(localDateTime);
  }
}
