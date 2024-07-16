package practice.hhplusecommerce.order.business.repository;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import practice.hhplusecommerce.order.business.entity.OrderProduct;

public interface OrderProductRepository {

  OrderProduct save(OrderProduct orderProduct);

  List<Tuple> getTop5ProductsLast3Days(LocalDateTime now, LocalDateTime minusDays3);

  List<OrderProduct> findAllByOrderId(Long id);
}
