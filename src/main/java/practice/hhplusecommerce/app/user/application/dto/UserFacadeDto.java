package practice.hhplusecommerce.app.user.application.dto;

public record UserFacadeDto(
    Long id,
    String name,
    Integer amount
) {

  public UserFacadeDto(Long id, String name, Integer amount) {
    this.id = id;
    this.name = name;
    this.amount = amount;
  }
}
