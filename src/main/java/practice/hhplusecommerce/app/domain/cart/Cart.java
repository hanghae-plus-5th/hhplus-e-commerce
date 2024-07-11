package practice.hhplusecommerce.app.domain.cart;

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
import practice.hhplusecommerce.app.domain.base.BaseLocalDateTimeEntity;
import practice.hhplusecommerce.app.domain.product.Product;
import practice.hhplusecommerce.app.domain.user.User;
import practice.hhplusecommerce.global.exception.BadRequestException;

@Getter
@Entity
@NoArgsConstructor
public class Cart extends BaseLocalDateTimeEntity {

  @Id
  @NotNull
  @Comment("고유번호")
  private Long id;

  @NotNull
  @Comment("장바구니 담은 상품 개수")
  private Integer quantity;

  @NotNull
  @Comment("유저 고유 번호")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
  private User user;

  @NotNull
  @Comment("상품 고유 번호")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
  private Product product;

  public Cart(Long id, Integer quantity, User user, Product product) {
    if (quantity <= 0){
        throw new BadRequestException("상품개수는 1개 이상이여야 합니다.");
    }

    this.id = id;
    this.quantity = quantity;
    this.user = user;
    this.product = product;
  }
}
