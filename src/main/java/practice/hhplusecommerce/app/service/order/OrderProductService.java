package practice.hhplusecommerce.app.service.order;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.order.OrderProduct;
import practice.hhplusecommerce.app.domain.product.Product;


@Service
@RequiredArgsConstructor
public class OrderProductService {

  public List<OrderProduct> createOrderProduct(List<Product> productList, Order order) {
    return null;
  }
}
