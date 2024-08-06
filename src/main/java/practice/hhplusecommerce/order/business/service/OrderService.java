package practice.hhplusecommerce.order.business.service;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto.OrderProductCreate;
import practice.hhplusecommerce.order.business.command.OrderCommand;
import practice.hhplusecommerce.order.business.entity.Order;
import practice.hhplusecommerce.order.business.entity.OrderProduct;
import practice.hhplusecommerce.order.business.repository.OrderProductRepository;
import practice.hhplusecommerce.order.business.repository.OrderRepository;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.user.business.entity.User;

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
  @Cacheable(value = "getPopularProduct")
  public List<OrderCommand.PopularProductResponse> getPopularProduct() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime minusDays3 = now.minusDays(3);
    List<Tuple> popularProductList = orderProductRepository.getPopularProduct(now, minusDays3);
    return popularProductList.stream().map(OrderCommand.PopularProductResponse::new).toList();
  }
}
