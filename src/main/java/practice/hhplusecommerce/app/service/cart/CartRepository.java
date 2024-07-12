package practice.hhplusecommerce.app.service.cart;

import java.util.List;
import java.util.Optional;
import practice.hhplusecommerce.app.domain.cart.Cart;

public interface CartRepository {

  List<Cart> findAllByUserId(Long userId);

  Optional<Cart> findById(Long cartId);

  Cart save(Cart cart);

  void delete(Cart cart);
}
