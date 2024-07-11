package practice.hhplusecommerce.application.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import practice.hhplusecommerce.app.application.order.OrderFacade;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto.Create;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto.OrderProductCreate;
import practice.hhplusecommerce.app.application.order.dto.response.OrderFacadeResponseDto.OrderResponse;
import practice.hhplusecommerce.app.entity.order.Order;
import practice.hhplusecommerce.app.entity.order.OrderProduct;
import practice.hhplusecommerce.app.entity.product.Product;
import practice.hhplusecommerce.app.entity.user.User;
import practice.hhplusecommerce.app.service.order.OrderProductService;
import practice.hhplusecommerce.app.service.order.OrderService;
import practice.hhplusecommerce.app.service.payment.PaymentService;
import practice.hhplusecommerce.app.service.product.ProductService;
import practice.hhplusecommerce.app.service.product.dto.request.ProductServiceRequestDto;
import practice.hhplusecommerce.app.service.product.dto.request.ProductServiceRequestDto.DeductionStock;
import practice.hhplusecommerce.app.service.user.UserService;

@MockBean(JpaMetamodelMappingContext.class)
public class OrderFacadeTest {

  @InjectMocks
  OrderFacade orderFacade;

  @Mock
  UserService userService;

  @Mock
  ProductService productService;

  @Mock
  OrderService orderService;

  @Mock
  OrderProductService orderProductService;

  @Mock
  PaymentService paymentService;


  @Test
  public void 주문하기기능_주문정보반환하는지_테스트() {

    //given
    long userId = 1L;
    String userName = "백현명";
    Integer amount = 2000;

    long productId = 1L;
    int quantity = 5;
    String productName = "꽃병";
    int stock = 5;

    long orderProductId = 1L;

    long orderId = 1L;
    int productPrice = 1500;

    //when
    User user = new User(userId, userName, amount);
    when(userService.getUser(userId)).thenReturn(user);

    List<Product> productList =   List.of(new Product(1L, productName, productPrice, stock));
    when(productService.getProductListByProductIdList(List.of(productId))).thenReturn(productList);

    Order order = new Order(orderId, productPrice, user);
    when(orderService.createOrder(order));

    List<OrderProduct> orderProductList = List.of(new OrderProduct(orderProductId, productName, productPrice, quantity, order, productList.get(0)));
    when(orderProductService.createOrderProduct(productList, order)).thenReturn(orderProductList);

    ProductServiceRequestDto.DeductionStock deductionStock = new DeductionStock();
    deductionStock.setProductId(productId);
    deductionStock.setDeductionStock(quantity);

    OrderFacadeRequestDto.Create create = new Create();
    create.setUserId(userId);

    OrderFacadeRequestDto.OrderProductCreate orderProductCreate = new OrderProductCreate();
    orderProductCreate.setId(productId);
    orderProductCreate.setQuantity(quantity);
    create.setProductList(List.of(orderProductCreate));
    OrderResponse orderResponse = orderFacade.order(create);

    //then
    assertEquals(orderResponse.getId(), orderId);
    assertEquals(orderResponse.getOrderTotalPrice(), productPrice);
    assertEquals(orderResponse.getOrderProductList().get(0).getId(), orderProductList.get(0).getId());
    assertEquals(orderResponse.getOrderProductList().get(0).getName(), orderProductList.get(0).getName());
    assertEquals(orderResponse.getOrderProductList().get(0).getQuantity(), orderProductList.get(0).getQuantity());
    assertEquals(orderResponse.getOrderProductList().get(0).getPrice(), orderProductList.get(0).getPrice());
  }
}
