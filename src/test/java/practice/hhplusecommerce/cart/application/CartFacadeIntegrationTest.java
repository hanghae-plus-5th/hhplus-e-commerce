package practice.hhplusecommerce.cart.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.cart.application.dto.requst.CartFacadeRequestDto;
import practice.hhplusecommerce.cart.application.dto.requst.CartFacadeRequestDto.Create;
import practice.hhplusecommerce.cart.application.dto.response.CartFacadeResponseDto;
import practice.hhplusecommerce.cart.business.entity.Cart;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.cart.business.repository.CartRepository;
import practice.hhplusecommerce.product.business.repository.ProductRepository;
import practice.hhplusecommerce.user.business.service.UserRepository;
import practice.hhplusecommerce.common.exception.BadRequestException;
import practice.hhplusecommerce.common.exception.NotFoundException;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartFacadeIntegrationTest {

  @Autowired
  private CartFacade cartFacade;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private CartRepository cartRepository;

  @Test
  public void 장바구니목록조회기능_장바구니정보조회되는지_통합테스트() {
    //given
    String userName = "백현명";
    int amount = 0;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;
    int quantity = 4;

    //when
    Product product = new Product(null, productName, productPrice, productStock);
    Product saveProduct = productRepository.save(product);

    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    Cart cart = new Cart(null, quantity, saveUser, saveProduct);
    Cart saveCart = cartRepository.save(cart);

    List<CartFacadeResponseDto> cartList = cartFacade.getCartList(saveUser.getId());

    //then
    assertEquals(cartList.getFirst().id(), saveCart.getId());
    assertEquals(cartList.getFirst().name(), saveProduct.getName());
    assertEquals(cartList.getFirst().price(), saveProduct.getPrice());
    assertEquals(cartList.getFirst().stock(), saveProduct.getStock());
    assertEquals(cartList.getFirst().quantity(), saveCart.getQuantity());
  }

  @Test
  public void 장바구니목록조회기능_유저없으면_에러반환하는지_통합테스트() {
    //given
    NotFoundException e = null;
    List<CartFacadeResponseDto> when = null;

    //when
    try {
      when = cartFacade.getCartList(0L);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "유저가 존재하지 않습니다.");
  }

  @Test
  public void 장바구니추가기능_추가잘되는지_통합테스트() {
    //given
    String userName = "백현명";
    int amount = 0;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;
    int quantity = 4;

    //when
    Product product = new Product(null, productName, productPrice, productStock);
    Product saveProduct = productRepository.save(product);

    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    CartFacadeRequestDto.Create create = new Create();
    create.setUserId(saveUser.getId());
    create.setQuantity(quantity);
    create.setProductId(saveProduct.getId());

    CartFacadeResponseDto cartFacadeResponseDto = cartFacade.addCart(create);
    Cart cart = cartRepository.findById(cartFacadeResponseDto.id()).get();

    assertEquals(cartFacadeResponseDto.id(), cart.getId());
    assertEquals(cartFacadeResponseDto.name(), saveProduct.getName());
    assertEquals(cartFacadeResponseDto.price(), saveProduct.getPrice());
    assertEquals(cartFacadeResponseDto.stock(), saveProduct.getStock());
    assertEquals(cartFacadeResponseDto.quantity(), quantity);

    assertEquals(cart.getQuantity(), quantity);
    assertEquals(cart.getProduct(), saveProduct);
    assertEquals(cart.getUser(), saveUser);
  }

  @Test
  public void 장바구니추가기능_유저없으면_에러반환하는지_통합테스트() {
    //given
    NotFoundException e = null;
    CartFacadeResponseDto when = null;

    //when
    try {
      CartFacadeRequestDto.Create create = new Create();
      create.setUserId(0L);
      create.setQuantity(5);
      create.setProductId(0L);

      when = cartFacade.addCart(create);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "유저가 존재하지 않습니다.");
  }

  @Test
  public void 장바구니추가기능_상품없으면_에러반환하는지_통합테스트() {
    //given
    NotFoundException e = null;
    CartFacadeResponseDto when = null;

    //when
    try {
      User user = new User(null, "테스트", 150);
      User saveUser = userRepository.save(user);

      CartFacadeRequestDto.Create create = new Create();
      create.setUserId(saveUser.getId());
      create.setQuantity(5);
      create.setProductId(0L);

      when = cartFacade.addCart(create);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "상품이 존재하지 않습니다.");
  }

  @Test
  public void 장바구니추가기능_재고가없으면_에러반환하는지_통합테스트() {
    //given
    String userName = "백현명";
    int amount = 0;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 0;
    int quantity = 4;

    BadRequestException e = null;
    CartFacadeResponseDto when = null;

    //when
    try {
      Product product = new Product(null, productName, productPrice, productStock);
      Product saveProduct = productRepository.save(product);

      User user = new User(null, userName, amount);
      User saveUser = userRepository.save(user);

      CartFacadeRequestDto.Create create = new Create();
      create.setUserId(saveUser.getId());
      create.setQuantity(quantity);
      create.setProductId(saveProduct.getId());

      when = cartFacade.addCart(create);
    } catch (BadRequestException nfe) {
      e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "재고가 부족합니다.");
  }

  @Test
  public void 장바구니삭제기능_삭제되는지_테스트() {
    //given
    String userName = "백현명";
    int amount = 0;
    String productName = "꽃병";
    int productPrice = 1200;
    int productStock = 5;
    int quantity = 4;

    //when
    Product product = new Product(null, productName, productPrice, productStock);
    Product saveProduct = productRepository.save(product);

    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    Cart cart = new Cart(null, quantity, user, saveProduct);
    Cart saveCart = cartRepository.save(cart);

    CartFacadeResponseDto cartFacadeResponseDto = cartFacade.deleteCart(saveCart.getId(), saveUser.getId());

    assertEquals(cartFacadeResponseDto.id(), saveCart.getId());
    assertEquals(cartFacadeResponseDto.name(), saveCart.getProduct().getName());
    assertEquals(cartFacadeResponseDto.stock(), saveCart.getProduct().getStock());
    assertEquals(cartFacadeResponseDto.quantity(), saveCart.getQuantity());
    assertFalse(cartRepository.findById(saveCart.getId()).isPresent());
  }

  @Test
  public void 장바구니삭제기능_유저없으면_에러반환하는지_통합테스트() {
    //given
    NotFoundException e = null;
    CartFacadeResponseDto when = null;

    //when
    try {

      when = cartFacade.deleteCart(0L, 0L);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "유저가 존재하지 않습니다.");
  }

  @Test
  public void 장바구니삭제기능_장바구니가없으면_에러반환하는지_통합테스트() {
    //given
    NotFoundException e = null;
    CartFacadeResponseDto when = null;

    String userName = "백현명";
    int amount = 0;

    //when
    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    try {
      when = cartFacade.deleteCart(0L, saveUser.getId());
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "장바구니가 존재하지 않습니다.");
  }
}
