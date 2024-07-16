package practice.hhplusecommerce.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.order.business.service.OrderService;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.service.ProductService;

@MockBean(JpaMetamodelMappingContext.class)
public class ProductFaçadeTest {

  @InjectMocks
  ProductFacade ProductFacade;

  @Mock
  ProductService productService;

  @Mock
  OrderService orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  //상품목록조회
  @Test
  public void 상품목록조회시_상품정보가반환되는지() {
    //given
    List<ProductFacadeResponseDto.Response> givenList = List.of(new ProductFacadeResponseDto.Response(1L, "꽃병", 1500, 5));

    //when
    List<Product> productList = List.of(new Product(1L, "꽃병", 1500, 5));
    when(productService.getProductList()).thenReturn(productList);

    List<ProductFacadeResponseDto.Response> whenList = ProductFacade.getProductList();

    //then
    for (int i = 0; i < 1; i++) {
      assertEquals(givenList.get(i).getId(), whenList.get(i).getId());
      assertEquals(givenList.get(i).getName(), whenList.get(i).getName());
      assertEquals(givenList.get(i).getPrice(), whenList.get(i).getPrice());
      assertEquals(givenList.get(i).getStock(), whenList.get(i).getStock());
    }

    verify(productService).getProductList();
  }
}
