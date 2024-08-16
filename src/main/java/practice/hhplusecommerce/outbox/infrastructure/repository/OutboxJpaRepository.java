package practice.hhplusecommerce.outbox.infrastructure.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import practice.hhplusecommerce.outbox.business.entity.Outbox;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

  @Query("SELECT o FROM Outbox o WHERE o.state = 'INIT' AND o.createdAt < :localDateTime AND o.updatedAt < :localDateTime")
  List<Outbox> findAllByFailEvent(LocalDateTime localDateTime);

  @Modifying
  @Query("DELETE FROM Outbox  WHERE createdAt < :localDateTime AND updatedAt < :localDateTime")
  void deleteAllOutboxStaleData(LocalDateTime localDateTime);
}
