package practice.hhplusecommerce.user.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import practice.hhplusecommerce.app.user.presentation.UserController;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void 유저잔액조회_성공() throws Exception {
    //given
    long userId = 1L;

    //when
    mockMvc.perform(get("/api/user/{user-id}/amount", userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.amount").exists());

    //then
  }

  @Test
  public void 유저잔액충전_성공() throws Exception {
    //given
    long userId = 1L;
    int amount = 1500;

    //when
    mockMvc.perform(get("/api/user/{user-id}/amount?amount="+ amount, userId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").exists())
        .andExpect(jsonPath("$.amount").exists());

    //then
  }
}
