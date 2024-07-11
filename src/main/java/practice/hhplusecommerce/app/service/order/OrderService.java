package practice.hhplusecommerce.app.service.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.order.OrderProduct;

@Service
@RequiredArgsConstructor
public class OrderService {

  public Order createOrder(Order order) {
    return null;
  }

  public List<Long> getTop5ProductIdsLast3Days() {
    return null;
  }
}
