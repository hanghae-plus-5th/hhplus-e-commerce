package practice.hhplusecommerce.app.presentation.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.hhplusecommerce.app.presentation.product.dto.response.ProductResponseDto.ProductResponse;

@Tag(name = "상품")
@RestController
@RequestMapping("/api/product")
public class ProductController {

  @Operation(summary = "상품 목록 조회")
  @GetMapping
  public List<ProductResponse> getProductList() {
    return List.of(new ProductResponse(
        1L,
        "수박",
        150,
        5
    ));
  }

  @Operation(summary = "인기 판매 상품 조회")
  @GetMapping("/top")
  public List<ProductResponse> getTop5ProductList() {
    return List.of(new ProductResponse(
        1L,
        "수박",
        150,
        5
    ));
  }
}
