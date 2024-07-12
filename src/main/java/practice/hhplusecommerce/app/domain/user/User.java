package practice.hhplusecommerce.app.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import practice.hhplusecommerce.app.domain.base.BaseLocalDateTimeEntity;
import practice.hhplusecommerce.global.exception.BadRequestException;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseLocalDateTimeEntity {

  @Id
  @Comment("고유번호")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  @Comment("유저명")
  private String name;

  @NotNull
  @Comment("잔액")
  private Integer amount;

  public User(Long id, String name, Integer amount) {
    this.id = id;
    this.name = name;
    this.amount = amount;
  }

  public void chargeAmount(Integer chargeAmount) {
    if (chargeAmount <= 0) {
      throw new BadRequestException("1원 이상만 충전이 가능합니다.");
    }
    this.amount += chargeAmount;
  }

  public void decreaseAmount(Integer totalProductPrice) {
    this.amount -= totalProductPrice;
    if (this.amount < 0) {
      throw new BadRequestException("잔액이 부족합니다.");
    }
  }

  public void validBuyPossible(Integer totalProductPrice) {
    if (this.amount - totalProductPrice < 0) {
      throw new BadRequestException("잔액이 부족합니다.");
    }
  }
}
