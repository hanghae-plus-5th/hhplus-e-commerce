package practice.hhplusecommerce.app.application.cart;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.app.application.cart.dto.requst.CartFacadeRequestDto;
import practice.hhplusecommerce.app.application.cart.dto.response.CartFacadeResponseDto;
import practice.hhplusecommerce.app.application.cart.dto.response.CartFacadeResponseDtoMapper;
import practice.hhplusecommerce.app.domain.cart.Cart;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.app.service.cart.CartService;
import practice.hhplusecommerce.app.service.product.ProductService;
import practice.hhplusecommerce.app.service.user.UserService;

@Component
@RequiredArgsConstructor
public class CartFacade {

  private final CartService cartService;
  private final UserService userService;
  private final ProductService productService;

  @Transactional(readOnly = true)
  public List<CartFacadeResponseDto> getCartList(Long userId) {
    userService.getUser(userId);
    return cartService.getCartList(userId)
        .stream()
        .map(CartFacadeResponseDtoMapper::toCartFacadeResponseDto)
        .toList();
  }

  @Transactional
  public CartFacadeResponseDto addCart(CartFacadeRequestDto.Create create) {
    User user = userService.getUser(create.getUserId());
    Product product = productService.getProduct(create.getProductId());
    product.validSoldOut();
    return CartFacadeResponseDtoMapper.toCartFacadeResponseDto(
        cartService.createCart(
            new Cart(
                null,
                create.getQuantity(),
                user,
                product
            )
        )
    );
  }

  @Transactional
  public CartFacadeResponseDto deleteCart(Long cartId, Long userId) {
    userService.getUser(userId);
    cartService.getCart(cartId);
    return CartFacadeResponseDtoMapper.toCartFacadeResponseDto(cartService.deleteCart(cartId));
  }
}
