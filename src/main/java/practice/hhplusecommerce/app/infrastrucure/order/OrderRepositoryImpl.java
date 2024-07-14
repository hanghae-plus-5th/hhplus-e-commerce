package practice.hhplusecommerce.app.infrastrucure.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.order.OrderJpaRepository;
import practice.hhplusecommerce.app.service.order.OrderRepository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;

  @Override
  public Order save(Order order) {
    return orderJpaRepository.save(order);
  }

  @Override
  public List<Order> getOrderIdListLast3Days(LocalDateTime now, LocalDateTime minusDays3) {
    return orderJpaRepository.getOrderIdListLast3Days(now, minusDays3);
  }
}
