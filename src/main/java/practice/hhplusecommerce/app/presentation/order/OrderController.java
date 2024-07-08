package practice.hhplusecommerce.app.presentation.order;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.app.presentation.order.dto.request.OrderRequestDto.OrderCreate;
import practice.hhplusecommerce.app.presentation.order.dto.response.OrderResponseDto.OrderProductResponse;
import practice.hhplusecommerce.app.presentation.order.dto.response.OrderResponseDto.OrderResponse;

@RestController
@RequestMapping("/api/order")
public class OrderController {

  @PostMapping
  public OrderResponse create(
      @RequestBody OrderCreate create
  ) {
    return new OrderResponse(
        1L,
        150,
        List.of(new OrderProductResponse(
            2L,
            "수박",
            1,
            150
        ))
    );
  }
}
