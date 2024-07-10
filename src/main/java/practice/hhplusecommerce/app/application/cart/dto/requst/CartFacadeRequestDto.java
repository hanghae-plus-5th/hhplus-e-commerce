package practice.hhplusecommerce.app.application.cart.dto.requst;

import lombok.Getter;
import lombok.Setter;

public class CartFacadeRequestDto {


  @Getter
  @Setter
  public static class Create {

    private Long userId;
    private Long productId;
    private Integer quantity;
  }
}
