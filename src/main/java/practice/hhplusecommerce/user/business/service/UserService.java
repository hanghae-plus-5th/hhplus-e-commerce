package practice.hhplusecommerce.user.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.common.exception.NotFoundException;

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
