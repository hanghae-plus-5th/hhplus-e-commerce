package practice.hhplusecommerce.app.order.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.common.exception.BadRequestException;
import practice.hhplusecommerce.app.common.exception.NotFoundException;
import practice.hhplusecommerce.app.order.application.dto.request.OrderFacadeRequestDto.Create;
import practice.hhplusecommerce.app.order.application.dto.request.OrderFacadeRequestDto.OrderProductCreate;
import practice.hhplusecommerce.app.order.application.dto.response.OrderFacadeResponseDto.OrderResponse;
import practice.hhplusecommerce.app.order.application.dto.response.OrderFacadeResponseDtoMapper;
import practice.hhplusecommerce.app.order.business.entity.Order;
import practice.hhplusecommerce.app.order.business.service.OrderService;
import practice.hhplusecommerce.app.payment.infrastructure.dataPlatform.DataPlatform;
import practice.hhplusecommerce.app.product.business.entity.Product;
import practice.hhplusecommerce.app.product.business.service.ProductService;
import practice.hhplusecommerce.app.user.business.entity.User;
import practice.hhplusecommerce.app.user.business.service.UserService;

@Component
@RequiredArgsConstructor
public class OrderFacade {

  private final OrderService orderService;
  private final UserService userService;
  private final ProductService productService;
  private final DataPlatform dataPlatform;

  @Transactional
  public OrderResponse order(Create create) {
    User user = userService.getUser(create.getUserId());
    List<Product> productList = productService.getProductListByProductIdList(create.getProductList().stream().map(OrderProductCreate::getId).toList());

    if (productList.size() != create.getProductList().size()) {
      throw new NotFoundException("상품", true);
    }

    Integer totalProductPrice = 0;

    for (Product product : productList) {
      for (OrderProductCreate orderProductCreate : create.getProductList()) {
        if (product.getId().equals(orderProductCreate.getId())) {
          product.validSalesPossible(orderProductCreate.getQuantity());
          product.decreaseStock(orderProductCreate.getQuantity());
          totalProductPrice += product.getPrice() * orderProductCreate.getQuantity();
          break;
        }
      }
    }

    user.validBuyPossible(totalProductPrice);

    Order order = orderService.createOrder(totalProductPrice, user, productList, create.getProductList());
    user.decreaseAmount(totalProductPrice);

    String status = dataPlatform.send(order.getId(), order.getUser().getId(), order.getOrderTotalPrice());
    if (!status.equals("OK 200")) {
      throw new BadRequestException("주문정보를 데이처플랫폼에 전송 실패했습니다.");
    }

    OrderResponse orderResponse = OrderFacadeResponseDtoMapper.toOrderResponse(order);
    orderResponse.setOrderProductList(order.getOrderProductList().stream().map(OrderFacadeResponseDtoMapper::toOrderProductResponse).toList());
    return orderResponse;
  }
}
