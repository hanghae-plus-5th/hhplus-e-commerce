package practice.hhplusecommerce.app.presentation.product.dto.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProductResponseDto {

  @Getter
  @Setter
  @NoArgsConstructor
  public static class ProductResponse {

    private Long id;
    private String name;
    private Integer price;
    private Integer stock;

    public ProductResponse(Long id, String name, Integer price, Integer stock) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.stock = stock;
    }
  }
}
