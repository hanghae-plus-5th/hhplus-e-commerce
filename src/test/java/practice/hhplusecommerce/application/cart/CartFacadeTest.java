package practice.hhplusecommerce.application.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.app.application.cart.CartFacade;
import practice.hhplusecommerce.app.application.cart.dto.requst.CartFacadeRequestDto;
import practice.hhplusecommerce.app.application.cart.dto.response.CartFacadeResponseDto;
import practice.hhplusecommerce.app.domain.cart.Cart;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.service.cart.CartService;
import practice.hhplusecommerce.app.service.product.ProductService;
import practice.hhplusecommerce.app.service.product.dto.request.ProductServiceRequestDto;
import practice.hhplusecommerce.app.service.product.dto.request.ProductServiceRequestDto.productStockResponse;
import practice.hhplusecommerce.app.service.user.UserService;

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
    Product product = new Product(productId, productName, price, stock);
    List<Cart> cartList = List.of(new Cart(cartId, quantity, user, product));
    when(userService.getUser(userId)).thenReturn(user);
    when(cartService.getCartList(userId)).thenReturn(cartList);

    ProductServiceRequestDto.productStockResponse productStockResponse = new productStockResponse();
    productStockResponse.setStock(stock);
    productStockResponse.setProductId(productId);
    when(productService.getStockList(List.of(productId))).thenReturn(List.of(productStockResponse));
    List<CartFacadeResponseDto> cartFacadeResponseDtoList = cartFacade.getCartList(userId);

    //then
    assertEquals(cartFacadeResponseDtoList.get(0).id(), cartId);
    assertEquals(cartFacadeResponseDtoList.get(0).productId(), productId);
    assertEquals(cartFacadeResponseDtoList.get(0).stock(), stock);
    assertEquals(cartFacadeResponseDtoList.get(0).name(), productName);
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
    int stock = 5;

    int quantity = 15;
    long cartId = 1;

    //when
    User user = new User(userId, name, amount);
    Product product = new Product(productId, productName, price, stock);
    Cart cart = new Cart(cartId, quantity, user, product);

    when(userService.getUser(userId)).thenReturn(user);
    ProductServiceRequestDto.productStockResponse productStockResponse = new productStockResponse();
    productStockResponse.setStock(stock);
    productStockResponse.setProductId(productId);
    when(productService.getStockList(List.of(productId))).thenReturn(List.of(productStockResponse));
    when(cartService.createCart(cart)).thenReturn(cart);

    CartFacadeRequestDto.Create create = new CartFacadeRequestDto.Create();
    create.setQuantity(quantity);
    create.setProductId(productId);
    create.setUserId(userId);
    CartFacadeResponseDto cartFacadeResponseDto = cartFacade.addCart(create);

    //then
    assertEquals(cartFacadeResponseDto.id(), cartId);
    assertEquals(cartFacadeResponseDto.price(), price);
    assertEquals(cartFacadeResponseDto.productId(), productId);
    assertEquals(cartFacadeResponseDto.stock(), stock);
    assertEquals(cartFacadeResponseDto.name(), productName);
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
    Product product = new Product(productId, productName, price, stock);
    Cart cart = new Cart(cartId, quantity, user, product);
    when(userService.getUser(userId)).thenReturn(user);
    when(cartService.deleteCart(cartId)).thenReturn(cart);
    CartFacadeResponseDto cartFacadeResponseDto = cartFacade.deleteCart(cartId, userId);

    //then
    assertEquals(cartFacadeResponseDto.id(), cartId);
    assertEquals(cartFacadeResponseDto.name(), productName);
    assertEquals(cartFacadeResponseDto.price(), price);
    assertEquals(cartFacadeResponseDto.stock(), stock);
    assertEquals(cartFacadeResponseDto.productId(), productId);
  }
}
