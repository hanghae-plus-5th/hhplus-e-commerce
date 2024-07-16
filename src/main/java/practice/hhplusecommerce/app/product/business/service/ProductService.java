package practice.hhplusecommerce.app.product.business.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.product.business.entity.Product;
import practice.hhplusecommerce.app.product.business.repository.ProductRepository;
import practice.hhplusecommerce.app.common.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional
  public List<Product> getProductList() {
    return productRepository.findAll();
  }

  public Product getProduct(Long productId) {
    return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("상품", true));
  }

  public List<Product> getProductListByProductIdList(List<Long> productIdList) {
    List<Product> productList = productRepository.findAllByIdIn(productIdList);
    if (productList.size() != productIdList.size()) {
      throw new NotFoundException("상품", true);
    }
    return productList;
  }
}
