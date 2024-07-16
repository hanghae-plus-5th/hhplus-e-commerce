package practice.hhplusecommerce.app.product.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import practice.hhplusecommerce.app.order.business.service.OrderService;
import practice.hhplusecommerce.app.product.application.dto.response.ProductFacadeResponseDtoMapper;
import practice.hhplusecommerce.app.product.application.dto.response.ProductFacadeResponseDto;
import practice.hhplusecommerce.app.product.application.dto.response.ProductFacadeResponseDto.Top5ProductsLast3DaysResponse;
import practice.hhplusecommerce.app.product.business.entity.Product;
import practice.hhplusecommerce.app.product.business.service.ProductService;

@Component
@RequiredArgsConstructor
public class ProductFacade {

  private final ProductService productService;
  private final OrderService orderService;

  public List<ProductFacadeResponseDto.Response> getProductList() {
    List<Product> productList = productService.getProductList();
    return productList
        .stream()
        .map(ProductFacadeResponseDtoMapper::toProductFacadeDto)
        .toList();
  }

  public List<Top5ProductsLast3DaysResponse> getTop5ProductsLast3Days() {
    return orderService.getTop5ProductsLast3Days().stream().map(ProductFacadeResponseDtoMapper::toTop5ProductsLast3DaysResponse).toList();
  }
}
