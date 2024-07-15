package practice.hhplusecommerce.app.cart.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.cart.business.entity.Cart;

@Repository
public interface CartJpaRepository extends JpaRepository<Cart, Long> {

  List<Cart> findAllByUserId(Long userId);
}
