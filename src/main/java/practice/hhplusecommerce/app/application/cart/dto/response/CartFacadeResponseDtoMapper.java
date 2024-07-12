package practice.hhplusecommerce.app.application.cart.dto.response;

import practice.hhplusecommerce.app.service.cart.dto.response.CartServiceResponseDto;

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
