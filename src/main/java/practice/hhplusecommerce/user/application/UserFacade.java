package practice.hhplusecommerce.user.application;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import practice.hhplusecommerce.user.application.dto.UserFacadeDtoResponseMapper;
import practice.hhplusecommerce.user.application.dto.UserFacadeResponseDto;
import practice.hhplusecommerce.user.business.service.UserService;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final RedissonClient redissonClient;

    public UserFacadeResponseDto.Response getUserAmount(Long userId) {
        return UserFacadeDtoResponseMapper.toUserFacadeDto(userService.getUser(userId));
    }

    public UserFacadeResponseDto.Response chargeUserAmount(Long userId, Integer chargeAmount) {
        return UserFacadeDtoResponseMapper.toUserFacadeDto(userService.chargeUserAmount(userId, chargeAmount));
    }

    public UserFacadeResponseDto.Response chargeUserAmountOfRedis(Long userId, Integer chargeAmount) {

        RLock lock = redissonClient.getLock(userId.toString());

        try {
            //획득시도 시간, 락 점유 시간
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        return UserFacadeDtoResponseMapper.toUserFacadeDto(userService.chargeUserAmountOfRedis(userId, chargeAmount));
    }

    public UserFacadeResponseDto.ToKenResponse login(String userName) {
        return UserFacadeDtoResponseMapper.toUserFacadeDto(userService.login(userName));
    }

    public void join(String userName) {
        userService.saveUser(userName);
    }
}
