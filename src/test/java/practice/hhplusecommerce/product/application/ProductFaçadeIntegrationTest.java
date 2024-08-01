package practice.hhplusecommerce.product.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.common.exception.NotFoundException;
import practice.hhplusecommerce.order.business.entity.Order;
import practice.hhplusecommerce.order.business.entity.OrderProduct;
import practice.hhplusecommerce.order.business.repository.OrderProductRepository;
import practice.hhplusecommerce.order.business.repository.OrderRepository;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto;
import practice.hhplusecommerce.product.application.dto.response.ProductFacadeResponseDto.Response;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.repository.ProductRepository;
import practice.hhplusecommerce.user.business.repository.UserRepository;
import practice.hhplusecommerce.user.business.entity.User;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductFaçadeIntegrationTest {

  @Autowired
  ProductFacade productFacade;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  OrderProductRepository orderProductRepository;

  @Autowired
  CacheManager cacheManager;

  @BeforeEach
  public void beforeEach() {
    cacheManager.getCache("getProductList").clear();
    cacheManager.getCache("getTop5ProductsLast3Days").clear();
  }

  @Test
  public void 상품목록조회기능_조회되는지_통합테스트() {
    //given
    List<Product> productList = List.of(
        new Product(null, "꽃병1", 1500, 5),
        new Product(null, "꽃병2", 1500, 5),
        new Product(null, "꽃병3", 1500, 5),
        new Product(null, "꽃병4", 1500, 5),
        new Product(null, "꽃병5", 1500, 5)
    );

    List<Product> givenList = new ArrayList<>();
    for (Product product : productList) {
      givenList.add(productRepository.save(product));
    }

    //when
    List<ProductFacadeResponseDto.Response> whenList = productFacade.getProductList();

    //then
    for (int i = 0; i < 5; i++) {
      assertEquals(givenList.get(i).getId(), whenList.get(i).getId());
      assertEquals(givenList.get(i).getName(), whenList.get(i).getName());
      assertEquals(givenList.get(i).getPrice(), whenList.get(i).getPrice());
      assertEquals(givenList.get(i).getStock(), whenList.get(i).getStock());
    }
  }

  @Test
  public void 상품상세조회기능_성공_통합테스트() {
    //given
    Product product = new Product(null, "꽃병1", 1500, 5);
    Product given = productRepository.save(product);

    //when
    Response when = productFacade.getProduct(product.getId());

    assertEquals(given.getId(), when.getId());
    assertEquals(given.getName(), when.getName());
    assertEquals(given.getPrice(), when.getPrice());
    assertEquals(given.getStock(), when.getStock());
  }

  @Test
  public void 상품상세조회기능_상품없을시_에러반환_통합테스트() {
    //given
    NotFoundException e = null;
    Response when = null;

    //when
    try {
      when = productFacade.getProduct(-0L);
    } catch (NotFoundException nfe) {
        e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "상품이 존재하지 않습니다.");
  }

  @Test
  public void 상위상품목록조회기능_조회된순서와_sortedEntries객체의_순서가_맞는지_통합테스트() {
    //given
    Map<Long, Integer> given = new HashMap<>();

    //when
    //주문상품들 전부 저장
    List<OrderProduct> saveOrderProductList = getSaveOrderProductList();

    for (OrderProduct orderProduct : saveOrderProductList) {
      if (given.containsKey(orderProduct.getProduct().getId())) {
        given.put(orderProduct.getProduct().getId(), given.get(orderProduct.getProduct().getId()) + orderProduct.getQuantity());
      } else {
        given.put(orderProduct.getProduct().getId(), orderProduct.getQuantity());
      }
    }

    //내림차순으로 정열
    List<Map.Entry<Long, Integer>> sortedEntries = given.entrySet()
        .stream()
        .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed()
            .thenComparing(Map.Entry.<Long, Integer>comparingByKey().reversed()))
        .toList();

    List<ProductFacadeResponseDto.Top5ProductsLast3DaysResponse> top5ProductsLast3Days = productFacade.getTop5ProductsLast3Days();

    //then
    //순위가맞고 상품고유번호가 맞는지 체크
    assertEquals(sortedEntries.get(0).getKey(), top5ProductsLast3Days.get(0).getProductId());
    assertEquals(Long.valueOf(sortedEntries.get(0).getValue()), top5ProductsLast3Days.get(0).getSumQuantity());
    assertEquals(sortedEntries.get(1).getKey(), top5ProductsLast3Days.get(1).getProductId());
    assertEquals(Long.valueOf(sortedEntries.get(1).getValue()), top5ProductsLast3Days.get(1).getSumQuantity());
    assertEquals(sortedEntries.get(2).getKey(), top5ProductsLast3Days.get(2).getProductId());
    assertEquals(Long.valueOf(sortedEntries.get(2).getValue()), top5ProductsLast3Days.get(2).getSumQuantity());
    assertEquals(sortedEntries.get(3).getKey(), top5ProductsLast3Days.get(3).getProductId());
    assertEquals(Long.valueOf(sortedEntries.get(3).getValue()), top5ProductsLast3Days.get(3).getSumQuantity());
    assertEquals(sortedEntries.get(4).getKey(), top5ProductsLast3Days.get(4).getProductId());
    assertEquals(Long.valueOf(sortedEntries.get(4).getValue()), top5ProductsLast3Days.get(4).getSumQuantity());
  }

  private List<OrderProduct> getSaveOrderProductList() {
    List<Product> productList = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      productList.add(new Product(null, "꽃병" + i, 1500, 20));
    }
    String userName = "백현명";
    int amount = 0;

    List<Product> saveProductList = new ArrayList<>();
    for (Product product : productList) {
      saveProductList.add(productRepository.save(product));
    }

    User user = new User(null, userName, amount);
    User saveUser = userRepository.save(user);

    Order order = new Order(null, 1500, saveUser);
    Order saveOrder = orderRepository.save(order);

    List<OrderProduct> saveOrderProductList = new ArrayList<>();

    //랜덤으로 주문상품 저장
    Random random = new Random();
    for (int i = 0; i < 100; i++) {
      int j = random.nextInt(10);
      saveOrderProductList.add(
          orderProductRepository.save(
              new OrderProduct(
                  null,
                  saveProductList.get(j).getName(),
                  saveProductList.get(j).getPrice(),
                  3,
                  saveOrder,
                  saveProductList.get(j)
              )
          )
      );
    }
    return saveOrderProductList;
  }
}
