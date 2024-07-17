package practice.hhplusecommerce.user.infrastruture;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.user.business.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

  Optional<User> findByName(String name);
}
