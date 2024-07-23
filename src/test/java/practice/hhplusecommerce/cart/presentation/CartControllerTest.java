package practice.hhplusecommerce.cart.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static practice.hhplusecommerce.iterceptor.JwtTokenInterceptor.TOKEN_INFO;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import practice.hhplusecommerce.cart.application.CartFacade;
import practice.hhplusecommerce.cart.application.dto.requst.CartFacadeRequestDto;
import practice.hhplusecommerce.cart.application.dto.response.CartFacadeResponseDto;
import practice.hhplusecommerce.cart.presentation.dto.CartRequestDto.CartCreate;
import practice.hhplusecommerce.common.jwt.TokenInfoDto;
import practice.hhplusecommerce.iterceptor.JwtTokenInterceptor;

@WebMvcTest(CartController.class)
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(JwtTokenInterceptor.class)
public class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  CartFacade cartFacade;

  @Autowired
  private ObjectMapper objectMapper;

  private TokenInfoDto tokenInfoDto = new TokenInfoDto("백현명", 1L);

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(new CartController(cartFacade))
        .build();
  }

  @Test
  public void 장바구니목록조회_성공() throws Exception {
    //given
    long userId = 1L;

    //when
    List<CartFacadeResponseDto> cartFacadeResponseDtoList = List.of(
        new CartFacadeResponseDto(1L, "꽃병", 5, 4, 1500, 1L)
    );

    when(cartFacade.getCartList(userId)).thenReturn(cartFacadeResponseDtoList);

    //then
    mockMvc.perform(get("/api/cart")
            .requestAttr(TOKEN_INFO, tokenInfoDto))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].name").exists())
        .andExpect(jsonPath("$[0].quantity").exists())
        .andExpect(jsonPath("$[0].stock").exists())
        .andExpect(jsonPath("$[0].productId").exists());
  }

  @Test
  public void 장바구니추가_성공() throws Exception {
    //given
    CartCreate cartCreate = new CartCreate();
    cartCreate.setProductId(1L);
    cartCreate.setQuantity(5);

    //when
    CartFacadeResponseDto cartFacadeResponseDto = new CartFacadeResponseDto(1L, "꽃병", 5, 4, 1500, 1L);
    when(cartFacade.addCart(eq(1L), any(CartFacadeRequestDto.Create.class))).thenReturn(cartFacadeResponseDto);

    //then
    mockMvc.perform(post("/api/cart")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(cartCreate))
            .requestAttr(TOKEN_INFO, tokenInfoDto))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.quantity").exists())
        .andExpect(jsonPath("$.stock").exists())
        .andExpect(jsonPath("$.productId").exists())
        .andExpect(jsonPath("$.price").exists());
  }

  @Test
  public void 장바구니삭제_성공() throws Exception {
    //given
    long cartId = 1L;

    //when
    CartFacadeResponseDto cartFacadeResponseDto = new CartFacadeResponseDto(1L, "꽃병", 5, 4, 1500, 1L);
    when(cartFacade.deleteCart(1L, 1L)).thenReturn(cartFacadeResponseDto);

    //then
    mockMvc.perform(delete("/api/cart/{cart-id}", cartId)
            .requestAttr(TOKEN_INFO, tokenInfoDto))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.quantity").exists())
        .andExpect(jsonPath("$.stock").exists())
        .andExpect(jsonPath("$.productId").exists())
        .andExpect(jsonPath("$.price").exists());
  }
}
