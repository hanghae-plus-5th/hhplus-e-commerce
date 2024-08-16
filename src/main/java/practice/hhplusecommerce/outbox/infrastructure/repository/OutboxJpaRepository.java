package practice.hhplusecommerce.outbox.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.hhplusecommerce.outbox.business.entity.Outbox;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

}
