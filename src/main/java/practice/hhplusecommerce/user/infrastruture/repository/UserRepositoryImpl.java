package practice.hhplusecommerce.user.infrastruture.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.user.business.UserRepository;
import practice.hhplusecommerce.user.business.entity.User;

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

  @Override
  public Optional<User> findByName(String name) {
    return userJpaRepository.findByName(name);
  }
}
