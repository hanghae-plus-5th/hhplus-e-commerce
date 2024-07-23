package practice.hhplusecommerce.user.application.dto;

public class UserFacadeResponseDto {

  public record Response(
      Long id,
      String name,
      Integer amount
  ) {

    public Response(Long id, String name, Integer amount) {
      this.id = id;
      this.name = name;
      this.amount = amount;
    }
  }

  public record ToKenResponse(
      Long id,
      String name,
      Integer amount,
      String accessToken
  ) {

    public ToKenResponse(Long id, String name, Integer amount, String accessToken) {
      this.id = id;
      this.name = name;
      this.amount = amount;
      this.accessToken = accessToken;
    }
  }
}
