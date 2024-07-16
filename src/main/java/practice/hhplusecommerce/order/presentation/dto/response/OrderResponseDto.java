package practice.hhplusecommerce.order.presentation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OrderResponseDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @Schema(name = "주문 응답 DTO")
  public static class OrderResponse {

    @Schema(description = "주문 고유 번호")
    private Long id;

    @Schema(description = "주문 총 금액")
    private Integer orderTotalPrice;

    @Schema(description = "주문상품 목록")
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
  @Schema(name = "주문 상품 응답 DTO")
  public static class OrderProductResponse {

    @Schema(description = "주문 상품 고유번호", defaultValue = "1")
    private Long id;

    @Schema(description = "상품명", defaultValue = "탁자")
    private String name;

    @Schema(description = "주문한 개수", defaultValue = "1")
    private Integer quantity;

    @Schema(description = "상품금액", defaultValue = "1500")
    private Integer price;

    public OrderProductResponse(Long id, String name, Integer quantity, Integer price) {
      this.id = id;
      this.name = name;
      this.quantity = quantity;
      this.price = price;
    }
  }
}
