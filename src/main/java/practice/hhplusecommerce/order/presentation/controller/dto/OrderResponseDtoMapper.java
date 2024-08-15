package practice.hhplusecommerce.order.presentation.controller.dto;

import practice.hhplusecommerce.order.application.dto.response.OrderFacadeResponseDto;
import practice.hhplusecommerce.order.presentation.controller.dto.OrderResponseDto.OrderProductResponse;
import practice.hhplusecommerce.order.presentation.controller.dto.OrderResponseDto.OrderResponse;

public class OrderResponseDtoMapper {

  public static OrderResponseDto.OrderResponse toOrderResponse(OrderFacadeResponseDto.OrderResponse from) {
    OrderResponseDto.OrderResponse to = new OrderResponse();
    to.setId(from.getId());
    to.setOrderTotalPrice(from.getOrderTotalPrice());
    to.setOrderProductList(from.getOrderProductList().stream().map(OrderResponseDtoMapper::toOrderProductResponse).toList());
    return to;
  }

  public static OrderResponseDto.OrderProductResponse toOrderProductResponse(OrderFacadeResponseDto.OrderProductResponse from) {
    OrderResponseDto.OrderProductResponse to = new OrderProductResponse();
    to.setId(from.getId());
    to.setName(from.getName());
    to.setQuantity(from.getQuantity());
    to.setPrice(from.getPrice());
    to.setQuantity(from.getQuantity());
    return to;
  }
}
