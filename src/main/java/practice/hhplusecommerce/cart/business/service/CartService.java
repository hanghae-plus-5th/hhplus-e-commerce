package practice.hhplusecommerce.cart.business.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.cart.business.entity.Cart;
import practice.hhplusecommerce.cart.business.dto.CartServiceResponseDto;
import practice.hhplusecommerce.cart.business.dto.CartServiceResponseDtoMapper;
import practice.hhplusecommerce.cart.business.repository.CartRepository;
import practice.hhplusecommerce.common.exception.NotFoundException;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.user.business.entity.User;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;

  public List<CartServiceResponseDto.Response> getCartList(Long userId) {
    return cartRepository.findAllByUserId(userId)
        .stream()
        .map(CartServiceResponseDtoMapper::toResponse)
        .toList();
  }

  @Transactional
  public CartServiceResponseDto.Response getCart(Long cartId) {
    return CartServiceResponseDtoMapper.toResponse(
        cartRepository.findById(cartId)
            .orElseThrow(() -> {
              log.error("CartService.getCart parameter cartId : {}", cartId);
              return new NotFoundException("장바구니", true);
            })
    );
  }

  public CartServiceResponseDto.Response createCart(Integer quantity, User user, Product product) {
    Cart cart = new Cart(null, quantity, user, product);
    return CartServiceResponseDtoMapper.toResponse(cartRepository.save(cart));
  }

  @Transactional
  public CartServiceResponseDto.Response deleteCart(Long cartId) {
    Cart cart = cartRepository.findById(cartId).orElseThrow(() -> {
      log.error("CartService.deleteCart parameter cartId : {}", cartId);
      return new NotFoundException("장바구니", true);
    });
    cartRepository.delete(cart);
    return CartServiceResponseDtoMapper.toResponse(cart);
  }
}
