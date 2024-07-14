package practice.hhplusecommerce.app.service.order;

import java.time.LocalDateTime;
import java.util.List;
import practice.hhplusecommerce.app.domain.order.Order;

public interface OrderRepository {

  Order save(Order order);

  List<Order> getOrderIdListLast3Days(LocalDateTime now, LocalDateTime minusDays3);
}
