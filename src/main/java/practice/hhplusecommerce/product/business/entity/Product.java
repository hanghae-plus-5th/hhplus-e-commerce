package practice.hhplusecommerce.product.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import practice.hhplusecommerce.common.baseEntity.BaseLocalDateTimeEntity;
import practice.hhplusecommerce.common.exception.BadRequestException;

@Getter
@Entity
@NoArgsConstructor
public class Product extends BaseLocalDateTimeEntity {

  @Id
  @Comment("고유번호")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Comment("상품명")
  private String name;

  @NotNull
  @Comment("상품명")
  private Integer price;

  @NotNull
  @Comment("상품명")
  private Integer stock;

  public Product(Long id, String name, Integer price, Integer stock) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.stock = stock;
  }

  public void validSalesPossible(Integer quantity) {
    if (this.stock < quantity) {
      throw new BadRequestException("재고가 부족합니다.");
    }
  }

  public void decreaseStock(Integer quantity) {
    this.stock -= quantity;
    if (this.stock < 0) {
      throw new BadRequestException("재고가 부족합니다.");
    }
  }

  public void update(String name, Integer stock, Integer price) {
    if (stock < 0) {
      throw new BadRequestException("재고가 0보다 작을 수 없습니다.");
    } else if (price < 0) {
      throw new BadRequestException("가격이 0보다 작을 수 없습니다.");
    }

    this.stock = stock;
    this.name = name;
    this.price = price;
  }
}
