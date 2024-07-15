package practice.hhplusecommerce.app.order.business.repository;

import practice.hhplusecommerce.app.order.business.entity.Order;

public interface OrderRepository {

  Order save(Order order);
}
