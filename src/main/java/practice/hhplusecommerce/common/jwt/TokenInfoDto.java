package practice.hhplusecommerce.common.jwt;

import lombok.Getter;

@Getter
public class TokenInfoDto {

  private String useName;

  public TokenInfoDto(String useName) {
    this.useName = useName;
  }
}
