package practice.hhplusecommerce.app.application.cart.dto.response;

public record CartFacadeResponseDto(
    Long id,
    String name,
    Integer stock,
    Integer price,
    Long productId
) {

  public CartFacadeResponseDto(Long id, String name, Integer stock, Integer price, Long productId) {
    this.id = id;
    this.name = name;
    this.stock = stock;
    this.price = price;
    this.productId = productId;
  }
}
