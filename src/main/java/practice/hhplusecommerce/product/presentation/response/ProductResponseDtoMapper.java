package practice.hhplusecommerce.product.presentation.response;

import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto;

public class ProductResponseDtoMapper {

  public static ProductResponseDto.ProductResponse toProductResponse(ProductFacadeResponseDto.Response response) {
      return new ProductResponseDto.ProductResponse(response.getId(), response.getName(), response.getPrice(), response.getStock());
  }

  public static ProductResponseDto.ProductResponse toProductResponse(ProductFacadeResponseDto.PopularProductResponse response) {
    return new ProductResponseDto.ProductResponse(response.getProductId(), response.getProductName(), response.getProductPrice(), response.getProductStock());
  }
}
