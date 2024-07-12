package practice.hhplusecommerce.app.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.user.User;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public Order createOrder(Integer totalProductPrice, User user) {
    Order order = new Order(null, totalProductPrice, user);
    return orderRepository.save(order);
  }
}
