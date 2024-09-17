package practice.hhplusecommerce.user.business.service;

import java.util.concurrent.atomic.AtomicReference;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.common.exception.NotFoundException;
import practice.hhplusecommerce.common.handler.TransactionalHandler;
import practice.hhplusecommerce.common.jwt.JwtTokenProvider;
import practice.hhplusecommerce.common.utils.RedisUtils;
import practice.hhplusecommerce.user.business.dto.UserServiceResponseDto.TokenResponse;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.user.business.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private static final Logger log = LoggerFactory.getLogger(UserService.class);
  private final StringRedisTemplate redisTemplate;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;
  private final TransactionalHandler transactionalHandler;

  @Transactional(readOnly = true)
  public User getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저", true));
  }

  @Transactional
  public User chargeUserAmount(Long userId, Integer chargeAmount) {
    User user = userRepository.findByIdPessimisticLock(userId).orElseThrow(() -> new NotFoundException("유저", true));
    user.chargeAmount(chargeAmount);
    return user;
  }

	public User chargeUserAmountForRedis(Long userId, Integer chargeAmount) {
		boolean lock = RedisUtils.lock(userId.toString(), userId.toString(), 10000L, redisTemplate);
		AtomicReference<User> user = new AtomicReference<>();
		try {
			transactionalHandler.runWithTransaction(() -> {
				if (lock) {
					user.set(userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저", true)));
					user.get().chargeAmount(chargeAmount);
				}
			});

		} catch (Exception e) {
			log.error(e.getMessage());
			throw new RuntimeException(e);
		} finally {
			RedisUtils.unlock(userId.toString(), userId.toString(), redisTemplate);
		}

		return user.get();
	}

  @Transactional(readOnly = true)
  public TokenResponse login(String name) {
    User user = userRepository.findByName(name).orElseThrow(() -> new NotFoundException("유저", true));
    String accessToken = jwtTokenProvider.createAccessToken(user.getName(), user.getId());
    TokenResponse response = new TokenResponse(user.getId(), user.getName(), user.getAmount(), accessToken);
    return response;
  }

  @Transactional
  public User saveUser(String userName) {
    User user = new User(null, userName, 0);
    return userRepository.save(user);
  }

  public void decreaseAmount(Long userId, int totalProductPrice) {
    User user = userRepository.findByIdPessimisticLock(userId).orElseThrow(() -> new NotFoundException("유저", true));
    user.decreaseAmount(totalProductPrice);
  }
}
