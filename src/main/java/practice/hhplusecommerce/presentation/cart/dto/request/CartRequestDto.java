package practice.hhplusecommerce.presentation.cart.dto.request;

import lombok.Getter;
import lombok.Setter;

public class CartRequestDto {

  @Getter
  @Setter
  public static class CartCreate {

    private Integer userId;
    private Integer productId;
    private Integer quantity;
  }
}
