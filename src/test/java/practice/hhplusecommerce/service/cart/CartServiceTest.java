package practice.hhplusecommerce.service.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.app.domain.cart.Cart;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.service.cart.CartRepository;
import practice.hhplusecommerce.app.service.cart.CartService;
import practice.hhplusecommerce.app.service.cart.dto.response.CartServiceResponseDto;
import practice.hhplusecommerce.app.service.cart.dto.response.CartServiceResponseDto.Response;
import practice.hhplusecommerce.global.exception.NotFoundException;

@MockBean(JpaMetamodelMappingContext.class)
public class CartServiceTest {

  @InjectMocks
  CartService cartService;

  @Mock
  CartRepository cartRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void 장바구니목록조회기능_장바구니목록조회되는지_테스트() {
    //given
    long userId = 1;
    String name = "백현명";
    int amount = 1500;
    long productId = 1L;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;
    long cartId = 1L;
    int quantity = 4;

    //when
    User user = new User(userId, name, amount);
    Product product = new Product(productId, productName, productPrice, productStock);
    Cart cart = new Cart(cartId, quantity, user, product);
    when(cartRepository.findAllByUserId(userId)).thenReturn(List.of(cart));
    List<Response> cartList = cartService.getCartList(userId);

    //then
    assertEquals(cartList.getFirst().getId(), cartId);
    assertEquals(cartList.getFirst().getName(), productName);
    assertEquals(cartList.getFirst().getStock(), productStock);
    assertEquals(cartList.getFirst().getQuantity(), quantity);
  }

  @Test
  public void 장바구니조회기능_장바구니조회되는지_테스트() {
    //given
    long userId = 1;
    String name = "백현명";
    int amount = 1500;
    long productId = 1L;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;
    long cartId = 1L;
    int quantity = 4;

    //when
    User user = new User(userId, name, amount);
    Product product = new Product(productId, productName, productPrice, productStock);
    Cart cart = new Cart(cartId, quantity, user, product);
    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    CartServiceResponseDto.Response when = cartService.getCart(cartId);

    //then
    assertEquals(when.getId(), cartId);
    assertEquals(when.getName(), productName);
    assertEquals(when.getStock(), productStock);
    assertEquals(when.getQuantity(), quantity);
  }

  @Test
  public void 장바구니조회기능_장바구니없을시_에러반환하는지_테스트() {
    //given
    long cartId = 1L;

    //when
    when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

    NotFoundException e = null;
    CartServiceResponseDto.Response when = null;

    try {
      when = cartService.getCart(cartId);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "장바구니가 존재하지 않습니다.");
  }

  @Test
  public void 장바구니담기기능_장바구니담기는지_테스트() {
    //given
    long userId = 1;
    String name = "백현명";
    int amount = 1500;
    long productId = 1L;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;
    long cartId = 1L;
    int quantity = 4;

    //when
    User user = new User(userId, name, amount);
    Product product = new Product(productId, productName, productPrice, productStock);
    Cart cart = new Cart(null, quantity, user, product);
    Cart createdCart = new Cart(cartId, quantity, user, product);
    when(cartRepository.save(cart)).thenReturn(createdCart);
    Response when = cartService.createCart(cart);

    //then
    assertEquals(when.getId(), cartId);
    assertEquals(when.getName(), productName);
    assertEquals(when.getStock(), productStock);
    assertEquals(when.getQuantity(), quantity);
  }

  @Test
  public void 장바구니삭제기능_장바구니삭제되는지_테스트() {
    //given
    long userId = 1;
    String name = "백현명";
    int amount = 1500;
    long productId = 1L;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;
    long cartId = 1L;
    int quantity = 4;

    //when
    User user = new User(userId, name, amount);
    Product product = new Product(productId, productName, productPrice, productStock);
    Cart cart = new Cart(cartId, quantity, user, product);
    when(cartRepository.findById(cartId)).thenReturn(Optional.of(cart));
    Response when = cartService.deleteCart(cartId);

    //then
    assertEquals(when.getId(), cartId);
    assertEquals(when.getName(), productName);
    assertEquals(when.getStock(), productStock);
    assertEquals(when.getQuantity(), quantity);
  }

  @Test
  public void 장바구니삭제기능_장바구니없을시_테스트() {
    //given
    long cartId = 1L;

    //when
    when(cartRepository.findById(cartId)).thenReturn(Optional.empty());

    NotFoundException e = null;
    CartServiceResponseDto.Response when = null;

    try {
      when = cartService.deleteCart(cartId);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "장바구니가 존재하지 않습니다.");
  }
}
