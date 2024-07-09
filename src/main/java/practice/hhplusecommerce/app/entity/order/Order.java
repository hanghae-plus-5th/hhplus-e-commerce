package practice.hhplusecommerce.app.entity.order;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import practice.hhplusecommerce.app.entity.base.BaseLocalDateTimeEntity;
import practice.hhplusecommerce.app.entity.user.User;

@Getter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order extends BaseLocalDateTimeEntity {

  @Id
  @NotNull
  @Comment("고유번호")
  private Long id;

  @NotNull
  @Comment("주문 총 금액")
  private Integer orderTotalPrice;

  @NotNull
  @Comment("유저 고유 번호")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
  private User user;

  public Order(Long id, Integer orderTotalPrice, User user) {
    this.id = id;
    this.orderTotalPrice = orderTotalPrice;
    this.user = user;
  }
}
