package practice.hhplusecommerce.app.user.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.user.business.entity.User;
import practice.hhplusecommerce.app.common.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public User getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저", true));
  }

  @Transactional
  public User chargeUserAmount(Long userId, Integer chargeAmount) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저", true));
    user.chargeAmount(chargeAmount);
    return user;
  }
}
