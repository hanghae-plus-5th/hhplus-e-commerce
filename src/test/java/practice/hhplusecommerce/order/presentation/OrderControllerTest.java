package practice.hhplusecommerce.order.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static practice.hhplusecommerce.iterceptor.JwtTokenInterceptor.TOKEN_INFO;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import practice.hhplusecommerce.common.jwt.TokenInfoDto;
import practice.hhplusecommerce.iterceptor.JwtTokenInterceptor;
import practice.hhplusecommerce.order.application.OrderFacade;
import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto;
import practice.hhplusecommerce.order.application.dto.response.OrderFacadeResponseDto;
import practice.hhplusecommerce.order.application.dto.response.OrderFacadeResponseDto.OrderProductResponse;
import practice.hhplusecommerce.order.application.dto.response.OrderFacadeResponseDto.OrderResponse;
import practice.hhplusecommerce.order.presentation.dto.OrderRequestDto.OrderCreate;
import practice.hhplusecommerce.order.presentation.dto.OrderRequestDto.OrderProductCreate;

@WebMvcTest(OrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(JwtTokenInterceptor.class)
public class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  OrderFacade orderFacade;

  @Autowired
  private ObjectMapper objectMapper;

  private TokenInfoDto tokenInfoDto = new TokenInfoDto("백현명", 1L);

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderFacade))
        .build();
  }

  @Test
  public void 주문하기_성공() throws Exception {
    //given
    OrderCreate givenOrderCreate = new OrderCreate();
    givenOrderCreate.setUserId(1L);

    OrderProductCreate givenOrderProductCreate = new OrderProductCreate();
    givenOrderProductCreate.setId(1L);
    givenOrderProductCreate.setQuantity(5);
    givenOrderCreate.getProductList().add(givenOrderProductCreate);


    //when
    OrderFacadeRequestDto.Create create = new OrderFacadeRequestDto.Create();
    OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderFacadeRequestDto.OrderProductCreate();
    orderProductCreate.setId(1L);
    orderProductCreate.setQuantity(5);
    create.getProductList().add(orderProductCreate);


    OrderFacadeResponseDto.OrderResponse orderResponse = new OrderResponse();
    orderResponse.setId(1L);
    orderResponse.setOrderTotalPrice(1500);


    OrderFacadeResponseDto.OrderProductResponse orderProductResponse = new OrderProductResponse();
    orderProductResponse.setId(1L);
    orderProductResponse.setName("꽃병");
    orderProductResponse.setPrice(1500);
    orderProductResponse.setQuantity(5);
    orderResponse.getOrderProductList().add(orderProductResponse);

    create.setProductList(List.of());
    when(orderFacade.order(eq(1L), any(OrderFacadeRequestDto.Create.class))).thenReturn(orderResponse);

    //then
    mockMvc.perform(post("/api/order")
            .contentType("application/json")
            .requestAttr(TOKEN_INFO, tokenInfoDto)
            .content(objectMapper.writeValueAsString(givenOrderCreate)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.orderTotalPrice").exists())
        .andExpect(jsonPath("$.orderProductList").exists())
        .andExpect(jsonPath("$.orderProductList").isArray())
        .andExpect(jsonPath("$.orderProductList[0].id").exists())
        .andExpect(jsonPath("$.orderProductList[0].name").exists())
        .andExpect(jsonPath("$.orderProductList[0].quantity").exists())
        .andExpect(jsonPath("$.orderProductList[0].price").exists());
  }
}
