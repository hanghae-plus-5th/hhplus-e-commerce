package practice.hhplusecommerce.app.infrastrucure.product;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.product.ProductJpaRepository;
import practice.hhplusecommerce.app.service.product.ProductRepository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

  private final ProductJpaRepository productJpaRepository;

  @Override
  public Product save(Product product) {
    return productJpaRepository.save(product);
  }

  @Override
  public Optional<Product> findById(Long productId) {
    return productJpaRepository.findById(productId);
  }
}
