package practice.hhplusecommerce.product.business.dto;

public class ProductCommand {

  public record Update(
      Long id,
      String name,
      Integer price,
      Integer stock
  ) {

    public Update(Long id, String name, Integer price, Integer stock) {
      this.id = id;
      this.name = name;
      this.price = price;
      this.stock = stock;
    }
  }
}
