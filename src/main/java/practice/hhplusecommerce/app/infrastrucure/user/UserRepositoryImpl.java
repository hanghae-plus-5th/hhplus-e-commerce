package practice.hhplusecommerce.app.infrastrucure.user;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.domain.user.UserJpaRepository;
import practice.hhplusecommerce.app.service.user.UserRepository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final UserJpaRepository userJpaRepository;

  @Override
  public Optional<User> findById(long userId) {
    return userJpaRepository.findById(userId);
  }

  @Override
  public User save(User user) {
    return userJpaRepository.save(user);
  }
}
