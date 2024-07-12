package practice.hhplusecommerce.app.application.user.dto;

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
