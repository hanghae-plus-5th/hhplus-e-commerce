package practice.hhplusecommerce.app.order.infrastructure;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.order.business.entity.OrderProduct;
import practice.hhplusecommerce.app.order.business.repository.OrderProductRepository;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepository {

  private final OrderProductJpaRepository orderProductJpaRepository;

  @Override
  public OrderProduct save(OrderProduct orderProduct) {
    return orderProductJpaRepository.save(orderProduct);
  }

  @Override
  public List<Tuple> getTop5ProductsLast3Days(LocalDateTime now, LocalDateTime minusDays3) {
    return orderProductJpaRepository.findTop5ProductsLast3Days(now, minusDays3);
  }

  @Override
  public List<OrderProduct> findAllByOrderId(Long orderId) {
    return orderProductJpaRepository.findALlByOrderId(orderId);
  }
}
