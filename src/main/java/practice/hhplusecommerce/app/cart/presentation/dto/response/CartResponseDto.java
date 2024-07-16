package practice.hhplusecommerce.app.cart.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CartResponseDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @Schema(name = "장바구니 응답 DTO")
  public static class CartResponse {

    @Schema(description = "장바구니 고유 번호", defaultValue = "1")
    private Long id;

    @Schema(description = "상품 명", defaultValue = "탁자")
    private String name;

    @Schema(description = "장바구니에 담은 개수", defaultValue = "15")
    private Integer quantity;

    @Schema(description = "재고", defaultValue = "12")
    private Integer stock;

    @Schema(description = "가격", defaultValue = "2000")
    private Integer price;

    @Schema(description = "상품 고유 번호", defaultValue = "1")
    private Long productId;

    public CartResponse(Long id, String name, Integer quantity, Integer stock, Integer price, Long productId) {
      this.id = id;
      this.name = name;
      this.quantity = quantity;
      this.stock = stock;
      this.price = price;
      this.productId = productId;
    }
  }
}
