package practice.hhplusecommerce.app.entity.payment;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import practice.hhplusecommerce.app.entity.base.BaseLocalDateTimeEntity;
import practice.hhplusecommerce.app.entity.order.Order;

@Getter
@Setter
@Entity
public class Payment extends BaseLocalDateTimeEntity {

  @Id
  @NotNull
  @Comment("고유번호")
  private Long id;

  @NotNull
  @Comment("결제 금액")
  private Integer amount;

  @NotNull
  @Comment("주문 고유 번호")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
  private Order order;
}
