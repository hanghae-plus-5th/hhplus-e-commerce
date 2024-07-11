package practice.hhplusecommerce.service.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.service.product.ProductRepository;
import practice.hhplusecommerce.app.service.product.ProductService;
import practice.hhplusecommerce.global.exception.NotFoundException;

@MockBean(JpaMetamodelMappingContext.class)
public class ProductServiceTest {

  @InjectMocks
  ProductService productService;

  @Mock
  ProductRepository productRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void 상품목록조회기능_상품조회되는지_테스트() {
    //given
    Long productId = 1L;
    String productName = "꽃병";
    Integer productPrice = 1500;
    Integer stock = 5;

    //when
    Product product = new Product(productId, productName, productPrice, stock);
    when(productRepository.findAll()).thenReturn(List.of(product));

    List<Product> productList = productService.getProductList();

    //then
    assertEquals(productList.getFirst().getId(), productId);
    assertEquals(productList.getFirst().getName(), productName);
    assertEquals(productList.getFirst().getPrice(), productPrice);
    assertEquals(productList.getFirst().getStock(), stock);
  }

  @Test
  public void 상품조회기능_상품조회되는지_테스트() {
    //given
    Long productId = 1L;
    String productName = "꽃병";
    Integer productPrice = 1500;
    Integer stock = 5;

    //when
    Product product = new Product(productId, productName, productPrice, stock);
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));

    Product when = productService.getProduct(productId);

    //then
    assertEquals(when.getId(), productId);
    assertEquals(when.getName(), productName);
    assertEquals(when.getPrice(), productPrice);
    assertEquals(when.getStock(), stock);
  }

  @Test
  public void 상품조회기능_유저존재하지않을시_에러반환하는지_테스트() {
    //given
    Long productId = 1L;

    Product when = null;
    NotFoundException e = null;

    //when
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    try {
      when = productService.getProduct(productId);
    } catch (NotFoundException bre) {
      e = bre;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "상품이 존재하지 않습니다.");
  }

  @Test
  public void 상품고유번호목록가지고_상품목록조회기능_조회되는지__테스트() {
    //given
    Long productId = 1L;
    String productName = "꽃병";
    Integer productPrice = 1500;
    Integer stock = 5;

    //when
    Product product = new Product(productId, productName, productPrice, stock);

    when(productRepository.findAllByIdIn(List.of(productId))).thenReturn(List.of(product));
    List<Product> productList = productService.getProductListByProductIdList(List.of(productId));

    //then
    assertEquals(productList.getFirst().getId(), productId);
    assertEquals(productList.getFirst().getName(), productName);
    assertEquals(productList.getFirst().getPrice(), productPrice);
    assertEquals(productList.getFirst().getStock(), stock);
  }

  @Test
  public void 상품고유번호목록가지고_상품목록조회기능_상품이존재하지않을경우_에러반환하는지_테스트() {
    //given
    Long productId = 1L;

    List<Product> when = null;
    NotFoundException e = null;
    //when

    when(productRepository.findAllByIdIn(List.of(productId))).thenReturn(Collections.emptyList());

    try {
      when = productService.getProductListByProductIdList(List.of(productId));
    } catch (NotFoundException bre) {
      e = bre;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "상품이 존재하지 않습니다.");
  }
}
