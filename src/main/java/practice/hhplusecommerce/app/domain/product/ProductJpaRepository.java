package practice.hhplusecommerce.app.domain.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

  List<Product> findAllByIdIn(List<Long> productId);
}
