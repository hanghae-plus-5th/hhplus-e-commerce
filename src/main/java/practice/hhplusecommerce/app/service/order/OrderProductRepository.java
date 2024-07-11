package practice.hhplusecommerce.app.service.order;

import practice.hhplusecommerce.app.domain.order.OrderProduct;

public interface OrderProductRepository {

  OrderProduct save(OrderProduct orderProduct);
}
