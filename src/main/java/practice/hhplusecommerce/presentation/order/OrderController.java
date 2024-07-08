package practice.hhplusecommerce.presentation.order;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.presentation.order.dto.request.OrderRequestDto;
import practice.hhplusecommerce.presentation.order.dto.response.OrderResponseDto;

@RestController
@RequestMapping("/api/order")
public class OrderController {

  @PostMapping
  public OrderResponseDto.OrderResponse create(
      @RequestBody OrderRequestDto.OrderCreate create
  ) {
    return new OrderResponseDto.OrderResponse(
        1L,
        150,
        List.of(new OrderResponseDto.OrderProductResponse(
            2L,
            "수박",
            1,
            150
        ))
    );
  }
}
