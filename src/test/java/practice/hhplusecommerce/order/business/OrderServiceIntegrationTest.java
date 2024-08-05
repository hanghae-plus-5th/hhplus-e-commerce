package practice.hhplusecommerce.order.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.persistence.Tuple;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto;
import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto.Create;
import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto.OrderProductCreate;
import practice.hhplusecommerce.order.business.command.OrderCommand.Top5ProductsLast3DaysResponse;
import practice.hhplusecommerce.order.business.entity.Order;
import practice.hhplusecommerce.order.business.entity.OrderProduct;
import practice.hhplusecommerce.order.business.repository.OrderProductRepository;
import practice.hhplusecommerce.order.business.repository.OrderRepository;
import practice.hhplusecommerce.order.business.service.OrderService;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.repository.ProductRepository;
import practice.hhplusecommerce.user.business.repository.UserRepository;
import practice.hhplusecommerce.user.business.entity.User;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceIntegrationTest {

  @Autowired
  OrderService orderService;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  OrderProductRepository orderProductRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  UserRepository userRepository;


  @Test
  public void 주문생성_성공_통합테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";
    Integer amount = 5000;

    long productId = 1L;
    int quantity = 1;
    String productName = "꽃병";
    int stock = 5;

    int productPrice = 1500;

    User user = userRepository.save(new User(null, userName, amount));
    Product product = productRepository.save(new Product(null, productName, productPrice, stock));

    OrderFacadeRequestDto.Create create = new Create();

    OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderProductCreate();
    orderProductCreate.setId(product.getId());
    orderProductCreate.setQuantity(quantity);
    create.setProductList(List.of(orderProductCreate));

    //when
    Order when = orderService.createOrder(productPrice, user, List.of(product), create.getProductList());
    Order saveOrder = orderRepository.findById(when.getId()).orElse(null);

    //then
    assertNotNull(saveOrder);
    assertEquals(when.getId(), saveOrder.getId());
    assertEquals(when.getOrderTotalPrice(), productPrice);
    assertEquals(when.getOrderProductList().get(0).getId(), saveOrder.getOrderProductList().get(0).getId());
    assertEquals(when.getOrderProductList().get(0).getName(), saveOrder.getOrderProductList().get(0).getName());
    assertEquals(when.getOrderProductList().get(0).getQuantity(), saveOrder.getOrderProductList().get(0).getQuantity());
    assertEquals(when.getOrderProductList().get(0).getPrice(), saveOrder.getOrderProductList().get(0).getPrice());
  }

  @Test
  public void 상위상품조회_성공_통합테스트() {
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

    //when
    List<Top5ProductsLast3DaysResponse> top5ProductsLast3Days = orderService.getTop5ProductsLast3Days();

    //then
    //순위가맞고 상품고유번호가 맞는지 체크
    assertEquals(sortedEntries.get(0).getKey(), top5ProductsLast3Days.get(0).productId());
    assertEquals(Long.valueOf(sortedEntries.get(0).getValue()), top5ProductsLast3Days.get(0).sumQuantity());
    assertEquals(sortedEntries.get(1).getKey(), top5ProductsLast3Days.get(1).productId());
    assertEquals(Long.valueOf(sortedEntries.get(1).getValue()), top5ProductsLast3Days.get(1).sumQuantity());
    assertEquals(sortedEntries.get(2).getKey(), top5ProductsLast3Days.get(2).productId());
    assertEquals(Long.valueOf(sortedEntries.get(2).getValue()), top5ProductsLast3Days.get(2).sumQuantity());
    assertEquals(sortedEntries.get(3).getKey(), top5ProductsLast3Days.get(3).productId());
    assertEquals(Long.valueOf(sortedEntries.get(3).getValue()), top5ProductsLast3Days.get(3).sumQuantity());
    assertEquals(sortedEntries.get(4).getKey(), top5ProductsLast3Days.get(4).productId());
    assertEquals(Long.valueOf(sortedEntries.get(4).getValue()), top5ProductsLast3Days.get(4).sumQuantity());
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
