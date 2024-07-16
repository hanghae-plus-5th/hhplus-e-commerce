package practice.hhplusecommerce.app.order.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.order.business.entity.Order;
import practice.hhplusecommerce.app.order.business.repository.OrderRepository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;

  @Override
  public Order save(Order order) {
    return orderJpaRepository.save(order);
  }
}
