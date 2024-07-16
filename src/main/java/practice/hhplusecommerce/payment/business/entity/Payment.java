package practice.hhplusecommerce.payment.business.entity;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;
import practice.hhplusecommerce.common.baseEntity.BaseLocalDateTimeEntity;
import practice.hhplusecommerce.order.business.entity.Order;

@Getter
@Setter
@Entity
public class Payment extends BaseLocalDateTimeEntity {

  @Id
  @Comment("고유번호")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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
