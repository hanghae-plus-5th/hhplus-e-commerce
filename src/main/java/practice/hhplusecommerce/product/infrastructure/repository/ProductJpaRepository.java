package practice.hhplusecommerce.product.infrastructure.repository;

import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.product.business.entity.Product;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  List<Product> findAllByIdIn(List<Long> productId);
}
