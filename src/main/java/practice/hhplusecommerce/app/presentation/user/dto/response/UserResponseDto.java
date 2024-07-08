package practice.hhplusecommerce.app.presentation.user.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserResponseDto {

  @Getter
  @Setter
  @NoArgsConstructor
  public static class UserResponse {

    private Long id;
    private String name;
    private Integer amount;

    public UserResponse(Long id, String name, Integer amount) {
      this.id = id;
      this.name = name;
      this.amount = amount;
    }
  }
}
