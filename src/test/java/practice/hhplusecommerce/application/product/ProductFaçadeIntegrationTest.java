package practice.hhplusecommerce.application.product;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.product.ProductFacade;
import practice.hhplusecommerce.app.application.product.dto.ProductFacadeDto;
import practice.hhplusecommerce.app.application.product.dto.response.ProductFacadeResponseDto;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.order.OrderProduct;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.service.order.OrderProductRepository;
import practice.hhplusecommerce.app.service.order.OrderRepository;
import practice.hhplusecommerce.app.service.product.ProductRepository;
import practice.hhplusecommerce.app.service.user.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
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

    productRepository.saveAll(productList);
    List<Product> givenList = productRepository.findAll();

    //when
    List<ProductFacadeDto> whenList = productFacade.getProductList();

    //then
    for (int i = 0; i < 5; i++) {
      assertEquals(givenList.get(i).getId(), whenList.get(i).id());
      assertEquals(givenList.get(i).getName(), whenList.get(i).name());
      assertEquals(givenList.get(i).getPrice(), whenList.get(i).price());
      assertEquals(givenList.get(i).getStock(), whenList.get(i).stock());
    }
  }

  @Test
  public void 상위상품목록조회기능_조회된순서와_sortedEntries객체의_랭킹이맞는지_통합테스트() {
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
