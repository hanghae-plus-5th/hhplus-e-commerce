package practice.hhplusecommerce.app.domain.cart;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartJpaRepository extends JpaRepository<Cart, Long> {

  List<Cart> findAllByUserId(Long userId);
}
