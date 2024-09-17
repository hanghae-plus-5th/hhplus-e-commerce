package practice.hhplusecommerce.common.utils;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtils {

	private static final String LOCK_KEY_PREFIX = "lock:";

	public static boolean lock(String lockKey, String lockValue, long expireTime, StringRedisTemplate redisTemplate) {
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		Boolean isLocked = ops.setIfAbsent(LOCK_KEY_PREFIX + lockKey, lockValue, expireTime, TimeUnit.MILLISECONDS);
		return isLocked != null && isLocked;
	}

	public static boolean unlock(String lockKey, String lockValue, StringRedisTemplate redisTemplate) {
		ValueOperations<String, String> ops = redisTemplate.opsForValue();
		String currentLockValue = ops.get(LOCK_KEY_PREFIX + lockKey);
		if (lockValue.equals(currentLockValue)) {
			redisTemplate.delete(LOCK_KEY_PREFIX + lockKey);
			return true;
		}
		return false;
	}
}
