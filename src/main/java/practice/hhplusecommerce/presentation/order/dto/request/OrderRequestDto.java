package practice.hhplusecommerce.presentation.order.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class OrderRequestDto {

  @Getter
  @Setter
  public static class OrderCreate {

    private Integer userId;
    private List<OrderProductCreate> productList = new ArrayList<>();
  }

  @Getter
  @Setter
  public static class OrderProductCreate {

    private Integer id;
    private Integer quantity;
  }
}
