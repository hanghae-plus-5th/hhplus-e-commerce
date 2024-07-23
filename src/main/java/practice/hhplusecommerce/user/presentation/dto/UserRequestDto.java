package practice.hhplusecommerce.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDto {

  @Getter
  @NoArgsConstructor
  public static class Request {

    @Schema(description = "유저명", defaultValue = "백현명")
    private String name;

    public Request(String name) {
      this.name = name;
    }
  }
}
