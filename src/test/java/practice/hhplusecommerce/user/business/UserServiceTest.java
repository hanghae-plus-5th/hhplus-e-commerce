package practice.hhplusecommerce.user.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static practice.hhplusecommerce.common.jwt.JwtTokenProvider.BEARER;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.common.jwt.JwtTokenProvider;
import practice.hhplusecommerce.user.business.dto.UserServiceResponseDto.TokenResponse;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.common.exception.BadRequestException;
import practice.hhplusecommerce.common.exception.NotFoundException;

@MockBean(JpaMetamodelMappingContext.class)
public class UserServiceTest {

  @InjectMocks
  UserService userService;

  @Mock
  UserRepository userRepository;

  @Mock
  JwtTokenProvider jwtTokenProvider;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Test
  public void 잔액조회기능_유저조회되는지_테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";
    int amount = 1500;

    //when
    User user = new User(userId, userName, amount);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    User when = userService.getUser(userId);

    //then
    assertEquals(when.getId(), userId);
    assertEquals(when.getName(), userName);
    assertEquals(when.getAmount(), amount);
  }

  @Test
  public void 잔액조회기능_유저를_못찾았을경우_에러반환하는지_테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";
    int amount = 1500;

    //when
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    NotFoundException e = null;
    User user = null;
    try {
      user = userService.getUser(userId);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(user);
    assertNotNull(e);
    assertEquals(e.getMessage(), "유저가 존재하지 않습니다.");
  }

  @Test
  public void 잔액충전기능_충전되는지_테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";
    int amount = 1500;
    int chargeAmount = 2500;

    //when
    User user = new User(userId, userName, amount);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    User when = userService.chargeUserAmount(userId, chargeAmount);

    //then
    assertEquals(when.getId(), userId);
    assertEquals(when.getName(), userName);
    assertEquals(when.getAmount(), amount + chargeAmount);
  }

  @Test
  public void 잔액충전기능_유저를_못찾았을경우_에러반환하는지_테스트() {
    //given
    long userId = 1L;
    int chargeAmount = -1500;

    //when
    when(userRepository.findById(userId)).thenReturn(Optional.empty());

    NotFoundException e = null;
    User user = null;
    try {
      user = userService.chargeUserAmount(userId, chargeAmount);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(user);
    assertNotNull(e);
    assertEquals(e.getMessage(), "유저가 존재하지 않습니다.");
  }

  @Test
  public void 잔액충전기능_충전시_음수일경우_에러반환하는지_테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";
    int amount = 1500;
    int chargeAmount = -1500;

    //when
    User user = new User(userId, userName, amount);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    User when = null;
    Exception e = null;
    try {
      when = userService.chargeUserAmount(userId, chargeAmount);
    } catch (BadRequestException bre) {
      e = bre;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "1원 이상만 충전이 가능합니다.");
  }

  @Test
  public void 잔액충전기능_충전시_0일경우_에러반환하는지_테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";
    int amount = 1500;
    int chargeAmount = 0;

    //when
    User user = new User(userId, userName, amount);
    when(userRepository.findById(userId)).thenReturn(Optional.of(user));

    User when = null;
    Exception e = null;
    try {
      when = userService.chargeUserAmount(userId, chargeAmount);
    } catch (BadRequestException bre) {
      e = bre;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "1원 이상만 충전이 가능합니다.");
  }

  @Test
  public void 로그인기능_엑세스토큰_반환하는지_테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";
    int amount = 1500;
    String AccessToken =  BEARER + "엑세스토큰";

    //when
    User user = new User(userId, userName, amount);
    when(userRepository.findByName(userName)).thenReturn(Optional.of(user));
    when(jwtTokenProvider.createAccessToken(userName, userId)).thenReturn(AccessToken);

    TokenResponse login = userService.login(userName);

    //then
    assertEquals(login.id(), userId);
    assertEquals(login.name(), userName);
    assertEquals(login.amount(), amount);
    assertEquals(login.accessToken(), AccessToken);
  }

  @Test
  public void 로그인기능_유저정보없을시_에러반환하는지_테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";

    //when
    when(userRepository.findByName(userName)).thenReturn(Optional.empty());

    //then
    NotFoundException e = null;
    try {
      userService.login(userName);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNotNull(e);
    assertEquals(e.getMessage(), "유저가 존재하지 않습니다.");
  }

  @Test
  public void 회원가입기능_유저저장되는지_테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";
    int amount = 1500;

    //when
    User when = new User(userId, userName, amount);
    when(userRepository.save(any(User.class))).thenReturn(when);

    User then = userService.saveUser(userName);

    //then
    assertEquals(then.getId(), when.getId());
    assertEquals(then.getName(), when.getName());
    assertEquals(then.getAmount(), when.getAmount());
  }
}
