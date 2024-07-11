package practice.hhplusecommerce.app.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.global.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저", true));
  }

  public User chargeUserAmount(Long userId, Integer chargeAmount) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저", true));
    user.chargeAmount(chargeAmount);
    return user;
  }
}
