package practice.hhplusecommerce.order.infrastructure.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.order.business.entity.Order;
import practice.hhplusecommerce.order.business.repository.OrderRepository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

  private final OrderJpaRepository orderJpaRepository;

  @Override
  public Order save(Order order) {
    return orderJpaRepository.save(order);
  }

  @Override
  public Optional<Order> findById(Long id) {
    return orderJpaRepository.findById(id);
  }
}
