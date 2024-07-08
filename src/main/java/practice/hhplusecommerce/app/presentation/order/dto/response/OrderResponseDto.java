package practice.hhplusecommerce.app.presentation.order.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderResponseDto {

  @Getter
  @Setter
  @NoArgsConstructor
  public static class OrderResponse {

    private Long id;
    private Integer orderTotalPrice;
    private List<OrderProductResponse> orderProductList;

    public OrderResponse(Long id, Integer orderTotalPrice, List<OrderProductResponse> orderProductList) {
      this.id = id;
      this.orderTotalPrice = orderTotalPrice;
      this.orderProductList = orderProductList;
    }
  }

  @Getter
  @Setter
  @NoArgsConstructor
  public static class OrderProductResponse {

    private Long id;
    private String name;
    private Integer quantity;
    private Integer totalPrice;

    public OrderProductResponse(Long id, String name, Integer quantity, Integer totalPrice) {
      this.id = id;
      this.name = name;
      this.quantity = quantity;
      this.totalPrice = totalPrice;
    }
  }
}
