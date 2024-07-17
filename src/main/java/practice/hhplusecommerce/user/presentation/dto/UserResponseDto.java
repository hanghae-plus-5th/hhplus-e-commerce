package practice.hhplusecommerce.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserResponseDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @Schema(name = "유저 응답 DTO")
  public static class UserResponse {

    @Schema(description = "유저 고유번호", defaultValue = "1")
    private Long id;

    @Schema(description = "유저명", defaultValue = "백현명")
    private String name;

    @Schema(description = "잔액", defaultValue = "5000")
    private Integer amount;

    public UserResponse(Long id, String name, Integer amount) {
      this.id = id;
      this.name = name;
      this.amount = amount;
    }
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @Schema(name = "유저 토큰 응답 DTO")
  public static class TokenUserResponse {

    @Schema(description = "유저 고유번호", defaultValue = "1")
    private Long id;

    @Schema(description = "유저명", defaultValue = "백현명")
    private String name;

    @Schema(description = "잔액", defaultValue = "5000")
    private Integer amount;

    @Schema(description = "엑세스토큰")
    private String accessToken;

    public TokenUserResponse(Long id, String name, Integer amount, String accessToken) {
      this.id = id;
      this.name = name;
      this.amount = amount;
      this.accessToken = accessToken;
    }
  }
}
