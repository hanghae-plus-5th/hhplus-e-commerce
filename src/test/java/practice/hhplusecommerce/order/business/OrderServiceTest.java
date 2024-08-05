package practice.hhplusecommerce.order.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
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
import practice.hhplusecommerce.support.CustomTuple;
import practice.hhplusecommerce.user.business.entity.User;

@MockBean(JpaMetamodelMappingContext.class)
public class OrderServiceTest {

  @InjectMocks
  OrderService orderService;

  @Mock
  OrderRepository orderRepository;

  @Mock
  OrderProductRepository orderProductRepository;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void 주문생성_성공_테스트() {
    //given
    long userId = 1L;
    String userName = "백현명";
    Integer amount = 5000;

    long productId = 1L;
    int quantity = 1;
    String productName = "꽃병";
    int stock = 5;

    long orderProductId = 1L;

    long orderId = 1L;
    int productPrice = 1500;

    User user = new User(userId, userName, amount);

    List<Product> productList = List.of(new Product(1L, productName, productPrice, stock));

    OrderFacadeRequestDto.Create create = new Create();


    OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderProductCreate();
    orderProductCreate.setId(productId);
    orderProductCreate.setQuantity(quantity);
    create.setProductList(List.of(orderProductCreate));

    Order saveOrder = new Order(1L, productPrice, user);
    saveOrder.addOrderProduct(new OrderProduct(orderProductId, productName, productPrice, quantity, saveOrder, productList.get(0)));

    //when
    when(orderRepository.save(any(Order.class))).thenReturn(saveOrder);
    Order order = orderService.createOrder(productPrice, user, productList, create.getProductList());

    //then

    //then
    assertEquals(order.getId(), orderId);
    assertEquals(order.getOrderTotalPrice(), productPrice);
    assertEquals(order.getOrderProductList().get(0).getId(), saveOrder.getOrderProductList().get(0).getId());
    assertEquals(order.getOrderProductList().get(0).getName(), saveOrder.getOrderProductList().get(0).getName());
    assertEquals(order.getOrderProductList().get(0).getQuantity(), saveOrder.getOrderProductList().get(0).getQuantity());
    assertEquals(order.getOrderProductList().get(0).getPrice(), saveOrder.getOrderProductList().get(0).getPrice());
  }

  @Test
  public void 상위상품조회_성공_테스트() {
    //given
    List<Tuple> givenList = List.of(
        new CustomTuple(1L, "상품1", 1000, 50, 10L),
        new CustomTuple(2L, "상품2", 2000, 30, 5L),
        new CustomTuple(2L, "상품2", 2000, 30, 5L),
        new CustomTuple(2L, "상품2", 2000, 30, 5L),
        new CustomTuple(2L, "상품2", 2000, 30, 5L)
    );

    //when
    when(orderProductRepository.getTop5ProductsLast3Days(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(givenList);
    List<Top5ProductsLast3DaysResponse> top5ProductsLast3Days = orderService.getTop5ProductsLast3Days();

    //then
    for (int i = 0; i < 1; i++) {
      assertEquals(givenList.get(i).get("productId"), top5ProductsLast3Days.get(i).productId());
      assertEquals(givenList.get(i).get("productName"), top5ProductsLast3Days.get(i).productName());
      assertEquals(givenList.get(i).get("productStock"), top5ProductsLast3Days.get(i).productStock());
      assertEquals(givenList.get(i).get("productPrice"), top5ProductsLast3Days.get(i).productPrice());
    }
    verify(orderProductRepository).getTop5ProductsLast3Days(any(LocalDateTime.class), any(LocalDateTime.class));
  }
}
