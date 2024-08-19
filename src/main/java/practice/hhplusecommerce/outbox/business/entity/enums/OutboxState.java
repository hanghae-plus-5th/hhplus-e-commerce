package practice.hhplusecommerce.outbox.business.entity.enums;

import lombok.Getter;

@Getter
public enum OutboxState {

  INIT("생성"),
  PUBLISH("발행");

  private final String label;

  OutboxState(String label) {
    this.label = label;
  }
}
