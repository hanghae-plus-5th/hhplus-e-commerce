package practice.hhplusecommerce.order.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.order.presentation.dto.request.OrderRequestDto.OrderCreate;
import practice.hhplusecommerce.order.presentation.dto.response.OrderResponseDto.OrderProductResponse;
import practice.hhplusecommerce.order.presentation.dto.response.OrderResponseDto.OrderResponse;

@Tag(name = "주문")
@RestController
@RequestMapping("/api/order")
public class OrderController {

  @Operation(summary = "주문하기")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "404", description = "유저정보 or 상품정보가 존재하지 않습니다."),
      @ApiResponse(responseCode = "400", description = "{상품명}이 품절 상태 입니다.")
  })
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
