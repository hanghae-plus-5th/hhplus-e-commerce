package practice.hhplusecommerce.app.application.product.dto;

import practice.hhplusecommerce.app.domain.product.Product;

public class ProductFacadeDtoMapper {

  public static ProductFacadeDto toProductFacadeDto(Product product) {
      return new ProductFacadeDto(product.getId(), product.getName(), product.getPrice(), product.getStock());
  }
}
