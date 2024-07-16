package practice.hhplusecommerce.order.business.repository;

import practice.hhplusecommerce.order.business.entity.Order;

public interface OrderRepository {

  Order save(Order order);
}
