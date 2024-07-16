package practice.hhplusecommerce.cart.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.hhplusecommerce.cart.application.dto.requst.CartFacadeRequestDto.Create;
import practice.hhplusecommerce.cart.application.dto.response.CartFacadeResponseDto;
import practice.hhplusecommerce.cart.application.dto.response.CartFacadeResponseDtoMapper;
import practice.hhplusecommerce.cart.business.service.CartService;
import practice.hhplusecommerce.product.business.entity.Product;
import practice.hhplusecommerce.user.business.entity.User;
import practice.hhplusecommerce.product.business.service.ProductService;
import practice.hhplusecommerce.user.business.service.UserService;

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
  public CartFacadeResponseDto addCart(Create create) {
    User user = userService.getUser(create.getUserId());
    Product product = productService.getProduct(create.getProductId());
    product.validSalesPossible(create.getQuantity());

    return CartFacadeResponseDtoMapper.toCartFacadeResponseDto(
        cartService.createCart(
            create.getQuantity(),
            user,
            product
        )
    );
  }

  public CartFacadeResponseDto deleteCart(Long cartId, Long userId) {
    userService.getUser(userId);
    cartService.getCart(cartId);
    return CartFacadeResponseDtoMapper.toCartFacadeResponseDto(cartService.deleteCart(cartId));
  }
}
