package practice.hhplusecommerce.app.infrastrucure.order;

import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.domain.order.OrderProduct;
import practice.hhplusecommerce.app.service.order.OrderProductRepository;

@Repository
public class OrderProductRepositoryImpl implements OrderProductRepository {

  @Override
  public OrderProduct save(OrderProduct orderProduct) {
    return null;
  }
}
