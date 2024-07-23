package practice.hhplusecommerce.order.application.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class OrderFacadeResponseDto {

  @Getter
  @Setter
  public static class OrderResponse {

    private Long id;
    private Integer orderTotalPrice;
    private List<OrderFacadeResponseDto.OrderProductResponse> orderProductList = new ArrayList<>();
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
