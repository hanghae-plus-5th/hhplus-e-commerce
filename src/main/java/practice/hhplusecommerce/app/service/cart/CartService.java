package practice.hhplusecommerce.app.service.cart;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.cart.Cart;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.service.cart.dto.response.CartServiceResponseDto;

@Service
@RequiredArgsConstructor
public class CartService {

  public List<CartServiceResponseDto.Response> getCartList(Long userId) {
    return null;
  }

  public CartServiceResponseDto.Response getCart(Long cartId) {
    return null;
  }

  public CartServiceResponseDto.Response createCart(User user, Product product) {
    return null;
  }

  public CartServiceResponseDto.Response deleteCart(Long cartId) {
    return null;
  }


}
