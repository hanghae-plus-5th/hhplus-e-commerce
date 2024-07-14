package practice.hhplusecommerce.app.application.product.dto.response;

import lombok.Getter;
import lombok.Setter;

public class ProductFacadeResponseDto {

  @Getter
  @Setter
  public static class Top5ProductsLast3DaysResponse {

    private Long productId;
    private String productName;
    private Integer productPrice;
    private Integer productStock;
    private Long sumQuantity;
  }
}
