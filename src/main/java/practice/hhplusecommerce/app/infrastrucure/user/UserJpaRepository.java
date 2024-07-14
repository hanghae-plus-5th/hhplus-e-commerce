package practice.hhplusecommerce.app.infrastrucure.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.app.domain.user.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

}
