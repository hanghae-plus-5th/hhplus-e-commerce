package practice.hhplusecommerce.app.service.order;


import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.order.OrderProduct;
import practice.hhplusecommerce.app.domain.product.Product;


@Service
@RequiredArgsConstructor
public class OrderProductService {

  private final OrderProductRepository orderProductRepository;

  public List<OrderProduct> createOrderProduct(List<Product> productList, Order order) {
    return null;
  }

  public List<Tuple> getTop5ProductsLast3Days() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime minusDays3 = now.minusDays(3);
    return orderProductRepository.getTop5ProductsLast3Days(now, minusDays3);
  }
}
