package practice.hhplusecommerce.user.presentation;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static practice.hhplusecommerce.common.jwt.JwtTokenProvider.BEARER;
import static practice.hhplusecommerce.iterceptor.JwtTokenInterceptor.TOKEN_INFO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import practice.hhplusecommerce.common.jwt.JwtTokenProvider;
import practice.hhplusecommerce.common.jwt.TokenInfoDto;
import practice.hhplusecommerce.iterceptor.JwtTokenInterceptor;
import practice.hhplusecommerce.user.application.UserFacade;
import practice.hhplusecommerce.user.application.dto.UserFacadeResponseDto;
import practice.hhplusecommerce.user.application.dto.UserFacadeResponseDto.Response;
import practice.hhplusecommerce.user.presentation.dto.UserRequestDto;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(JwtTokenProvider.class) // 필요한 빈들을 MockBean으로 등록
@MockBean(JwtTokenInterceptor.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserFacade userFacade;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private JwtTokenProvider jwtTokenProvider;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userFacade))
        .build();
  }

  @Test
  public void 로그인기능_성공() throws Exception {
    //given
    long userId = 1L;
    int amount = 0;
    String userName = "백현명";

    //when
    UserFacadeResponseDto.ToKenResponse when = new UserFacadeResponseDto.ToKenResponse(userId, userName, amount, BEARER + " 엑세트토큰");
    UserRequestDto.Request joinRequest = new UserRequestDto.Request(userName);
    when(userFacade.login(joinRequest.getName())).thenReturn(when);

    mockMvc.perform(post("/api/user/login")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(joinRequest)))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.accessToken").exists())
        .andExpect(jsonPath("$.amount").exists());

    //then
    verify(userFacade).login(joinRequest.getName());
  }

  @Test
  public void 회원가입기능_성공() throws Exception {
    // given
    String userName = "백현명";

    // when
    UserRequestDto.Request joinRequest = new UserRequestDto.Request(userName);

    // then
    mockMvc.perform(post("/api/user/join")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(joinRequest)))
        .andExpect(status().isOk());
  }


  @Test
  public void 유저잔액조회_성공() throws Exception {
    //given
    long userId = 1L;
    int amount = 3000;
    String userName = "백현명";

    //when
    Response when = new UserFacadeResponseDto.Response(userId, userName, amount);
    when(userFacade.getUserAmount(userId)).thenReturn(when);

    //then
    mockMvc.perform(get("/api/user/amount")
            .requestAttr(TOKEN_INFO, createTokenInfoDto(userName, userId)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.amount").exists());

    verify(userFacade).getUserAmount(userId);
  }

  @Test
  public void 유저잔액충전_성공() throws Exception {
    //given
    long userId = 1L;
    int amount = 3000;
    int chargeAmount = 1500;
    String userName = "백현명";

    //when
    UserFacadeResponseDto.Response when = new UserFacadeResponseDto.Response(userId, userName, amount + chargeAmount);
    when(userFacade.chargeUserAmount(userId, chargeAmount)).thenReturn(when);

    //then
    mockMvc.perform(post("/api/user/amount?amount=" + chargeAmount)
            .requestAttr(TOKEN_INFO, createTokenInfoDto(userName, userId)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.amount").exists());

    verify(userFacade).chargeUserAmount(userId, chargeAmount);
  }


  public TokenInfoDto createTokenInfoDto(String userName, Long userId) {
    return new TokenInfoDto(userName, userId);
  }
}
