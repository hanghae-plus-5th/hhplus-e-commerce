package practice.hhplusecommerce.user.business.dto;

public class UserServiceResponseDto {

  public record TokenResponse(
      Long id,
      String name,
      Integer amount,
      String accessToken
  ){

    public TokenResponse(Long id, String name, Integer amount, String accessToken) {
      this.id = id;
      this.name = name;
      this.amount = amount;
      this.accessToken = accessToken;
    }
  }
}
