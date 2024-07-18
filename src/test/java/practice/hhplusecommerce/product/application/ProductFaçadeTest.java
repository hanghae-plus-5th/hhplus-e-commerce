package practice.hhplusecommerce.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.Tuple;
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
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.Response;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.Top5ProductsLast3DaysResponse;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.service.ProductService;
import practice.hhplusecommerce.support.CustomTuple;

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
  public void 상품목록조회시_상품정보가반환되는지_테스트() {
    //given
    long productId = 1L;
    String productName = "꽃병";
    int productPrice = 1500;
    int productStock = 5;

    List<ProductFacadeResponseDto.Response> givenList = List.of(new ProductFacadeResponseDto.Response(productId, productName, productPrice, productStock));

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

  @Test
  public void 상품상세조회시_상품조회되는지_테스트() {
    //given
    long productId = 1L;
    String productName = "꽃병";
    int productPrice = 1500;
    int productStock = 5;

    //when
    Product product = new Product(productId, productName, productPrice, productStock);
    when(productService.getProduct(1L)).thenReturn(product);

    Response when = ProductFacade.getProduct(productId);

    //then
    assertEquals(when.getId(), productId);
    assertEquals(when.getPrice(), productPrice);
    assertEquals(when.getName(), productName);
    assertEquals(when.getStock(), productStock);
    verify(productService).getProduct(productId);
  }

  @Test
  public void 상위상품조회시_상위상품조회되는지_테스트() {

    // Tuple 객체 생성 및 데이터 설정
    List<Tuple> tupleList = List.of(
        new CustomTuple(1L, "상품1", 1000, 50, 10L),
        new CustomTuple(2L, "상품2", 2000, 30, 5L),
        new CustomTuple(2L, "상품2", 2000, 30, 5L),
        new CustomTuple(2L, "상품2", 2000, 30, 5L),
        new CustomTuple(2L, "상품2", 2000, 30, 5L)
    );

    when(orderService.getTop5ProductsLast3Days()).thenReturn(tupleList);
    List<Top5ProductsLast3DaysResponse> top5ProductsLast3Days = ProductFacade.getTop5ProductsLast3Days();

    for (int i = 0; i < 1; i++) {
      assertEquals(top5ProductsLast3Days.get(i).getProductId(), tupleList.get(i).get("productId"));
      assertEquals(top5ProductsLast3Days.get(i).getProductName(), tupleList.get(i).get("productName"));
      assertEquals(top5ProductsLast3Days.get(i).getProductStock(), tupleList.get(i).get("productStock"));
      assertEquals(top5ProductsLast3Days.get(i).getProductPrice(), tupleList.get(i).get("productPrice"));
    }
    verify(orderService).getTop5ProductsLast3Days();
  }

}
