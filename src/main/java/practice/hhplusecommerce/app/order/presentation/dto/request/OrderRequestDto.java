package practice.hhplusecommerce.app.order.presentation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class OrderRequestDto {

  @Getter
  @Setter
  @Schema(name = "주문 생성 DTO")
  public static class OrderCreate {

    @Schema(description = "유저 고유번호", defaultValue = "1")
    private Long userId;

    @Schema(description = "주문할 상품 목록")
    private List<OrderProductCreate> productList = new ArrayList<>();
  }

  @Getter
  @Setter
  @Schema(name = "장바구니 주문 상품 생성 DTO")
  public static class OrderProductCreate {

    @Schema(description = "상품 고유 번호", defaultValue = "1")
    private Long id;

    @Schema(description = "주문한 개수", defaultValue = "40")
    private Integer quantity;
  }
}
