package practice.hhplusecommerce.app.application.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.user.dto.UserFacadeDto;
import practice.hhplusecommerce.app.application.user.dto.UserFacadeDtoMapper;
import practice.hhplusecommerce.app.service.user.UserService;

@Component
@RequiredArgsConstructor
public class UserFacade {

  private final UserService userService;

  @Transactional(readOnly = true)
  public UserFacadeDto getUserAmount(Long userId) {
    return null;
  }

  @Transactional(readOnly = true)
  public UserFacadeDto chargeUserAmount(Long userId, Integer chargeAmount) {
    return null;
  }
}
