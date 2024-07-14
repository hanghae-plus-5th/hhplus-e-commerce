package practice.hhplusecommerce.app.service.order;


import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto.OrderProductCreate;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.order.OrderProduct;
import practice.hhplusecommerce.app.domain.product.Product;


@Service
@RequiredArgsConstructor
public class OrderProductService {

  private final OrderProductRepository orderProductRepository;

  public List<OrderProduct> createOrderProduct(List<Product> productList, List<OrderProductCreate> orderProductCreateList, Order order) {
    List<OrderProduct> orderProductList = new ArrayList<>();

    for (Product product : productList) {
      for (OrderProductCreate orderProductCreate : orderProductCreateList) {
        if (product.getId().equals(orderProductCreate.getId())) {
          orderProductList.add(
              orderProductRepository.save(
                  new OrderProduct(
                      null,
                      product.getName(),
                      product.getPrice(),
                      orderProductCreate.getQuantity(),
                      order,
                      product
                  )
              )
          );
          break;
        }
      }
    }

    return orderProductList;
  }

  public List<Tuple> getTop5ProductsLast3Days() {
    LocalDateTime now = LocalDateTime.now();
    LocalDateTime minusDays3 = now.minusDays(3);
    return orderProductRepository.getTop5ProductsLast3Days(now, minusDays3);
  }
}
