package practice.hhplusecommerce.app.presentation.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.app.presentation.product.dto.response.ProductResponseDto.ProductResponse;

@RestController
@RequestMapping("/api/product")
public class ProductController {

  @GetMapping
  public ProductResponse getProductList() {
    return new ProductResponse(
        1L,
        "수박",
        150,
        5
    );
  }

  @GetMapping("/top")
  public ProductResponse getTop5ProductList() {
    return new ProductResponse(
        1L,
        "수박",
        150,
        5
    );
  }
}
