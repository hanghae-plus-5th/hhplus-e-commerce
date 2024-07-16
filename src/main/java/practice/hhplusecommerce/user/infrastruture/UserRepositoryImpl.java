package practice.hhplusecommerce.user.infrastruture;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.user.business.service.UserRepository;

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
