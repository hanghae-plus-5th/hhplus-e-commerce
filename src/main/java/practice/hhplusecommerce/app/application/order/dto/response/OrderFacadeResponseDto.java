package practice.hhplusecommerce.app.application.order.dto.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import practice.hhplusecommerce.app.presentation.order.dto.response.OrderResponseDto;

public class OrderFacadeResponseDto {

  @Getter
  @Setter
  public static class OrderResponse {

    private Long id;
    private Integer orderTotalPrice;
    private List<OrderResponseDto.OrderProductResponse> orderProductList;
  }

  @Getter
  @Setter
  public static class OrderProductResponse {
    private Long id;
    private String name;
    private Integer quantity;
    private Integer price;
  }
}
