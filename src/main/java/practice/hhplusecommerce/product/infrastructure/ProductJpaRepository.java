package practice.hhplusecommerce.product.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.product.business.entity.Product;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

  List<Product> findAllByIdIn(List<Long> productId);
}
