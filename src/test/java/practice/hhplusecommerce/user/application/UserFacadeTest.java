package practice.hhplusecommerce.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.user.application.dto.UserFacadeDto;
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
    UserFacadeDto when = userFacade.getUserAmount(accountId);

    //then
    assertEquals(amount, when.amount());
    assertEquals(accountId, when.id());
    assertEquals(name, when.name());
  }
}
