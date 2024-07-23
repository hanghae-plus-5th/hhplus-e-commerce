package practice.hhplusecommerce.user.application.dto;

import practice.hhplusecommerce.user.business.dto.UserServiceResponseDto;
import practice.hhplusecommerce.user.business.entity.User;

public class UserFacadeDtoResponseMapper {

  public static UserFacadeResponseDto.Response toUserFacadeDto(User user) {
    return new UserFacadeResponseDto.Response(user.getId(), user.getName(), user.getAmount());
  }

  public static UserFacadeResponseDto.ToKenResponse toUserFacadeDto(UserServiceResponseDto.TokenResponse response) {
    return new UserFacadeResponseDto.ToKenResponse(response.id(), response.name(), response.amount(), response.accessToken());
  }
}
