package practice.hhplusecommerce.order.business.command;

import jakarta.persistence.Tuple;

public class OrderCommand {

  public record Top5ProductsLast3DaysResponse(
      Long productId,
      String productName,
      Integer productPrice,
      Integer productStock,
      Long sumQuantity
  ) {

    public Top5ProductsLast3DaysResponse(Long productId, String productName, Integer productPrice, Integer productStock, Long sumQuantity) {
      this.productId = productId;
      this.productName = productName;
      this.productPrice = productPrice;
      this.productStock = productStock;
      this.sumQuantity = sumQuantity;
    }

    public Top5ProductsLast3DaysResponse(Tuple tuple) {
      this(
          (Long) tuple.get("productId"),
          (String) tuple.get("productName"),
          (Integer) tuple.get("productPrice"),
          (Integer) tuple.get("productStock"),
          (Long) tuple.get("sumQuantity")
      );
    }
  }
}
