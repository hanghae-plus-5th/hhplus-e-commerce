package practice.hhplusecommerce.product.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import practice.hhplusecommerce.order.business.service.OrderService;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.Response;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.PopularProductResponse;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDtoMapper;
import practice.hhplusecommerce.product.business.dto.ProductCommand;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.service.ProductService;
import practice.hhplusecommerce.product.presentation.request.ProductRequestDto.Update;

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

  public List<PopularProductResponse> getPopularProduct() {
    return orderService.getPopularProduct().stream().map(ProductFacadeResponseDtoMapper::toTop5ProductsLast3DaysResponse).toList();
  }

  public void updateProduct(Long productId, Update update) {
    productService.updateProduct(new ProductCommand.Update(productId, update.name(), update.price(), update.stock()));
  }

  public void deleteProduct(Long productId) {
    productService.deleteProduct(productId);
  }
}
