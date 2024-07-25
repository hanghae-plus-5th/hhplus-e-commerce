package practice.hhplusecommerce.product.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.common.exception.NotFoundException;
import practice.hhplusecommerce.common.handler.TransactionalHandler;
import practice.hhplusecommerce.order.business.repository.OrderProductRepository;
import practice.hhplusecommerce.order.business.repository.OrderRepository;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.repository.ProductRepository;
import practice.hhplusecommerce.product.business.service.ProductService;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.user.business.repository.UserRepository;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceIntegrationTest {

  @Autowired
  ProductService productService;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  OrderProductRepository orderProductRepository;

  @Autowired
  OrderRepository orderRepository;

  @Autowired
  TransactionalHandler transactionalHandler;


  @Test
  @Transactional
  public void 상품목록조회_성공_통합테스트() {
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
    List<Product> whenList = productService.getProductList();

    for (int i = 0; i < 5; i++) {
      assertEquals(givenList.get(i).getId(), whenList.get(i).getId());
      assertEquals(givenList.get(i).getName(), whenList.get(i).getName());
      assertEquals(givenList.get(i).getPrice(), whenList.get(i).getPrice());
      assertEquals(givenList.get(i).getStock(), whenList.get(i).getStock());
    }
  }

  @Test
  @Transactional
  public void 상품상세조회_상품존재하지않아서_에러반환_실패_통합테스트() {
    //given
    long productId = -0L;

    //when
    NotFoundException e = null;
    Product when = null;

    try {
      when = productService.getProduct(productId);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(when);
    assertNotNull(e);
    assertEquals(e.getMessage(), "상품이 존재하지 않습니다.");
  }

  @Test
  @Transactional
  public void 상위상세조회_성공_통합테스트() {
    //given
    Product product = new Product(null, "꽃병1", 1500, 5);
    Product given = productRepository.save(product);

    //when
    Product when = productService.getProduct(given.getId());

    //then
    assertEquals(when.getId(), given.getId());
    assertEquals(when.getPrice(), given.getPrice());
    assertEquals(when.getName(), given.getName());
    assertEquals(when.getStock(), given.getStock());
  }

  @Test
  @Transactional
  public void 상품고유번호목록이_주어졌을때_상품목록조회_성공_통합테스트() {

    //given
    List<Product> givenList = new ArrayList<>();

    List<Product> productList = List.of(
        new Product(null, "꽃병1", 1500, 5),
        new Product(null, "꽃병2", 1500, 5),
        new Product(null, "꽃병3", 1500, 5),
        new Product(null, "꽃병4", 1500, 5),
        new Product(null, "꽃병5", 1500, 5)
    );

    for (Product product : productList) {
      givenList.add(productRepository.save(product));
    }

    //when
    List<Product> whenList = productService.getProductListByProductIdList(givenList.stream().map(Product::getId).toList());

    for (int i = 0; i < 5; i++) {
      assertEquals(givenList.get(i).getId(), whenList.get(i).getId());
      assertEquals(givenList.get(i).getName(), whenList.get(i).getName());
      assertEquals(givenList.get(i).getPrice(), whenList.get(i).getPrice());
      assertEquals(givenList.get(i).getStock(), whenList.get(i).getStock());
    }
  }

  @Test
  @Transactional
  public void 상품고유번호목록이_주어졌을때_상품목록조회_상품없을시_실패_통합테스트() {

    //given
    List<Long> productIdList = List.of(-0L);

    //when
    NotFoundException e = null;
    List<Product> whenList = null;

    try {
      whenList = productService.getProductListByProductIdList(productIdList);
    } catch (NotFoundException nfe) {
      e = nfe;
    }

    //then
    assertNull(whenList);
    assertNotNull(e);
    assertEquals(e.getMessage(), "상품이 존재하지 않습니다.");
  }

  @Test
  public void 상품재고차감기능_동시성문제_비관락_통합테스트() {
    //given
    int price = 500;
    int quantity = 2;
    int stock1 = 100000;
    int max = 100;

    List<Product> productList = List.of(
        new Product(null, "꽃병1", price, stock1),
        new Product(null, "꽃병2", price, stock1)
    );

    List<Product> saveProductList = new ArrayList<>();
    for (Product product : productList) {
      saveProductList.add(productRepository.save(product));
    }

    Map<Long, Integer> productDecreaseStockMap = new HashMap<>();
    for (Product product : productList) {
      productDecreaseStockMap.put(product.getId(), quantity);
    }

    //when
    CompletableFuture<?>[] futures = IntStream.range(0, max)
        .mapToObj(i -> CompletableFuture.runAsync(() -> transactionalHandler.runWithTransaction(() -> productService.decreaseProductsStock(productList.stream().map(Product::getId).toList(), productDecreaseStockMap))))
        .toArray(CompletableFuture[]::new);

    CompletableFuture.allOf(futures).join();

    //then
    productRepository.deleteAllInBatch(saveProductList);

    List<Product> whenProductList = productRepository.findAllByIdIn(saveProductList.stream().map(Product::getId).toList());
    for (int i = 0; i < whenProductList.size(); i++) {
      assertEquals(stock1 - quantity * max, whenProductList.get(i).getStock());
    }
  }
}
