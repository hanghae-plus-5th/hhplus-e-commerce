package practice.hhplusecommerce.app.service.order;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.entity.order.Order;
import practice.hhplusecommerce.app.entity.order.OrderProduct;
import practice.hhplusecommerce.app.entity.product.Product;

@Service
@RequiredArgsConstructor
public class OrderProductService {

  public List<OrderProduct> createOrderProduct(List<Product> productList, Order order) {
    return null;
  }
}
