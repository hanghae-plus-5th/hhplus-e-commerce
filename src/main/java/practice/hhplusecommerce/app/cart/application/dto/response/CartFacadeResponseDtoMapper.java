package practice.hhplusecommerce.app.cart.application.dto.response;

import practice.hhplusecommerce.app.cart.business.dto.CartServiceResponseDto;

public class CartFacadeResponseDtoMapper {

  public static CartFacadeResponseDto toCartFacadeResponseDto(CartServiceResponseDto.Response response) {
    return new CartFacadeResponseDto(
        response.getId(),
        response.getName(),
        response.getStock(),
        response.getQuantity(),
        response.getPrice(),
        response.getProductId()
    );
  }
}
