package practice.hhplusecommerce.app.infrastrucure.order;

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
}
