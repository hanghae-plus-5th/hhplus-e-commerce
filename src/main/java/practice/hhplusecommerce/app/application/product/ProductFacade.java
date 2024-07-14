package practice.hhplusecommerce.app.application.product;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.product.dto.ProductFacadeDto;
import practice.hhplusecommerce.app.application.product.dto.ProductFacadeDtoMapper;
import practice.hhplusecommerce.app.application.product.dto.response.ProductFacadeResponseDto;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.service.order.OrderProductService;
import practice.hhplusecommerce.app.service.order.OrderService;
import practice.hhplusecommerce.app.service.product.ProductService;

@Component
@RequiredArgsConstructor
public class ProductFacade {

  private final ProductService productService;
  private final OrderService orderService;
  private final OrderProductService orderProductService;

  @Transactional
  public List<ProductFacadeDto> getProductList() {
    List<Product> productList = productService.getProductList();
    return productList
        .stream()
        .map(ProductFacadeDtoMapper::toProductFacadeDto)
        .toList();
  }

  @Transactional
  public List<ProductFacadeResponseDto.Top5ProductsLast3DaysResponse> getTop5ProductsLast3Days() {
    return orderProductService.getTop5ProductsLast3Days().stream().map(ProductFacadeDtoMapper::toTop5ProductsLast3DaysResponse).toList();
  }
}
