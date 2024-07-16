package practice.hhplusecommerce.order.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import practice.hhplusecommerce.order.presentation.dto.request.OrderRequestDto.OrderCreate;
import practice.hhplusecommerce.order.presentation.dto.request.OrderRequestDto.OrderProductCreate;

@WebMvcTest(OrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper; // ObjectMapper를 사용하여 객체를 JSON으로 변환

  @Test
  public void 주문하기_성공() throws Exception {
    //given
    OrderCreate orderCreate = new OrderCreate();
    orderCreate.setUserId(1L);

    OrderProductCreate orderProductCreate = new OrderProductCreate();
    orderProductCreate.setId(1L);
    orderProductCreate.setQuantity(5);
    orderCreate.getProductList().add(orderProductCreate);

    //when
    mockMvc.perform(post("/api/order")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(orderCreate)))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.orderTotalPrice").exists())
        .andExpect(jsonPath("$.orderProductList").exists())
        .andExpect(jsonPath("$.orderProductList").isArray())
        .andExpect(jsonPath("$.orderProductList[0].id").exists())
        .andExpect(jsonPath("$.orderProductList[0].name").exists())
        .andExpect(jsonPath("$.orderProductList[0].quantity").exists())
        .andExpect(jsonPath("$.orderProductList[0].price").exists());
    //then
  }
}
