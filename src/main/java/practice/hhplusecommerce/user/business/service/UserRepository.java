package practice.hhplusecommerce.user.business.service;

import java.util.Optional;
import practice.hhplusecommerce.user.business.entity.User;

public interface UserRepository {

  Optional<User> findById(long userId);

  User save(User user);
}
