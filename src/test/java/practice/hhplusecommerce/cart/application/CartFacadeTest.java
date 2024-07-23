package practice.hhplusecommerce.cart.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.cart.application.dto.requst.CartFacadeRequestDto;
import practice.hhplusecommerce.cart.application.dto.response.CartFacadeResponseDto;
import practice.hhplusecommerce.cart.business.dto.CartServiceResponseDto;
import practice.hhplusecommerce.cart.business.dto.CartServiceResponseDto.Response;
import practice.hhplusecommerce.cart.business.service.CartService;
import practice.hhplusecommerce.common.exception.BadRequestException;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.service.ProductService;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.user.business.service.UserService;

@MockBean(JpaMetamodelMappingContext.class)
public class CartFacadeTest {

  @InjectMocks
  CartFacade cartFacade;

  @Mock
  CartService cartService;

  @Mock
  ProductService productService;

  @Mock
  UserService userService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void 장바구니조회기능_장바구니목록이조회되는지_테스트() {
    //given
    long userId = 1L;
    String name = "백현명";
    int amount = 3000;

    long productId = 1L;
    String productName = "꽃병";
    int price = 1500;
    int stock = 5;

    int quantity = 15;
    long cartId = 1;

    //when
    User user = new User(userId, name, amount);

    when(userService.getUser(userId)).thenReturn(user);

    Response response = new Response();
    response.setId(cartId);
    response.setName(productName);
    response.setPrice(price);
    response.setStock(stock);
    response.setProductId(productId);
    when(cartService.getCartList(userId)).thenReturn(List.of(response));

    List<CartFacadeResponseDto> cartFacadeResponseDtoList = cartFacade.getCartList(userId);

    //then
    assertEquals(cartFacadeResponseDtoList.get(0).id(), cartId);
    assertEquals(cartFacadeResponseDtoList.get(0).productId(), productId);
    assertEquals(cartFacadeResponseDtoList.get(0).stock(), stock);
    assertEquals(cartFacadeResponseDtoList.get(0).name(), productName);
    assertEquals(cartFacadeResponseDtoList.get(0).price(), price);
  }

  @Test
  public void 장바구니담기기능_장바구니에추가되는지_테스트() {
    //given
    long userId = 1L;
    String name = "백현명";
    int amount = 3000;

    long productId = 1L;
    String productName = "꽃병";
    int price = 1500;
    int stock = 20;

    int quantity = 15;
    long cartId = 1;

    //when
    User user = new User(userId, name, amount);
    Product product = new Product(productId, productName, price, stock);
    CartServiceResponseDto.Response cartResponse = new CartServiceResponseDto.Response();
    cartResponse.setId(cartId);
    cartResponse.setQuantity(quantity);
    cartResponse.setName(productName);
    cartResponse.setProductId(productId);
    cartResponse.setPrice(price);
    cartResponse.setStock(stock);

    when(userService.getUser(userId)).thenReturn(user);
    when(productService.getProduct(productId)).thenReturn(product);

    when(cartService.createCart(quantity, user, product)).thenReturn(cartResponse);

    CartFacadeRequestDto.Create create = new CartFacadeRequestDto.Create();
    create.setQuantity(quantity);
    create.setProductId(productId);
    CartFacadeResponseDto cartFacadeResponseDto = cartFacade.addCart(userId, create);

    //then
    assertEquals(cartFacadeResponseDto.id(), cartId);
    assertEquals(cartFacadeResponseDto.price(), price);
    assertEquals(cartFacadeResponseDto.productId(), productId);
    assertEquals(cartFacadeResponseDto.stock(), stock);
    assertEquals(cartFacadeResponseDto.name(), productName);
  }

  @Test
  public void 장바구니담기기능_재고없는상품_장바구니에담을시_예외처리되는지_테스트() {
    //given
    long userId = 1L;
    String name = "백현명";
    int amount = 3000;

    long productId = 1L;
    String productName = "꽃병";
    int price = 1500;
    int stock = 0;

    int quantity = 15;
    long cartId = 1;

    //when
    User user = new User(userId, name, amount);
    Product product = new Product(productId, productName, price, stock);
    CartServiceResponseDto.Response cartResponse = new CartServiceResponseDto.Response();
    cartResponse.setId(cartId);
    cartResponse.setQuantity(quantity);
    cartResponse.setName(productName);
    cartResponse.setProductId(productId);
    cartResponse.setPrice(price);
    cartResponse.setStock(stock);

    when(userService.getUser(userId)).thenReturn(user);
    when(productService.getProduct(productId)).thenReturn(product);

    when(cartService.createCart(quantity, user, product)).thenReturn(cartResponse);

    CartFacadeRequestDto.Create create = new CartFacadeRequestDto.Create();
    create.setQuantity(quantity);
    create.setProductId(productId);

    BadRequestException badRequestException = null;
    CartFacadeResponseDto cartFacadeResponseDto = null;
    try {
      cartFacadeResponseDto = cartFacade.addCart(userId, create);
    } catch (BadRequestException e) {
      badRequestException = e;
    }

    //then
    assertNull(cartFacadeResponseDto);
    assertEquals(badRequestException.getMessage(), "재고가 부족합니다.");
    assertEquals(badRequestException.getClass(), BadRequestException.class);
  }

  @Test
  public void 장바구니삭제기능_장바구니삭제되는지_테스트() {
    //given
    long userId = 1L;
    String name = "백현명";
    int amount = 3000;

    long productId = 1L;
    String productName = "꽃병";
    int price = 1500;
    int stock = 5;

    int quantity = 15;
    long cartId = 1;

    //when
    User user = new User(userId, name, amount);
    CartServiceResponseDto.Response cartResponse = new CartServiceResponseDto.Response();
    cartResponse.setId(cartId);
    cartResponse.setQuantity(quantity);
    cartResponse.setName(productName);
    cartResponse.setProductId(productId);
    cartResponse.setPrice(price);
    cartResponse.setStock(stock);

    ;
    when(userService.getUser(userId)).thenReturn(user);
    when(cartService.getCart(cartId)).thenReturn(cartResponse);
    when(cartService.deleteCart(cartId)).thenReturn(cartResponse);
    CartFacadeResponseDto cartFacadeResponseDto = cartFacade.deleteCart(cartId, userId);

    //then
    assertEquals(cartFacadeResponseDto.id(), cartId);
    assertEquals(cartFacadeResponseDto.name(), productName);
    assertEquals(cartFacadeResponseDto.price(), price);
    assertEquals(cartFacadeResponseDto.stock(), stock);
    assertEquals(cartFacadeResponseDto.productId(), productId);
  }
}
