package practice.hhplusecommerce.app.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

}
