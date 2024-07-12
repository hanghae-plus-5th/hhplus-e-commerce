package practice.hhplusecommerce.app.application.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.order.dto.request.OrderFacadeRequestDto;
import practice.hhplusecommerce.app.application.order.dto.response.OrderFacadeResponseDto;
import practice.hhplusecommerce.app.service.order.OrderService;

@Component
@RequiredArgsConstructor
public class OrderFacade {

  private final OrderService orderService;

  @Transactional
  public OrderFacadeResponseDto.OrderResponse order(OrderFacadeRequestDto.Create create) {
    return null;
  }
}
