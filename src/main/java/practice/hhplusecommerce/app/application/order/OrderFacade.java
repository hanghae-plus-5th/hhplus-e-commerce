package practice.hhplusecommerce.app.application.order;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto.OrderProductCreate;
import practice.hhplusecommerce.app.application.order.dto.response.OrderFacadeResponseDto;
import practice.hhplusecommerce.app.application.order.dto.response.OrderFacadeResponseDto.OrderResponse;
import practice.hhplusecommerce.app.application.order.dto.response.OrderFacadeResponseDtoMapper;
import practice.hhplusecommerce.app.domain.order.Order;
import practice.hhplusecommerce.app.domain.order.OrderProduct;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.service.dataPlatform.DataPlatform;
import practice.hhplusecommerce.app.service.order.OrderProductService;
import practice.hhplusecommerce.app.service.order.OrderService;
import practice.hhplusecommerce.app.service.product.ProductService;
import practice.hhplusecommerce.app.service.user.UserService;
import practice.hhplusecommerce.global.exception.BadRequestException;
import practice.hhplusecommerce.global.exception.NotFoundException;

@Component
@RequiredArgsConstructor
public class OrderFacade {

  private final OrderService orderService;
  private final UserService userService;
  private final ProductService productService;
  private final OrderProductService orderProductService;
  private final DataPlatform dataPlatform;

  @Transactional
  public OrderFacadeResponseDto.OrderResponse order(OrderFacadeRequestDto.Create create) {
    User user = userService.getUser(create.getUserId());
    List<Product> productList = productService.getProductListByProductIdList(create.getProductList().stream().map(OrderProductCreate::getId).toList());

    if (productList.size() != create.getProductList().size()) {
      throw new NotFoundException("상품", true);
    }

    Integer totalProductPrice = 0;

    for (Product product : productList) {
      for (OrderFacadeRequestDto.OrderProductCreate orderProductCreate : create.getProductList()) {
        if (product.getId().equals(orderProductCreate.getId())) {
          product.validSalesPossible(orderProductCreate.getQuantity());
          product.decreaseStock(orderProductCreate.getQuantity());
          totalProductPrice += product.getPrice();
          break;
        }
      }
    }

    user.validBuyPossible(totalProductPrice);

    Order order = orderService.createOrder(totalProductPrice, user);
    List<OrderProduct> orderProductList = orderProductService.createOrderProduct(productList, order);

    user.decreaseAmount(totalProductPrice);

    String status = dataPlatform.send(order.getId(), order.getUser().getId(), order.getOrderTotalPrice());
    if (!status.equals("OK 200")) {
      throw new BadRequestException("주문정보를 데이처플랫폼에 전송 실패했습니다.");
    }

    OrderResponse orderResponse = OrderFacadeResponseDtoMapper.toOrderResponse(order);
    orderResponse.setOrderProductList(orderProductList.stream().map(OrderFacadeResponseDtoMapper::toOrderProductResponse).toList());
    return orderResponse;
  }
}
