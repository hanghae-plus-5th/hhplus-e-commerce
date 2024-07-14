package practice.hhplusecommerce.app.infrastrucure.order;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.domain.order.OrderProduct;
import practice.hhplusecommerce.app.domain.order.OrderProductJpaRepository;
import practice.hhplusecommerce.app.service.order.OrderProductRepository;

@Repository
@RequiredArgsConstructor
public class OrderProductRepositoryImpl implements OrderProductRepository {

  private final OrderProductJpaRepository productJpaRepository;

  @Override
  public OrderProduct save(OrderProduct orderProduct) {
    return productJpaRepository.save(orderProduct);
  }

  @Override
  public List<Tuple> getTop5ProductsLast3Days(LocalDateTime now, LocalDateTime minusDays3) {
    return productJpaRepository.findTop5ProductsLast3Days(now, minusDays3);
  }

}
