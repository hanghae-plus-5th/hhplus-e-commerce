package practice.hhplusecommerce.app.presentation.cart.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

public class CartRequestDto {

  @Getter
  @Setter
  @Schema(name = "장바구니 생성 DTO")
  public static class CartCreate {

    @Schema(description = "유저 고유 번호", defaultValue = "1")
    private Integer userId;

    @Schema(description = "상품 고유 번호", defaultValue = "1")
    private Integer productId;

    @Schema(description = "구매 개수", defaultValue = "1")
    private Integer quantity;
  }
}
