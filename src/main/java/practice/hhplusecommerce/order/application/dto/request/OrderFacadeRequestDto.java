package practice.hhplusecommerce.order.application.dto.request;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class OrderFacadeRequestDto {

  @Getter
  @Setter
  public static class Create {

    private List<OrderProductCreate> productList = new ArrayList<>();
  }

  @Getter
  @Setter
  public static class OrderProductCreate {

    private Long id;
    private Integer quantity;
  }
}
