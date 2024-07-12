package practice.hhplusecommerce.app.service.cart.dto.response;

import practice.hhplusecommerce.app.domain.cart.Cart;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.service.cart.dto.response.CartServiceResponseDto.Response;

public class CartServiceResponseDtoMapper {

  public static CartServiceResponseDto.Response toResponse(Cart cart) {
    CartServiceResponseDto.Response response = new Response();

    Product product = cart.getProduct();

    response.setId(cart.getId());
    response.setQuantity(cart.getQuantity());
    response.setName(product.getName());
    response.setPrice(product.getPrice());
    response.setProductId(product.getId());
    response.setStock(product.getStock());
    return response;
  }
}
