package practice.hhplusecommerce.outbox.business.repository;

import java.util.Optional;
import practice.hhplusecommerce.outbox.business.entity.Outbox;

public interface OutboxRepository {

  Outbox save(Outbox outbox);

  Optional<Outbox> findById(Long outboxId);
}
