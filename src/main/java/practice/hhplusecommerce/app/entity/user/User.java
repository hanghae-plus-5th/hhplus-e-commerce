package practice.hhplusecommerce.app.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "users")
public class User extends BaseLocalDateTimeEntity {

  @Id
  @NotNull
  @Comment("고유번호")
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
}
