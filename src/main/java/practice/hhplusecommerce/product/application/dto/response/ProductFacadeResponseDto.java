package practice.hhplusecommerce.product.application.dto.response;

import lombok.Getter;
import lombok.Setter;

public class ProductFacadeResponseDto {

  @Getter
  @Setter
  public static class Top5ProductsLast3DaysResponse {

    private Long productId;
    private String productName;
    private Integer productPrice;
    private Integer productStock;
    private Long sumQuantity;
  }

  @Getter
  public static class Response {
    private Long id;
    private String name;
    private Integer price;
    private Integer stock;

    public Response(Long id, String name, Integer price, Integer stock) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.stock = stock;
    }
  }
}
