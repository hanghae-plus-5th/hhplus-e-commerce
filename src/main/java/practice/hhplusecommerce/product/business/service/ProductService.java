package practice.hhplusecommerce.product.business.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.common.exception.NotFoundException;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.repository.ProductRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional
  public List<Product> getProductList() {
    return productRepository.findAll();
  }

  @Transactional
  public Product getProduct(Long productId) {
    return productRepository.findById(productId).orElseThrow(() -> {
      log.error("ProductService.getProduct productId : {}", productId);
      return new NotFoundException("상품", true);
    });
  }


  public List<Product> getProductListByProductIdList(List<Long> productIdList) {
    List<Product> productList = productRepository.findAllByIdIn(productIdList);
    if (productList.size() != productIdList.size()) {
      log.error("ProductService.getProductListByProductIdList productList.size() {} , productIdList.size(), {}", productList.size(), productIdList.size());
      throw new NotFoundException("상품", true);
    }
    return productList;
  }
}
