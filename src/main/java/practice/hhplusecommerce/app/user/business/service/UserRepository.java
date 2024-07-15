package practice.hhplusecommerce.app.user.business.service;

import java.util.Optional;
import practice.hhplusecommerce.app.user.business.entity.User;

public interface UserRepository {

  Optional<User> findById(long userId);

  User save(User user);
}
