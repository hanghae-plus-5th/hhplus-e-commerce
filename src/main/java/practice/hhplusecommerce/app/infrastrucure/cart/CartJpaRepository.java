package practice.hhplusecommerce.app.infrastrucure.cart;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.domain.cart.Cart;

@Repository
public interface CartJpaRepository extends JpaRepository<Cart, Long> {

  List<Cart> findAllByUserId(Long userId);
}
