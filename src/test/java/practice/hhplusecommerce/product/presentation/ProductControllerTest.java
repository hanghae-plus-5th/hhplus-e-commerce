package practice.hhplusecommerce.product.presentation;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import practice.hhplusecommerce.common.jwt.JwtTokenProvider;
import practice.hhplusecommerce.iterceptor.JwtTokenInterceptor;
import practice.hhplusecommerce.product.application.ProductFacade;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.Response;
import practice.hhplusecommerce.product.presentation.request.ProductRequestDto;

@WebMvcTest(ProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
@MockBean(JwtTokenProvider.class) // 필요한 빈들을 MockBean으로 등록
@MockBean(JwtTokenInterceptor.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductFacade productFacade;

  @Autowired
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productFacade)).build();
  }

  @Test
  public void 상품목록조회기능_성공케이스() throws Exception {
    //given
    List<Response> givenList = List.of(new ProductFacadeResponseDto.Response(1L, "꽃병", 1500, 5));

    //when
    when(productFacade.getProductList()).thenReturn(givenList);

    //then
    mockMvc.perform(get("/api/product"))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].name").exists())
        .andExpect(jsonPath("$[0].price").exists())
        .andExpect(jsonPath("$[0].stock").exists());

    verify(productFacade).getProductList();
  }

  @Test
  public void 상품상세조회기능_성공케이스() throws Exception {
    //given
    Long productId = 1L;

    //when
    when(productFacade.getProduct(productId)).thenReturn(new ProductFacadeResponseDto.Response(1L, "꽃병", 1500, 5));

    //then
    mockMvc.perform(get("/api/product/{product-id}", productId))
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.price").exists())
        .andExpect(jsonPath("$.stock").exists());

    verify(productFacade).getProduct(productId);
  }

  @Test
  public void 인기판매상품조회기능_성공케이스() throws Exception {
    //given
    List<ProductFacadeResponseDto.Top5ProductsLast3DaysResponse> givenList = List.of(new ProductFacadeResponseDto.Top5ProductsLast3DaysResponse(1L, "꽃병", 1500, 5, 20L));

    //when
    when(productFacade.getTop5ProductsLast3Days()).thenReturn(givenList);

    //then
    mockMvc.perform(get("/api/product/top"))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].name").exists())
        .andExpect(jsonPath("$[0].price").exists())
        .andExpect(jsonPath("$[0].stock").exists());
  }

  @Test
  @DisplayName("상품수정기능 성공 테스트")
  public void updateProduct_mockMvc_success() throws Exception {
    //given
    Long productId = 1L;
    ProductRequestDto.Update update = new ProductRequestDto.Update("상품명", 1500, 5);

    //when
    doNothing().when(productFacade).updateProduct(any(Long.class), any(ProductRequestDto.Update.class));

    //then
    mockMvc.perform(put("/api/product/{product-id}", productId)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(update)))
        .andExpect(status().isOk());

    verify(productFacade).updateProduct(any(Long.class), any(ProductRequestDto.Update.class));
  }

  @Test
  @DisplayName("RequestBody의 필드 값 하나라도 null이라면 400에러로 실패하는지 테스트")
  public void updateProduct_mockMvc_400_error_Fail() throws Exception {
    //given
    Long productId = 1L;
    ProductRequestDto.Update update1 = new ProductRequestDto.Update(null, 1500, 5);
    ProductRequestDto.Update update2 = new ProductRequestDto.Update("상품명", null, 5);
    ProductRequestDto.Update update3 = new ProductRequestDto.Update("상품명", 1500, null);

    //when
    doNothing().when(productFacade).updateProduct(any(Long.class), any(ProductRequestDto.Update.class));

    //then
    mockMvc.perform(put("/api/product/{product-id}", productId)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(update1)))
        .andExpect(status().is4xxClientError());

    mockMvc.perform(put("/api/product/{product-id}", productId)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(update2)))
        .andExpect(status().is4xxClientError());

    mockMvc.perform(put("/api/product/{product-id}", productId)
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(update3)))
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("상품삭제 성공 테스트")
  public void deleteProduct_mockMvc_success() throws Exception {
    //given
    Long productId = 1L;

    //when
    doNothing().when(productFacade).deleteProduct(any(Long.class));

    //then
    mockMvc.perform(delete("/api/product/{product-id}", productId))
        .andExpect(status().isOk());

    verify(productFacade).deleteProduct(any(Long.class));
  }
}
