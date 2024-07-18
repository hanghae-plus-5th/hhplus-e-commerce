package practice.hhplusecommerce.order.presentation;

import static practice.hhplusecommerce.iterceptor.JwtTokenInterceptor.TOKEN_INFO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.common.jwt.TokenInfoDto;
import practice.hhplusecommerce.order.application.OrderFacade;
import practice.hhplusecommerce.order.presentation.dto.OrderRequestDto.OrderCreate;
import practice.hhplusecommerce.order.presentation.dto.OrderRequestDtoMapper;
import practice.hhplusecommerce.order.presentation.dto.OrderResponseDto.OrderResponse;
import practice.hhplusecommerce.order.presentation.dto.OrderResponseDtoMapper;

@Tag(name = "주문")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

  private final OrderFacade orderFacade;

  /**
   * 주문과 결제가 같이 진행됩니다.
   * */
  @Operation(summary = "주문하기")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "404", description = "유저정보 or 상품정보가 존재하지 않습니다."),
      @ApiResponse(responseCode = "400", description = "{상품명}이 품절 상태 입니다.")
  })
  @PostMapping
  public OrderResponse create(
      @RequestAttribute(value = TOKEN_INFO) TokenInfoDto tokenInfoDto,
      @RequestBody OrderCreate create
  ) {
    return OrderResponseDtoMapper.toOrderResponse(orderFacade.order(tokenInfoDto.getUserId(), OrderRequestDtoMapper.toCreate(create)));
  }
}
