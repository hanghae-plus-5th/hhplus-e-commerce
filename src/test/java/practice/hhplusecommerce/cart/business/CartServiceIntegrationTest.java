package practice.hhplusecommerce.cart.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.cart.business.dto.CartServiceResponseDto;
import practice.hhplusecommerce.cart.business.dto.CartServiceResponseDto.Response;
import practice.hhplusecommerce.cart.business.entity.Cart;
import practice.hhplusecommerce.cart.business.repository.CartRepository;
import practice.hhplusecommerce.cart.business.service.CartService;
import practice.hhplusecommerce.common.exception.NotFoundException;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.repository.ProductRepository;
import practice.hhplusecommerce.user.business.UserRepository;
import practice.hhplusecommerce.user.business.entity.User;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartServiceIntegrationTest {

  @Autowired
  CartService cartService;

  @Autowired
  CartRepository cartRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ProductRepository productRepository;


  @Test
  public void 장바구니생성_통과_통합테스트() {
    //given
    String name = "백현명";
    int amount = 1500;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;


    User user = userRepository.save(new User(null, name, amount));
    Product product = productRepository.save(new Product(null, productName, productPrice, productStock));

    //when
    Response when = cartService.createCart(5, user, product);
    Cart saveCart = cartRepository.findById(when.getId()).orElse(null);

    //then
    assertEquals(when.getId(), saveCart.getId());
    assertEquals(when.getName(), saveCart.getProduct().getName());
    assertEquals(when.getStock(), saveCart.getProduct().getStock());
    assertEquals(when.getQuantity(), saveCart.getQuantity());

  }

  @Test
  public void 장바구니삭제_통과_통합테스트() {
    //given
    String name = "백현명";
    int amount = 1500;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;


    User user = userRepository.save(new User(null, name, amount));
    Product product = productRepository.save(new Product(null, productName, productPrice, productStock));
    Cart cart = cartRepository.save( new Cart(null, 5, user, product));

    //when
    cartService.deleteCart(cart.getId());

    //then
    Cart saveCart = cartRepository.findById(cart.getId()).orElse(null);
    assertNull(saveCart);
  }

  @Test
  public void 장바구니목록조회_통과_통합테스트() {
    //given
    String name = "백현명";
    int amount = 1500;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;


    User user = userRepository.save(new User(null, name, amount));
    Product product = productRepository.save(new Product(null, productName, productPrice, productStock));
    Cart cart = cartRepository.save( new Cart(null, 5, user, product));

    //when
    List<Response> cartList = cartService.getCartList(user.getId());

    //then
    assertEquals(cartList.getFirst().getId(), cart.getId());
    assertEquals(cartList.getFirst().getName(), cart.getProduct().getName());
    assertEquals(cartList.getFirst().getStock(), cart.getProduct().getStock());
    assertEquals(cartList.getFirst().getQuantity(), cart.getQuantity());
  }

  @Test
  public void 장바구니상세조회_통과_통합테스트() {
    //given
    String name = "백현명";
    int amount = 1500;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;


    User user = userRepository.save(new User(null, name, amount));
    Product product = productRepository.save(new Product(null, productName, productPrice, productStock));
    Cart saveCart = cartRepository.save( new Cart(null, 5, user, product));

    //when
    Response cartResponse = cartService.getCart(saveCart.getId());

    //then
    assertEquals(cartResponse.getId(), saveCart.getId());
    assertEquals(cartResponse.getName(), saveCart.getProduct().getName());
    assertEquals(cartResponse.getStock(), saveCart.getProduct().getStock());
    assertEquals(cartResponse.getQuantity(), saveCart.getQuantity());
  }

  @Test
  public void 장바구니상세조회_장바구니없을시_에러반환_및_실패_통합테스트() {
    //given
    long cartId = -0L;

    //when
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
}
