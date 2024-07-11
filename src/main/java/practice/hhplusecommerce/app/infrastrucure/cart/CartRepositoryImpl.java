package practice.hhplusecommerce.app.infrastrucure.cart;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.domain.cart.Cart;
import practice.hhplusecommerce.app.domain.cart.CartJpaRepository;
import practice.hhplusecommerce.app.service.cart.CartRepository;

@Repository
@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepository {

  private final CartJpaRepository cartJpaRepository;

  @Override
  public List<Cart> findAllByUserId(Long userId) {
    return null;
  }

  @Override
  public Optional<Cart> findById(Long cartId) {
    return Optional.empty();
  }

  @Override
  public Cart save(Cart cart) {
    return null;
  }

  @Override
  public void delete(Cart cart) {

  }
}
