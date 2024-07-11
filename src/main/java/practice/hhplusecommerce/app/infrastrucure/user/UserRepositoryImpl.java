package practice.hhplusecommerce.app.infrastrucure.user;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.service.user.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

  @Override
  public Optional<User> findById(long userId) {
    return Optional.empty();
  }
}
