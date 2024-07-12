package practice.hhplusecommerce.app.service.product.dto.request;

import lombok.Getter;
import lombok.Setter;

public class ProductServiceRequestDto {

  @Getter
  @Setter
  public static class productStockResponse {
      private Long productId;
      private Integer stock;
  }

  @Getter
  @Setter
  public static class DeductionStock {
    private Long productId;
    private Integer deductionStock;
  }
}
