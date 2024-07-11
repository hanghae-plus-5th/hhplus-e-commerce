package practice.hhplusecommerce.app.domain.order;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends JpaRepository<Order, Long> {

  @Query("SELECT O FROM Order O WHERE O.createdAt <= :now  AND  O.createdAt >= :minusDays3")
  List<Order> getOrderIdListLast3Days(LocalDateTime now, LocalDateTime minusDays3);
}
