package practice.hhplusecommerce.app.service.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.service.product.dto.request.ProductServiceRequestDto;
import practice.hhplusecommerce.global.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<Product> getProductList() {
    return null;
  }

  public Product getProduct(Long productId) {
    return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("상품", true));
  }

  public List<Product> getProductListByProductIdList(List<Long> productIdList) {
    return null;
  }

  public List<Product> getTop5ProductsLast3Days() {
    return null;
  }

  public List<ProductServiceRequestDto.productStockResponse> getStockList(List<Long> productIdList) {
    return null;
  }
}
