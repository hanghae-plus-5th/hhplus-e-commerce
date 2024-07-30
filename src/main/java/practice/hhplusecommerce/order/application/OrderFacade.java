package practice.hhplusecommerce.order.application;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import practice.hhplusecommerce.common.exception.BadRequestException;
import practice.hhplusecommerce.common.handler.TransactionalHandler;
import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto.Create;
import practice.hhplusecommerce.order.application.dto.request.OrderFacadeRequestDto.OrderProductCreate;
import practice.hhplusecommerce.order.application.dto.response.OrderFacadeResponseDto.OrderResponse;
import practice.hhplusecommerce.order.application.dto.response.OrderFacadeResponseDtoMapper;
import practice.hhplusecommerce.order.business.entity.Order;
import practice.hhplusecommerce.order.business.service.OrderService;
import practice.hhplusecommerce.order.infrastructure.dataPlatform.DataPlatform;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.product.business.service.ProductService;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.user.business.service.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderFacade {

  private final OrderService orderService;
  private final UserService userService;
  private final ProductService productService;
  private final DataPlatform dataPlatform;
  private final TransactionalHandler transactionalHandler;

  /**
   * 주문과 결제가 같이 진행됩니다.
   */
  public OrderResponse order(Long userId, Create create) {
    User user = userService.getUser(userId);
    List<Product> productList = productService.getProductListByProductIdList(create.getProductList().stream().map(OrderProductCreate::getId).toList());

    int totalProductPrice = 0;
    Map<Long, Integer> productDecreaseStockMap = new HashMap<>();

    for (Product product : productList) {
      for (OrderProductCreate orderProductCreate : create.getProductList()) {
        if (product.getId().equals(orderProductCreate.getId())) {
          productDecreaseStockMap.put(product.getId(), orderProductCreate.getQuantity());
          totalProductPrice += product.getPrice() * orderProductCreate.getQuantity();
          break;
        }
      }
    }

    int finalTotalProductPrice = totalProductPrice;
    Order order = transactionalHandler.runWithTransaction(() -> createOrderAfterDecreaseProductStockAndUserAmount(
            create,
            finalTotalProductPrice,
            userId,
            productDecreaseStockMap,
            user,
            productList
        )
    );

    String status = dataPlatform.send(order.getId(), order.getUser().getId(), order.getOrderTotalPrice());
    if (!status.equals("OK 200")) {
      log.error("OrderFacade.order status : {}", status);
      throw new BadRequestException("주문정보를 데이처플랫폼에 전송 실패했습니다.");
    }

    OrderResponse orderResponse = OrderFacadeResponseDtoMapper.toOrderResponse(order);
    orderResponse.setOrderProductList(order.getOrderProductList().stream().map(OrderFacadeResponseDtoMapper::toOrderProductResponse).toList());
    return orderResponse;
  }

  /**
   * 최소 단위로 트랜잭션을 걸고 싶어서 분리
   */
  private Order createOrderAfterDecreaseProductStockAndUserAmount(Create create, int totalProductPrice, Long userId, Map<Long, Integer> productDecreaseStockMap, User user, List<Product> productList) {
    productService.decreaseProductsStock(create.getProductList().stream().map(OrderProductCreate::getId).toList(), productDecreaseStockMap);
    userService.decreaseAmount(userId, totalProductPrice);
    return orderService.createOrder(totalProductPrice, user, productList, create.getProductList());
  }
}
