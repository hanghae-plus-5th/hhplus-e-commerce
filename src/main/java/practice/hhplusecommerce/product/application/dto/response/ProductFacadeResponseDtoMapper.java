package practice.hhplusecommerce.product.application.dto.response;

import practice.hhplusecommerce.order.business.command.OrderCommand;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.Top5ProductsLast3DaysResponse;
import practice.hhplusecommerce.product.business.entity.Product;

public class ProductFacadeResponseDtoMapper {

  public static ProductFacadeResponseDto.Response toProductFacadeDto(Product product) {
    return new ProductFacadeResponseDto.Response(product.getId(), product.getName(), product.getPrice(), product.getStock());
  }

  public static ProductFacadeResponseDto.Top5ProductsLast3DaysResponse toTop5ProductsLast3DaysResponse(OrderCommand.Top5ProductsLast3DaysResponse command) {
    ProductFacadeResponseDto.Top5ProductsLast3DaysResponse top5ProductsLast3DaysResponse = new Top5ProductsLast3DaysResponse();
    top5ProductsLast3DaysResponse.setProductId(command.productId());
    top5ProductsLast3DaysResponse.setProductName(command.productName());
    top5ProductsLast3DaysResponse.setProductStock(command.productStock());
    top5ProductsLast3DaysResponse.setProductPrice(command.productPrice());
    top5ProductsLast3DaysResponse.setSumQuantity(command.sumQuantity());
    return top5ProductsLast3DaysResponse;
  }
}
