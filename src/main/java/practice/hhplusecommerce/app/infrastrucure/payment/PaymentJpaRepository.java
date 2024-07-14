package practice.hhplusecommerce.app.infrastrucure.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.domain.payment.Payment;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

}
