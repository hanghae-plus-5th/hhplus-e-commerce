package practice.hhplusecommerce.app.presentation.cart;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.app.presentation.cart.dto.request.CartRequestDto.CartCreate;
import practice.hhplusecommerce.app.presentation.cart.dto.response.CartResponseDto.CartResponse;

@Tag(name = "장바구니")
@RestController
@RequestMapping("/api/cart")
public class CartController {

  @Operation(summary = "장바구니 목록 조회")
  @ApiResponse(responseCode = "404", description = "유저정보가 존재하지 않습니다.")
  @GetMapping
  public List<CartResponse> getCartList(@RequestParam("userId") Long userId) {
    return List.of(new CartResponse(
        1L,
        "수박",
        15,
        5,
        1500,
        1L
    ));
  }

  @Operation(summary = "장바구니 추가")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "404", description = "유저정보 or 상품정보가 존재하지 않습니다."),
      @ApiResponse(responseCode = "400", description = "{상품명}이 품절 상태 입니다.")
  })
  @PostMapping
  public CartResponse create(
      @RequestBody CartCreate create
  ) {
    return new CartResponse(
        1L,
        "수박",
        15,
        5,
        1500,
        1L
    );
  }

  @Operation(summary = "장바구니 삭제")
  @ApiResponse(responseCode = "404", description = "유저정보가 존재하지 않습니다.")
  @DeleteMapping("/{cart-id}")
  public CartResponse delete(
      @RequestParam("userId") Long userId,
      @PathVariable("cart-id") Long cartId
  ) {
    return new CartResponse(
        1L,
        "수박",
        15,
        5,
        1500,
        1L
    );
  }
}
