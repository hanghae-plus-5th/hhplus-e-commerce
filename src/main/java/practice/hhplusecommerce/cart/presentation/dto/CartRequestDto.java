package practice.hhplusecommerce.cart.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class CartRequestDto {

  @Getter
  @Setter
  @Schema(name = "장바구니 생성 DTO")
  public static class CartCreate {

    @Schema(description = "상품 고유 번호", defaultValue = "1")
    private Long productId;

    @Schema(description = "구매 개수", defaultValue = "1")
    private Integer quantity;
  }
}
