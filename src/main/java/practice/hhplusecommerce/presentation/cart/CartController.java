package practice.hhplusecommerce.presentation.cart;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.presentation.cart.dto.request.CartRequestDto;
import practice.hhplusecommerce.presentation.cart.dto.response.CartResponseDto;

@RestController
@RequestMapping("/api/cart")
public class CartController {

  @GetMapping
  public CartResponseDto.CartResponse getCartList() {
    return new CartResponseDto.CartResponse(
        1L,
        "수박",
        15,
        5,
        1500,
        1L
    );
  }

  @PostMapping
  public CartResponseDto.CartResponse create(
      @RequestBody CartRequestDto.CartCreate create
  ) {
    return new CartResponseDto.CartResponse(
        1L,
        "수박",
        15,
        5,
        1500,
        1L
    );
  }

  @DeleteMapping("/{cart-id}")
  public CartResponseDto.CartResponse delete(
      @RequestParam("userId") Long userId,
      @PathVariable("cart-id") String cartId
  ) {
    return new CartResponseDto.CartResponse(
        1L,
        "수박",
        15,
        5,
        1500,
        1L
    );
  }
}
