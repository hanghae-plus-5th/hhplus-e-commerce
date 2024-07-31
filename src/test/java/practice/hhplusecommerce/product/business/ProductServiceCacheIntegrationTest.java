package practice.hhplusecommerce.product.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
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
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.repository.ProductRepository;
import practice.hhplusecommerce.product.business.service.ProductService;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductServiceCacheIntegrationTest {


  @Autowired
  ProductService productService;

  @SpyBean
  ProductRepository productRepository;

  @Autowired
  DatabaseCleanUp databaseCleanUp;

  @Autowired
  CacheManager cacheManager;


  @BeforeAll
  public void setUpBeforeClass() {
    List<Product> productList = List.of(
        new Product(null, "꽃병1", 1500, 5),
        new Product(null, "꽃병2", 1500, 5),
        new Product(null, "꽃병3", 1500, 5),
        new Product(null, "꽃병4", 1500, 5),
        new Product(null, "꽃병5", 1500, 5)
    );
    productRepository.saveAll(productList);
  }

  @AfterAll
  public void tearDownAfterClass() {
    databaseCleanUp.execute();
  }

  @Test
  @DisplayName("상품 목록조회 2번째 조회부터 캐싱되는지 통합테스트")
  public void getProductList_cache_success() {
    //given
    cacheManager.getCache("getProductList").clear();

    //when
    List<Product> productList1 = productService.getProductList();
    List<Product> productList2 = productService.getProductList();
    productService.getProductList();

    //then
    verify(productRepository, times(1)).findAll();

    for (int i = 0; i < 5; i++) {
      assertEquals(productList1.get(i).getId(), productList2.get(i).getId());
      assertEquals(productList1.get(i).getName(), productList2.get(i).getName());
      assertEquals(productList1.get(i).getPrice(), productList2.get(i).getPrice());
      assertEquals(productList1.get(i).getStock(), productList2.get(i).getStock());
    }
  }
}
