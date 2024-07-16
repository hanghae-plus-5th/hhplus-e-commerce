package practice.hhplusecommerce.product.infrastructure;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.repository.ProductRepository;

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

  @Override
  public List<Product> findAll() {
    return productJpaRepository.findAll();
  }

  @Override
  public List<Product> findAllByIdIn(List<Long> productId) {
    return productJpaRepository.findAllByIdIn(productId);
  }

  @Override
  public void saveAll(List<Product> productList) {
    productJpaRepository.saveAll(productList);
  }
}
