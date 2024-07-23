package practice.hhplusecommerce.user.business.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.common.exception.NotFoundException;
import practice.hhplusecommerce.common.jwt.JwtTokenProvider;
import practice.hhplusecommerce.user.business.repository.UserRepository;
import practice.hhplusecommerce.user.business.dto.UserServiceResponseDto;
import practice.hhplusecommerce.user.business.dto.UserServiceResponseDto.TokenResponse;
import practice.hhplusecommerce.user.business.entity.User;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional(readOnly = true)
  public User getUser(Long userId) {
    return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저", true));
  }

  @Transactional
  public User chargeUserAmount(Long userId, Integer chargeAmount) {
    User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("유저", true));
    user.chargeAmount(chargeAmount);
    return user;
  }

  @Transactional(readOnly = true)
  public UserServiceResponseDto.TokenResponse login(String name) {
    User user = userRepository.findByName(name).orElseThrow(() -> new NotFoundException("유저", true));
    String accessToken = jwtTokenProvider.createAccessToken(user.getName(), user.getId());
    UserServiceResponseDto.TokenResponse response =  new TokenResponse(user.getId(), user.getName(), user.getAmount(), accessToken);
    return response;
  }

  @Transactional
  public User saveUser(String userName) {
    User user = new User(null, userName, 0);
    return userRepository.save(user);
  }
}
