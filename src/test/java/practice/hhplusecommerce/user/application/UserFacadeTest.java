package practice.hhplusecommerce.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static practice.hhplusecommerce.common.jwt.JwtTokenProvider.BEARER;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.user.application.dto.UserFacadeResponseDto;
import practice.hhplusecommerce.user.application.dto.UserFacadeResponseDto.ToKenResponse;
import practice.hhplusecommerce.user.business.dto.UserServiceResponseDto;
import practice.hhplusecommerce.user.business.dto.UserServiceResponseDto.TokenResponse;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.user.business.service.UserService;

@MockBean(JpaMetamodelMappingContext.class)
public class UserFacadeTest {

  @InjectMocks
  UserFacade userFacade;

  @Mock
  UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void 잔액조회기능테스트_유저정보와잔액이반환되는지() {
    //given
    long accountId = 1L;
    String name = "백현명";
    int amount = 3000;

    //when
    User user = new User(accountId, name, amount);
    when(userService.getUser(accountId)).thenReturn(user);
    UserFacadeResponseDto.Response when = userFacade.getUserAmount(accountId);

    //then
    assertEquals(amount, when.amount());
    assertEquals(accountId, when.id());
    assertEquals(name, when.name());
  }

  @Test
  public void 잔액충전기능테스트_충전이되고_유저정보와잔액이반환되는지() {
    //given
    long accountId = 1L;
    String name = "백현명";
    int amount = 3000;
    int chargeAmount = 3000;

    //when
    User user = new User(accountId, name, amount + chargeAmount);
    when(userService.chargeUserAmount(accountId, chargeAmount)).thenReturn(user);
    UserFacadeResponseDto.Response when = userFacade.chargeUserAmount(accountId, chargeAmount);

    //then
    assertEquals(amount + chargeAmount, when.amount());
    assertEquals(accountId, when.id());
    assertEquals(name, when.name());
  }

  @Test
  public void 로그인기능테스트_엑세스토큰_반환하는지_테스트() {
    //given
    long accountId = 1L;
    String name = "백현명";
    int amount = 3000;
    String AccessToken =  BEARER + "엑세스토큰";

    //when
    UserServiceResponseDto.TokenResponse response = new TokenResponse(accountId, name, amount, AccessToken);
    when(userService.login(name)).thenReturn(response);
    ToKenResponse login = userFacade.login(name);


    //then
    assertEquals(amount, login.amount());
    assertEquals(accountId, login.id());
    assertEquals(name, login.name());
    assertEquals(AccessToken, login.accessToken());
  }
}
