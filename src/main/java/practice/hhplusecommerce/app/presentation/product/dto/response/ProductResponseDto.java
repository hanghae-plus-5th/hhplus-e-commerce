package practice.hhplusecommerce.app.presentation.product.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProductResponseDto {

  @Getter
  @NoArgsConstructor
  @Schema(name = "상품 응답 DTO")
  public static class ProductResponse {

    @Schema(description = "상품 고유 번호", defaultValue = "1")
    private Long id;

    @Schema(description = "상품명", defaultValue = "탁자")
    private String name;

    @Schema(description = "금액", defaultValue = "1500")
    private Integer price;

    @Schema(description = "재고", defaultValue = "5")
    private Integer stock;

    @Builder
    public ProductResponse(Long id, String name, Integer price, Integer stock) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.stock = stock;
    }
  }
}
