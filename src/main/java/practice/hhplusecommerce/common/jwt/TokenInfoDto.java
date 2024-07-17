package practice.hhplusecommerce.common.jwt;

import lombok.Getter;

@Getter
public class TokenInfoDto {

  private String userName;
  private Long userId;

  public TokenInfoDto(String userName, Long userId) {
    this.userName = userName;
    this.userId = userId;
  }
}
