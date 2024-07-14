package practice.hhplusecommerce.application.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.order.OrderFacade;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto.Create;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto.OrderProductCreate;
import practice.hhplusecommerce.app.application.order.dto.response.OrderFacadeResponseDto.OrderResponse;
import practice.hhplusecommerce.app.domain.order.OrderProduct;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.service.order.OrderProductRepository;
import practice.hhplusecommerce.app.service.product.ProductRepository;
import practice.hhplusecommerce.app.service.user.UserRepository;
import practice.hhplusecommerce.global.exception.BadRequestException;
import practice.hhplusecommerce.global.exception.NotFoundException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class OrderServiceIntegrationTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderFacade orderFacade;

  @Autowired
  private OrderProductRepository orderProductRepository;

  @Test
  public void 주문기능_주문되는지_통합테스트() {
    //given
    String userName = "백현명";
    int amount = 5500;
    int price1 = 500;
    int price2 = 400;
    int totalProductPrice = price1 * 3 + price2 * 3;

    //when
    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    List<Product> productList = List.of(
        new Product(null, "꽃병1", price1, 5),
        new Product(null, "꽃병2", price2, 5)
    );

    List<Product> saveProductList = new ArrayList<>();
    for (Product product : productList) {
      saveProductList.add(productRepository.save(product));
    }

    OrderFacadeRequestDto.Create create = new Create();
    create.setUserId(saveUser.getId());
    create.setProductList(new ArrayList<>());

    for (Product product : saveProductList) {
      OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderProductCreate();
      orderProductCreate.setId(product.getId());
      orderProductCreate.setQuantity(3);
      create.getProductList().add(orderProductCreate);
    }

    OrderResponse order = orderFacade.order(create);

    List<OrderProduct> orderProductList = orderProductRepository.findAllByOrderId(order.getId());

    //then
    assertNotNull(order);
    assertEquals(order.getOrderTotalPrice(), totalProductPrice);

    for (int i = 0; i < orderProductList.size(); i++) {
      assertEquals(orderProductList.get(i).getName(), order.getOrderProductList().get(i).getName());
      assertEquals(orderProductList.get(i).getPrice(), order.getOrderProductList().get(i).getPrice());
      assertEquals(orderProductList.get(i).getQuantity(), order.getOrderProductList().get(i).getQuantity());
    }

    assertEquals(orderProductList.size(), order.getOrderProductList().size());
  }

  @Test
  public void 주문기능_상품이존재하지않을경우_에러반환하는지_통합테스트() {
    //given
    String userName = "백현명";
    int amount = 5500;

    OrderResponse orderResponse = null;
    NotFoundException e = null;

    //when
    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    OrderFacadeRequestDto.Create create = new Create();
    create.setUserId(saveUser.getId());
    create.setProductList(new ArrayList<>());

    OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderProductCreate();
    orderProductCreate.setId(0L);
    orderProductCreate.setQuantity(3);
    create.getProductList().add(orderProductCreate);

    try {
      orderResponse = orderFacade.order(create);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(orderResponse);
    assertNotNull(e);
    assertEquals(e.getMessage(), "상품이 존재하지 않습니다.");
  }

  @Test
  public void 주문기능_유저가존재하지않을경우_에러반환하는지_통합테스트() {
    //given
    OrderResponse orderResponse = null;
    NotFoundException e = null;

    //when
    OrderFacadeRequestDto.Create create = new Create();
    create.setUserId(0L);
    create.setProductList(new ArrayList<>());

    try {
      orderResponse = orderFacade.order(create);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(orderResponse);
    assertNotNull(e);
    assertEquals(e.getMessage(), "유저가 존재하지 않습니다.");
  }


  @Test
  public void 주문기능_상품재고가부족할경우_에러반환하는지_통합테스트() {
    //given
    String userName = "백현명";
    int amount = 5500;
    int price1 = 1500;
    int price2 = 2500;

    OrderResponse orderResponse = null;
    BadRequestException e = null;

    //when
    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    List<Product> productList = List.of(
        new Product(null, "꽃병1", price1, 5),
        new Product(null, "꽃병2", price2, 5)
    );

    List<Product> saveProductList = new ArrayList<>();
    for (Product product : productList) {
      saveProductList.add(productRepository.save(product));
    }

    OrderFacadeRequestDto.Create create = new Create();
    create.setUserId(saveUser.getId());
    create.setProductList(new ArrayList<>());

    for (Product product : saveProductList) {
      OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderProductCreate();
      orderProductCreate.setId(product.getId());
      orderProductCreate.setQuantity(6);
      create.getProductList().add(orderProductCreate);
    }

    try {
      orderResponse = orderFacade.order(create);
    } catch (BadRequestException bre) {
      e = bre;
    }

    //then
    assertNull(orderResponse);
    assertNotNull(e);
    assertEquals(e.getMessage(), "재고가 부족합니다.");
  }

  @Test
  public void 주문기능_유저의잔액이부족할경우_에러반환하는지_통합테스트() {
    //given
    String userName = "백현명";
    int amount = 3500;
    int price1 = 1500;
    int price2 = 2500;

    OrderResponse orderResponse = null;
    BadRequestException e = null;

    //when
    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    List<Product> productList = List.of(
        new Product(null, "꽃병1", price1, 5),
        new Product(null, "꽃병2", price2, 5)
    );

    List<Product> saveProductList = new ArrayList<>();
    for (Product product : productList) {
      saveProductList.add(productRepository.save(product));
    }

    OrderFacadeRequestDto.Create create = new Create();
    create.setUserId(saveUser.getId());
    create.setProductList(new ArrayList<>());

    for (Product product : saveProductList) {
      OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderProductCreate();
      orderProductCreate.setId(product.getId());
      orderProductCreate.setQuantity(4);
      create.getProductList().add(orderProductCreate);
    }

    try {
      orderResponse = orderFacade.order(create);
    } catch (BadRequestException bre) {
      e = bre;
    }

    //then
    assertNull(orderResponse);
    assertNotNull(e);
    assertEquals(e.getMessage(), "잔액이 부족합니다.");
  }

  @Test
  public void 주문기능_주문완료후재고가차감되었는지_통합테스트() {
    //given
    String userName = "백현명";
    int amount = 5500;
    int price1 = 300;
    int price2 = 500;
    int stock1 = 5;
    int stock2 = 5;

    //when
    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    List<Product> productList = List.of(
        new Product(null, "꽃병1", price1, stock1),
        new Product(null, "꽃병2", price2, stock2)
    );

    List<Product> saveProductList = new ArrayList<>();
    for (Product product : productList) {
      saveProductList.add(productRepository.save(product));
    }

    OrderFacadeRequestDto.Create create = new Create();
    create.setUserId(saveUser.getId());
    create.setProductList(new ArrayList<>());

    for (Product product : saveProductList) {
      OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderProductCreate();
      orderProductCreate.setId(product.getId());
      orderProductCreate.setQuantity(3);
      create.getProductList().add(orderProductCreate);
    }

    orderFacade.order(create);

    //then
    for (Product product : saveProductList) {
      assertEquals(product.getStock(), 2);
    }
  }

  @Test
  public void 주문기능_유저잔액차감되었는지_통합테스트() {
    //given
    String userName = "백현명";
    int amount = 5500;
    int price1 = 500;
    int price2 = 300;
    int quantity = 3;

    //when
    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    List<Product> productList = List.of(
        new Product(null, "꽃병1", price1, 5),
        new Product(null, "꽃병2", price2, 5)
    );

    List<Product> saveProductList = new ArrayList<>();
    for (Product product : productList) {
      saveProductList.add(productRepository.save(product));
    }

    OrderFacadeRequestDto.Create create = new Create();
    create.setUserId(saveUser.getId());
    create.setProductList(new ArrayList<>());

    for (Product product : saveProductList) {
      OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderProductCreate();
      orderProductCreate.setId(product.getId());
      orderProductCreate.setQuantity(3);
      create.getProductList().add(orderProductCreate);
    }

    orderFacade.order(create);

    //then
    assertEquals(saveUser.getAmount(), amount - ((price1 * quantity) + (price2 * quantity)));
  }
}
