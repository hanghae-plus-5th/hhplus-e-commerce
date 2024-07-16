package practice.hhplusecommerce.cart.business.dto;

import practice.hhplusecommerce.cart.business.entity.Cart;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.cart.business.dto.CartServiceResponseDto.Response;

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
