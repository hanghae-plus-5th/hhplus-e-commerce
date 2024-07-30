package practice.hhplusecommerce.user.infrastruture.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import practice.hhplusecommerce.user.business.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

  Optional<User> findByName(String name);

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("SELECT U FROM User U WHERE U.id = :userId")
  Optional<User> findByIdPessimisticLock(Long userId);
}
