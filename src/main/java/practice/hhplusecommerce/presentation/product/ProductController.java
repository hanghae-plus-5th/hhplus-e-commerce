package practice.hhplusecommerce.presentation.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.presentation.product.dto.response.ProductResponseDto;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @GetMapping
  public ProductResponseDto.ProductResponse getProductList() {
    return new ProductResponseDto.ProductResponse(
        1L,
        "수박",
        150,
        5
    );
  }

  @GetMapping("/top")
  public ProductResponseDto.ProductResponse getTop5ProductList() {
    return new ProductResponseDto.ProductResponse(
        1L,
        "수박",
        150,
        5
    );
  }
}
