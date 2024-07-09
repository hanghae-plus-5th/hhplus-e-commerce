package practice.hhplusecommerce.app.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import practice.hhplusecommerce.app.entity.base.BaseLocalDateTimeEntity;

@Getter
@Entity
@NoArgsConstructor
public class Product extends BaseLocalDateTimeEntity {

  @Id
  @NotNull
  @Comment("고유번호")
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
}
