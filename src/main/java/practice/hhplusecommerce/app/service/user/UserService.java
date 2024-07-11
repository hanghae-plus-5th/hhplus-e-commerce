package practice.hhplusecommerce.app.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.user.User;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  public User getUser(Long userId) {
    return null;
  }

  public User chargeUserAmount(Long userId, Integer chargeAmount) {
    return null;
  }

}
