package practice.hhplusecommerce.app.cart.business.dto;

import lombok.Getter;
import lombok.Setter;

public class CartServiceResponseDto {

  @Getter
  @Setter
  public static class Response {
    private Long id;
    private String name;
    private Integer stock;
    private Integer quantity;
    private Integer price;
    private Long productId;
  }
}
