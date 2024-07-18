package practice.hhplusecommerce.product.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import practice.hhplusecommerce.order.business.service.OrderService;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.Response;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.Top5ProductsLast3DaysResponse;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDtoMapper;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.service.ProductService;

@Component
@RequiredArgsConstructor
public class ProductFacade {

  private final ProductService productService;
  private final OrderService orderService;

  public List<Response> getProductList() {
    List<Product> productList = productService.getProductList();
    return productList
        .stream()
        .map(ProductFacadeResponseDtoMapper::toProductFacadeDto)
        .toList();
  }

  public Response getProduct(Long productId) {
    return ProductFacadeResponseDtoMapper.toProductFacadeDto(productService.getProduct(productId));
  }

  public List<Top5ProductsLast3DaysResponse> getTop5ProductsLast3Days() {
    return orderService.getTop5ProductsLast3Days().stream().map(ProductFacadeResponseDtoMapper::toTop5ProductsLast3DaysResponse).toList();
  }
}
