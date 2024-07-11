package practice.hhplusecommerce.app.service.order;

import practice.hhplusecommerce.app.domain.order.Order;

public interface OrderRepository {

  Order save(Order order);
}
