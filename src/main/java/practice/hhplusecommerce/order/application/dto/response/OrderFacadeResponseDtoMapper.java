package practice.hhplusecommerce.order.application.dto.response;

import practice.hhplusecommerce.order.application.dto.response.OrderFacadeResponseDto.OrderProductResponse;
import practice.hhplusecommerce.order.application.dto.response.OrderFacadeResponseDto.OrderResponse;
import practice.hhplusecommerce.order.business.entity.Order;
import practice.hhplusecommerce.order.business.entity.OrderProduct;

public class OrderFacadeResponseDtoMapper {

  public static OrderFacadeResponseDto.OrderResponse toOrderResponse(Order order) {
    OrderFacadeResponseDto.OrderResponse orderResponse = new OrderResponse();
    orderResponse.setId(order.getId());
    orderResponse.setOrderTotalPrice(order.getOrderTotalPrice());
    return orderResponse;
  }

  public static OrderFacadeResponseDto.OrderProductResponse toOrderProductResponse(OrderProduct orderProduct) {
    OrderFacadeResponseDto.OrderProductResponse orderProductResponse = new OrderProductResponse();
    orderProductResponse.setId(orderProduct.getId());
    orderProductResponse.setName(orderProduct.getName());
    orderProductResponse.setPrice(orderProduct.getPrice());
    orderProductResponse.setQuantity(orderProduct.getQuantity());
    return orderProductResponse;
  }

}
