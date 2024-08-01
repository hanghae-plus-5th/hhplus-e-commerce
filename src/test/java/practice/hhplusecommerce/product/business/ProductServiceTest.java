package practice.hhplusecommerce.product.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.common.exception.BadRequestException;
import practice.hhplusecommerce.common.exception.NotFoundException;
import practice.hhplusecommerce.product.business.dto.ProductCommand;
import practice.hhplusecommerce.product.business.dto.ProductCommand.Update;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.repository.ProductRepository;
import practice.hhplusecommerce.product.business.service.ProductService;

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
  @DisplayName("상품상세조회 API 성공테스트")
  public void 상품상세조회기능_상품조회되는지_테스트() {
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
  public void 상품조회기능_상품존재하지않을시_에러반환하는지_테스트() {
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

  @Test
  @DisplayName("수정한대로 수정이 되는지 테스트")
  public void updateProduct_success() {
    //given
    long productId = 1L;
    String name = "상품명";
    int price = 1500;
    int stock = 5;

    Product product = new Product(productId, "업데이트전", 1300, 3);
    ProductCommand.Update update = new Update(productId, name, price, stock);

    //when
    when(productRepository.findById(productId)).thenReturn(Optional.of(product));
    Product updatedProduct = productService.updateProduct(update);

    //then
    assertEquals(updatedProduct.getId(), productId);
    assertEquals(updatedProduct.getName(), name);
    assertEquals(updatedProduct.getPrice(), price);
    assertEquals(updatedProduct.getStock(), stock);
  }

  @Test
  @DisplayName("재고와 가격이 0미만 일 경우 실패하는지 테스트")
  public void updateProduct_price_lessThan0_fail() {
    //given
    long productId = 1L;
    String name = "상품명";
    int price = -1;
    int stock = 1400;

    Product product = new Product(productId, "업데이트전", 1300, 3);
    ProductCommand.Update update = new Update(productId, name, price, stock);

    Product updatedProduct = null;
    BadRequestException e = null;

    //when
    try {
      when(productRepository.findById(productId)).thenReturn(Optional.of(product));
      updatedProduct = productService.updateProduct(update);
    } catch (BadRequestException bre){
      e = bre;
    }

    //then
    assertNull(updatedProduct);
    assertNotNull(e);
    assertEquals(e.getMessage(), "가격이 0보다 작을 수 없습니다.");
  }

  @Test
  @DisplayName("재고와 재고 0미만 일 경우 실패하는지 테스트 ")
  public void updateProduct_stock_lessThan0_fail() {
    //given
    long productId = 1L;
    String name = "상품명";
    int price = 1400;
    int stock = -1;

    Product product = new Product(productId, "업데이트전", 1300, 3);
    ProductCommand.Update update = new Update(productId, name, price, stock);

    Product updatedProduct = null;
    BadRequestException e = null;

    //when

    try {
      when(productRepository.findById(productId)).thenReturn(Optional.of(product));
      updatedProduct = productService.updateProduct(update);
    } catch (BadRequestException bre){
      e = bre;
    }

    //then
    assertNull(updatedProduct);
    assertNotNull(e);
    assertEquals(e.getMessage(), "재고가 0보다 작을 수 없습니다.");
  }

  @Test
  @DisplayName("삭제기능 삭제 성공 테스트")
  public void deleteProduct_success() {
    //given
    long productId = 1L;

    //when
    doNothing().when(productRepository).deleteById(any(Long.class));
    productService.deleteProduct(productId);

    //then
    verify(productRepository).deleteById(any(Long.class));
  }
}
