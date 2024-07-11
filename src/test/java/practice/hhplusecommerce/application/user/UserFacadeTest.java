package practice.hhplusecommerce.application.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.app.application.user.UserFacade;
import practice.hhplusecommerce.app.application.user.dto.UserFacadeDto;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.service.user.UserService;

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
    UserFacadeDto when = userFacade.getUserAmount(accountId);

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
    User user = new User(accountId, name, amount);
    when(userService.getUser(accountId)).thenReturn(user);
    UserFacadeDto when = userFacade.chargeUserAmount(accountId, chargeAmount);

    //then
    assertEquals(amount + chargeAmount, when.amount());
    assertEquals(accountId, when.id());
    assertEquals(name, when.name());
  }
}
