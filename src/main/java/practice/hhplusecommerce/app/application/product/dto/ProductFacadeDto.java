package practice.hhplusecommerce.app.application.product.dto;

public record ProductFacadeDto(
    Long id,
    String name,
    Integer price,
    Integer stock
) {

  public ProductFacadeDto(Long id, String name, Integer price, Integer stock) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.stock = stock;
  }
}
