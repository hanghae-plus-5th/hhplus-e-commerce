package practice.hhplusecommerce.app.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.user.application.dto.UserFacadeDto;
import practice.hhplusecommerce.app.user.application.dto.UserFacadeDtoMapper;
import practice.hhplusecommerce.app.user.business.service.UserService;

@Component
@RequiredArgsConstructor
public class UserFacade {

  private final UserService userService;

  @Transactional(readOnly = true)
  public UserFacadeDto getUserAmount(Long userId) {
    return UserFacadeDtoMapper.toUserFacadeDto(userService.getUser(userId));
  }
}
