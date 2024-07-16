package practice.hhplusecommerce.app.payment.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.payment.entity.Payment;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

}
