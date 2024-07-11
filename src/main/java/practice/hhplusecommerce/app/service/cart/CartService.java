package practice.hhplusecommerce.app.service.cart;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.cart.Cart;

@Service
@RequiredArgsConstructor
public class CartService {

  public List<Cart> getCartList(Long userId) {
    return null;
  }

  public Cart createCart(Cart cart) {
    return null;
  }

  public Cart deleteCart(Long cartId) {
    return null;
  }
}
