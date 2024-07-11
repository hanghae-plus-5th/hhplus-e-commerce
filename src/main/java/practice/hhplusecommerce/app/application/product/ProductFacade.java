package practice.hhplusecommerce.app.application.product;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.product.dto.ProductFacadeDto;
import practice.hhplusecommerce.app.service.product.ProductService;

@Component
@RequiredArgsConstructor
public class ProductFacade {

  private final ProductService productService;

  @Transactional
  public List<ProductFacadeDto> getProductList() {
    return null;
  }


  @Transactional
  public List<ProductFacadeDto> getTop5ProductsLast3Days() {
    return null;
  }
}
