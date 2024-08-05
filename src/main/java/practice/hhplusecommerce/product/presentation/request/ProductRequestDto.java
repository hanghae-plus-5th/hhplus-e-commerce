package practice.hhplusecommerce.product.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductRequestDto {

  public record Update(

      @NotBlank(message = "금액이 필요합니다.")
      @Schema(description = "상품명", defaultValue = "탁자")
      String name,

      @NotNull(message = "금액이 필요합니다.")
      @Schema(description = "금액", defaultValue = "1500")
      Integer price,

      @NotNull(message = "재고가 필요합니다.")
      @Schema(description = "재고", defaultValue = "5")
      Integer stock
  ) {

    public Update(String name, Integer price, Integer stock) {
      this.name = name;
      this.price = price;
      this.stock = stock;
    }
  }
}
