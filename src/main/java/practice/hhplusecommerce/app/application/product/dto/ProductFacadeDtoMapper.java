package practice.hhplusecommerce.app.application.product.dto;

import jakarta.persistence.Tuple;
import practice.hhplusecommerce.app.application.product.dto.response.ProductFacadeResponseDto;
import practice.hhplusecommerce.app.application.product.dto.response.ProductFacadeResponseDto.Top5ProductsLast3DaysResponse;
import practice.hhplusecommerce.app.domain.product.Product;

public class ProductFacadeDtoMapper {

  public static ProductFacadeDto toProductFacadeDto(Product product) {
      return new ProductFacadeDto(product.getId(), product.getName(), product.getPrice(), product.getStock());
  }

  public static ProductFacadeResponseDto.Top5ProductsLast3DaysResponse toTop5ProductsLast3DaysResponse(Tuple tuple) {
    ProductFacadeResponseDto.Top5ProductsLast3DaysResponse top5ProductsLast3DaysResponse = new Top5ProductsLast3DaysResponse();
    top5ProductsLast3DaysResponse.setProductId((Long) tuple.get("productId"));
    top5ProductsLast3DaysResponse.setProductName((String) tuple.get("productName"));
    top5ProductsLast3DaysResponse.setProductStock((Integer) tuple.get("productStock"));
    top5ProductsLast3DaysResponse.setProductPrice((Integer) tuple.get("productPrice"));
    top5ProductsLast3DaysResponse.setSumQuantity((Long) tuple.get("sumQuantity"));
    return top5ProductsLast3DaysResponse;
  }
}
