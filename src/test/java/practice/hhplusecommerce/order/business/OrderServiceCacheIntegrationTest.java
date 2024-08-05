package practice.hhplusecommerce.order.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import practice.hhplusecommerce.common.test.DatabaseCleanUp;
import practice.hhplusecommerce.order.business.command.OrderCommand.Top5ProductsLast3DaysResponse;
import practice.hhplusecommerce.order.business.entity.Order;
import practice.hhplusecommerce.order.business.entity.OrderProduct;
import practice.hhplusecommerce.order.business.repository.OrderProductRepository;
import practice.hhplusecommerce.order.business.repository.OrderRepository;
import practice.hhplusecommerce.order.business.service.OrderService;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.repository.ProductRepository;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.user.business.repository.UserRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceCacheIntegrationTest {

  @SpyBean
  OrderService orderService;

  @Autowired
  OrderRepository orderRepository;

  @SpyBean
  OrderProductRepository orderProductRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  DatabaseCleanUp databaseCleanUp;

  @Autowired
  CacheManager cacheManager;

  @BeforeAll
  public void setUpBefore() {
    List<Product> productList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      productList.add(new Product(null, "꽃병" + i, 1500, 20));
    }

    List<Product> saveProductList = new ArrayList<>();
    for (Product product : productList) {
      saveProductList.add(productRepository.save(product));
    }

    User user = new User(null, "백현명", 0);
    User saveUser = userRepository.save(user);

    Order order = new Order(null, 1500, saveUser);
    Order saveOrder = orderRepository.save(order);

    //랜덤으로 주문상품 저장
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      int j = random.nextInt(10);
      orderProductRepository.save(
          new OrderProduct(
              null,
              saveProductList.get(j).getName(),
              saveProductList.get(j).getPrice(),
              3,
              saveOrder,
              saveProductList.get(j)
          )
      );
    }
  }

  @AfterAll
  public void tearDownAfter() {
    databaseCleanUp.execute();
  }

  @Test
  @DisplayName("인기상품조회 두번째 조회부터 캐싱 되는지 체크")
  public void getTop5ProductsLast3Days_cache_success() {
    //given
    cacheManager.getCache("getTop5ProductsLast3Days").clear();

    //when
    List<Top5ProductsLast3DaysResponse> top5ProductsLast3Days = orderService.getTop5ProductsLast3Days();
    List<Top5ProductsLast3DaysResponse> cacheTop5ProductsLast3Days = orderService.getTop5ProductsLast3Days();
    orderService.getTop5ProductsLast3Days();

    //then
    verify(orderProductRepository, times(1)).getTop5ProductsLast3Days(any(LocalDateTime.class), any(LocalDateTime.class));
    assertEquals(top5ProductsLast3Days.get(0).productId(), cacheTop5ProductsLast3Days.get(0).productId());
    assertEquals(top5ProductsLast3Days.get(0).productName(), cacheTop5ProductsLast3Days.get(0).productName());
    assertEquals(top5ProductsLast3Days.get(1).productId(), cacheTop5ProductsLast3Days.get(1).productId());
    assertEquals(top5ProductsLast3Days.get(1).productName(), cacheTop5ProductsLast3Days.get(1).productName());
    assertEquals(top5ProductsLast3Days.get(2).productId(), cacheTop5ProductsLast3Days.get(2).productId());
    assertEquals(top5ProductsLast3Days.get(2).productName(), cacheTop5ProductsLast3Days.get(2).productName());
    assertEquals(top5ProductsLast3Days.get(3).productId(), cacheTop5ProductsLast3Days.get(3).productId());
    assertEquals(top5ProductsLast3Days.get(3).productName(), cacheTop5ProductsLast3Days.get(3).productName());
    assertEquals(top5ProductsLast3Days.get(4).productId(), cacheTop5ProductsLast3Days.get(4).productId());
    assertEquals(top5ProductsLast3Days.get(4).productName(), cacheTop5ProductsLast3Days.get(4).productName());
  }
}
