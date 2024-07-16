package practice.hhplusecommerce.app.product.application.dto.response;

import jakarta.persistence.Tuple;
import practice.hhplusecommerce.app.product.application.dto.response.ProductFacadeResponseDto;
import practice.hhplusecommerce.app.product.application.dto.response.ProductFacadeResponseDto.Top5ProductsLast3DaysResponse;
import practice.hhplusecommerce.app.product.business.entity.Product;

public class ProductFacadeResponseDtoMapper {

  public static ProductFacadeResponseDto.Response toProductFacadeDto(Product product) {
      return new ProductFacadeResponseDto.Response(product.getId(), product.getName(), product.getPrice(), product.getStock());
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
