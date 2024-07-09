package practice.hhplusecommerce.presentation.product;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import practice.hhplusecommerce.app.presentation.product.ProductController;

@WebMvcTest(ProductController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void 상품목록조회_성공() throws Exception {
    //given
    //when
    mockMvc.perform(get("/api/product"))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].name").exists())
        .andExpect(jsonPath("$[0].price").exists())
        .andExpect(jsonPath("$[0].stock").exists());
    //then
  }

  @Test
  public void 인기판매상품조회_성공() throws Exception {
    //given
    //when
    mockMvc.perform(get("/api/product"))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$[0].id").exists())
        .andExpect(jsonPath("$[0].name").exists())
        .andExpect(jsonPath("$[0].price").exists())
        .andExpect(jsonPath("$[0].stock").exists());
    //then
  }
}
