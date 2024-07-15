package practice.hhplusecommerce.app.order.business.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import practice.hhplusecommerce.app.common.entity.BaseLocalDateTimeEntity;
import practice.hhplusecommerce.app.user.business.entity.User;
import practice.hhplusecommerce.app.common.exception.BadRequestException;

@Getter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order extends BaseLocalDateTimeEntity {

  @Id
  @Comment("고유번호")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Comment("주문 총 금액")
  private Integer orderTotalPrice;

  @NotNull
  @Comment("유저 고유 번호")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "none", value = ConstraintMode.NO_CONSTRAINT))
  private User user;

  @NotNull
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderProduct> orderProductList = new ArrayList<>();


  public Order(Long id, Integer orderTotalPrice, User user) {
    if (orderTotalPrice < 0) {
      throw new BadRequestException("총 상품 금액이 잘못됐습니다.");
    }

    this.id = id;
    this.orderTotalPrice = orderTotalPrice;
    this.user = user;
  }

  public void addOrderProduct(OrderProduct orderProduct) {
    orderProductList.add(orderProduct);
  }
}
