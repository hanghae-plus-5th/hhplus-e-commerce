package practice.hhplusecommerce.app.service.order;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto.OrderProductCreate;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.order.OrderProduct;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.user.User;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderProductRepository orderProductRepository;

  public Order createOrder(
      Integer totalProductPrice,
      User user,
      List<Product> productList,
      List<OrderProductCreate> orderProductCreateList
  ) {

    Order order = orderRepository.save(new Order(null, totalProductPrice, user));

    for (Product product : productList) {
      for (OrderProductCreate orderProductCreate : orderProductCreateList) {
        if (product.getId().equals(orderProductCreate.getId())) {
          order.addOrderProduct(new OrderProduct(
              null,
              product.getName(),
              product.getPrice(),
              orderProductCreate.getQuantity(),
              order,
              product
          ));
          break;
        }
      }
    }
    return order;
  }

  @Transactional
  public List<Tuple> getTop5ProductsLast3Days() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime minusDays3 = now.minusDays(3);
    return orderProductRepository.getTop5ProductsLast3Days(now, minusDays3);
  }
}
