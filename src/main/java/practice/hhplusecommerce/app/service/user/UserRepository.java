package practice.hhplusecommerce.app.service.user;

import java.util.Optional;
import practice.hhplusecommerce.app.domain.user.User;

public interface UserRepository {

  Optional<User> findById(long userId);

  User save(User user);
}
