package practice.hhplusecommerce.app.service.order;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import practice.hhplusecommerce.app.domain.order.OrderProduct;

public interface OrderProductRepository {

  OrderProduct save(OrderProduct orderProduct);

  List<Tuple> getTop5ProductsLast3Days(LocalDateTime now, LocalDateTime minusDays3);

  List<OrderProduct> findAllByOrderId(Long id);
}
