package practice.hhplusecommerce.product.application.dto.response;

import practice.hhplusecommerce.order.business.command.OrderCommand;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.PopularProductResponse;
import practice.hhplusecommerce.product.business.entity.Product;

public class ProductFacadeResponseDtoMapper {

  public static ProductFacadeResponseDto.Response toProductFacadeDto(Product product) {
    return new ProductFacadeResponseDto.Response(product.getId(), product.getName(), product.getPrice(), product.getStock());
  }

  public static ProductFacadeResponseDto.PopularProductResponse toTop5ProductsLast3DaysResponse(OrderCommand.PopularProductResponse command) {
    ProductFacadeResponseDto.PopularProductResponse popularProductResponse = new PopularProductResponse();
    popularProductResponse.setProductId(command.productId());
    popularProductResponse.setProductName(command.productName());
    popularProductResponse.setProductStock(command.productStock());
    popularProductResponse.setProductPrice(command.productPrice());
    popularProductResponse.setSumQuantity(command.sumQuantity());
    return popularProductResponse;
  }
}
