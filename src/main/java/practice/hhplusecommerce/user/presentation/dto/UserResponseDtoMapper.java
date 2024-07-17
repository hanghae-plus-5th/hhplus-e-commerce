package practice.hhplusecommerce.user.presentation.dto;

import practice.hhplusecommerce.user.application.dto.UserFacadeResponseDto;

public class UserResponseDtoMapper {

  public static UserResponseDto.UserResponse toUserResponse(UserFacadeResponseDto.Response response) {
    return new UserResponseDto.UserResponse(response.id(), response.name(), response.amount());
  }

  public static UserResponseDto.TokenUserResponse toTokenUserResponse(UserFacadeResponseDto.ToKenResponse  response) {
    return new UserResponseDto.TokenUserResponse(response.id(), response.name(), response.amount(), response.accessToken());
  }
}
