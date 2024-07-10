package practice.hhplusecommerce.app.application.cart;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.cart.dto.requst.CartFacadeRequestDto;
import practice.hhplusecommerce.app.application.cart.dto.response.CartFacadeResponseDto;
import practice.hhplusecommerce.app.service.cart.CartService;

@Component
@RequiredArgsConstructor
public class CartFacade {

  private final CartService cartService;

  @Transactional(readOnly = true)
  public List<CartFacadeResponseDto> getCartList(Long userId) {
      return null;
  }

  @Transactional
  public CartFacadeResponseDto addCart(CartFacadeRequestDto.Create create) {
    return null;
  }

  @Transactional
  public CartFacadeResponseDto deleteCart(Long cartId, Long userId) {

    return null;
  }

}
