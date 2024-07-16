package practice.hhplusecommerce.order.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.order.business.entity.Order;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

  @Query("SELECT O FROM Order O WHERE O.createdAt <= :now  AND  O.createdAt >= :minusDays3")
  List<Order> getOrderIdListLast3Days(LocalDateTime now, LocalDateTime minusDays3);
}
