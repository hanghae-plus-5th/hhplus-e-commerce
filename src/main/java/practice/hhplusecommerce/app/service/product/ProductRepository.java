package practice.hhplusecommerce.app.service.product;

import java.util.Optional;
import practice.hhplusecommerce.app.domain.product.Product;

public interface ProductRepository {

  Product save(Product product);

  Optional<Product> findById(Long productId);
}
