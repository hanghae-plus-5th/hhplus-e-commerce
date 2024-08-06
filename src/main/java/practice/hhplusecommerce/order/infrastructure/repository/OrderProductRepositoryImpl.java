package practice.hhplusecommerce.order.infrastructure.repository;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.order.business.entity.OrderProduct;
import practice.hhplusecommerce.order.business.repository.OrderProductRepository;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepository {

  private final OrderProductJpaRepository orderProductJpaRepository;

  @Override
  public OrderProduct save(OrderProduct orderProduct) {
    return orderProductJpaRepository.save(orderProduct);
  }

  @Override
  public List<Tuple> getPopularProduct(LocalDateTime now, LocalDateTime minusDays3) {
    return orderProductJpaRepository.getPopularProduct(now, minusDays3);
  }

  @Override
  public List<OrderProduct> findAllByOrderId(Long orderId) {
    return orderProductJpaRepository.findALlByOrderId(orderId);
  }
}
