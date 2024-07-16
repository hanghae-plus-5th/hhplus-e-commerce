package practice.hhplusecommerce.cart.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import practice.hhplusecommerce.cart.presentation.dto.request.CartRequestDto.CartCreate;

@WebMvcTest(CartController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void 장바구니목록조회_성공() throws Exception {
    //given
    long userId = 1L;

    //when
    mockMvc.perform(get("/api/cart?userId=" + userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].name").exists())
        .andExpect(jsonPath("$[0].quantity").exists())
        .andExpect(jsonPath("$[0].stock").exists())
        .andExpect(jsonPath("$[0].productId").exists());

    //then
  }

  @Test
  public void 장바구니추가_성공() throws Exception {
    //given
    CartCreate cartCreate = new CartCreate();
    cartCreate.setProductId(1L);
    cartCreate.setQuantity(5);
    cartCreate.setUserId(1L);

    //when
    mockMvc.perform(post("/api/cart")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(cartCreate)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.quantity").exists())
        .andExpect(jsonPath("$.stock").exists())
        .andExpect(jsonPath("$.productId").exists())
        .andExpect(jsonPath("$.price").exists());
    //then
  }

  @Test
  public void 장바구니삭제_성공() throws Exception {
    //given
    long cartId = 1L;
    long userId = 1L;

    //when
    mockMvc.perform(delete("/api/cart/{cart-id}?userId=" + userId, cartId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.quantity").exists())
        .andExpect(jsonPath("$.stock").exists())
        .andExpect(jsonPath("$.productId").exists())
        .andExpect(jsonPath("$.price").exists());
    //then
  }
}
