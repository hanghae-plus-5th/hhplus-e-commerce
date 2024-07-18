package practice.hhplusecommerce.order.presentation.dto;

import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto;
import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto.Create;

public class OrderRequestDtoMapper {

  public static OrderFacadeRequestDto.Create toCreate(OrderRequestDto.OrderCreate create) {
    OrderFacadeRequestDto.Create to = new Create();
    to.setProductList(create.getProductList().stream().map(OrderRequestDtoMapper::toOrderProductCreate).toList());
    return to;
  }

  public static OrderFacadeRequestDto.OrderProductCreate toOrderProductCreate(OrderRequestDto.OrderProductCreate from) {
    OrderFacadeRequestDto.OrderProductCreate to = new OrderFacadeRequestDto.OrderProductCreate();
    to.setId(from.getId());
    to.setQuantity(from.getQuantity());
    return to;
  }
}
