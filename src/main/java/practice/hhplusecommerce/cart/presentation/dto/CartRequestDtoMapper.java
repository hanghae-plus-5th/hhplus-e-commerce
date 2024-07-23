package practice.hhplusecommerce.cart.presentation.dto;

import practice.hhplusecommerce.cart.application.dto.requst.CartFacadeRequestDto;
import practice.hhplusecommerce.cart.application.dto.requst.CartFacadeRequestDto.Create;

public class CartRequestDtoMapper {

  public static CartFacadeRequestDto.Create toCreate(CartRequestDto.CartCreate from) {
    CartFacadeRequestDto.Create to = new Create();
    to.setQuantity(from.getQuantity());
    to.setProductId(from.getProductId());
    return to;
  }
}
