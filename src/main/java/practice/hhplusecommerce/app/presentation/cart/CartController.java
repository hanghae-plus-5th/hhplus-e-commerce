package practice.hhplusecommerce.app.presentation.cart;

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

@RestController
@RequestMapping("/api/cart")
public class CartController {

  @GetMapping
  public CartResponse getCartList() {
    return new CartResponse(
        1L,
        "수박",
        15,
        5,
        1500,
        1L
    );
  }

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

  @DeleteMapping("/{cart-id}")
  public CartResponse delete(
      @RequestParam("userId") Long userId,
      @PathVariable("cart-id") String cartId
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
