package practice.hhplusecommerce.app.entity.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

}
