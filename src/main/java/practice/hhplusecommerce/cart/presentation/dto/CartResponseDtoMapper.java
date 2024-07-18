package practice.hhplusecommerce.cart.presentation.dto;

import practice.hhplusecommerce.cart.application.dto.response.CartFacadeResponseDto;
import practice.hhplusecommerce.cart.presentation.dto.CartResponseDto.CartResponse;

public class CartResponseDtoMapper {

  public static CartResponseDto.CartResponse toCartResponse(CartFacadeResponseDto from) {
    CartResponseDto.CartResponse to = new CartResponse();
    to.setId(from.id());
    to.setQuantity(from.quantity());
    to.setPrice(from.price());
    to.setName(from.name());
    to.setProductId(from.productId());
    to.setStock(from.stock());
    return to;
  }
}
