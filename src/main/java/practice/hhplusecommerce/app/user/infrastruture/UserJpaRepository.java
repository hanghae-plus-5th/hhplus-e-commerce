package practice.hhplusecommerce.app.user.infrastruture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.user.business.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

}
