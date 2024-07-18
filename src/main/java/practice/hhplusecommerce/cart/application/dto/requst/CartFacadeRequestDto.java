package practice.hhplusecommerce.cart.application.dto.requst;

import lombok.Getter;
import lombok.Setter;

public class CartFacadeRequestDto {


  @Getter
  @Setter
  public static class Create {

    private Long productId;
    private Integer quantity;
  }
}
