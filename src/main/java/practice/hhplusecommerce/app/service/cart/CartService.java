package practice.hhplusecommerce.app.service.cart;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.domain.cart.Cart;
import practice.hhplusecommerce.app.service.cart.dto.response.CartServiceResponseDto;
import practice.hhplusecommerce.app.service.cart.dto.response.CartServiceResponseDtoMapper;
import practice.hhplusecommerce.global.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;

  public List<CartServiceResponseDto.Response> getCartList(Long userId) {
    return cartRepository.findAllByUserId(userId)
        .stream()
        .map(CartServiceResponseDtoMapper::toResponse)
        .toList();
  }

  public CartServiceResponseDto.Response getCart(Long cartId) {
    return CartServiceResponseDtoMapper.toResponse(
        cartRepository.findById(cartId)
            .orElseThrow(() -> new NotFoundException("장바구니", true))
    );
  }

  public CartServiceResponseDto.Response createCart(Cart cart) {
    return CartServiceResponseDtoMapper.toResponse(cartRepository.save(cart));
  }

  public CartServiceResponseDto.Response deleteCart(Long cartId) {
    Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new NotFoundException("장바구니", true));
    cartRepository.delete(cart);
    return CartServiceResponseDtoMapper.toResponse(cart);
  }
}
