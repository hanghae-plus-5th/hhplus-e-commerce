package practice.hhplusecommerce.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import practice.hhplusecommerce.user.application.dto.UserFacadeDtoResponseMapper;
import practice.hhplusecommerce.user.application.dto.UserFacadeResponseDto;
import practice.hhplusecommerce.user.business.UserService;

@Component
@RequiredArgsConstructor
public class UserFacade {

  private final UserService userService;

  public UserFacadeResponseDto.Response getUserAmount(Long userId) {
    return UserFacadeDtoResponseMapper.toUserFacadeDto(userService.getUser(userId));
  }

  public UserFacadeResponseDto.Response chargeUserAmount(Long userId, Integer chargeAmount) {
    return UserFacadeDtoResponseMapper.toUserFacadeDto(userService.chargeUserAmount(userId, chargeAmount));
  }

  public UserFacadeResponseDto.ToKenResponse login(String userName) {
    return UserFacadeDtoResponseMapper.toUserFacadeDto(userService.login(userName));
  }

  public void join(String userName) {
    userService.saveUser(userName);
  }
}
