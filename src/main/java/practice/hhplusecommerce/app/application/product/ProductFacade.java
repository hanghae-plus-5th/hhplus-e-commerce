package practice.hhplusecommerce.app.application.product;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.product.dto.ProductFacadeDto;
import practice.hhplusecommerce.app.application.product.dto.ProductFacadeDtoMapper;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.service.order.OrderService;
import practice.hhplusecommerce.app.service.product.ProductService;

@Component
@RequiredArgsConstructor
public class ProductFacade {

  private final ProductService productService;
  private final OrderService orderService;

  @Transactional
  public List<ProductFacadeDto> getProductList() {
    List<Product> productList = productService.getProductList();
    return productList
        .stream()
        .map(ProductFacadeDtoMapper::toProductFacadeDto)
        .toList();
  }

  @Transactional
  public List<ProductFacadeDto> getTop5ProductsLast3Days() {
    List<Long> productIdList = orderService.getTop5ProductIdsLast3Days();
    return productService.getProductListByProductIdList(productIdList)
        .stream()
        .map(ProductFacadeDtoMapper::toProductFacadeDto)
        .toList();
  }
}
