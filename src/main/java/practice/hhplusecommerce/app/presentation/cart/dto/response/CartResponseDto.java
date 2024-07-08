package practice.hhplusecommerce.app.presentation.cart.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CartResponseDto {

  @Getter
  @Setter
  @NoArgsConstructor
  public static class CartResponse {

    private Long id;
    private String name;
    private Integer quantity;
    private Integer stock;
    private Integer price;
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
