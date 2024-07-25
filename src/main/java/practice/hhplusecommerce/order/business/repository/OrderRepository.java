package practice.hhplusecommerce.order.business.repository;

import java.util.List;
import java.util.Optional;
import practice.hhplusecommerce.order.business.entity.Order;

public interface OrderRepository {

  Order save(Order order);

  Optional<Order> findById(Long id);

  List<Order> findAllByUserId(Long userId);

  void deleteAllInBatch(List<Order> orderList);
}
