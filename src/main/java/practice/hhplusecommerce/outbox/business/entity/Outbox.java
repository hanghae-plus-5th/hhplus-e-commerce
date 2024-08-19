package practice.hhplusecommerce.outbox.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import practice.hhplusecommerce.common.baseEntity.BaseLocalDateTimeEntity;
import practice.hhplusecommerce.outbox.business.entity.enums.OutboxState;
import practice.hhplusecommerce.outbox.business.entity.enums.OutboxType;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbox extends BaseLocalDateTimeEntity {

  @Id
  @Comment("고유번호")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Comment("메시지")
  private String message;

  @Comment("재시도 횟수")
  @ColumnDefault("0")
  private Integer retryCount;

  @NotNull
  @Comment("타입")
  @Enumerated(EnumType.STRING)
  private OutboxType type;

  @NotNull
  @Comment("상태")
  @Enumerated(EnumType.STRING)
  private OutboxState state;

  public Outbox(OutboxType type) {
    this.type = type;
    this.state = OutboxState.INIT;
  }

  public void publish() {
    this.state = OutboxState.PUBLISH;
  }

  public void saveMessage(String message) {
    this.message = message;
  }

  public void retry() {
    retryCount = retryCount + 1;
  }
}
