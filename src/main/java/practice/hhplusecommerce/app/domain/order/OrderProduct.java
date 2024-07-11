package practice.hhplusecommerce.app.domain.order;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import practice.hhplusecommerce.app.domain.product.Product;

@Getter
@Entity
@NoArgsConstructor
public class OrderProduct {

  @Id
  @NotNull
  @Comment("고유번호")
  private Long id;

  @NotNull
  @Comment("상품명")
  private String name;

  @NotNull
  @Comment("상품 금액")
  private Integer price;

  @NotNull
  @Comment("구매 수량")
  private Integer quantity;

  @NotNull
  @Comment("주문 고유 번호")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
  private Order order;

  @NotNull
  @Comment("상품 고유 번호")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
  private Product product;

  public OrderProduct(Long id, String name, Integer price, Integer quantity, Order order, Product product) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.quantity = quantity;
    this.order = order;
    this.product = product;
  }
}
