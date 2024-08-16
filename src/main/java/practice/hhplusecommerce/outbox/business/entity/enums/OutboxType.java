package practice.hhplusecommerce.outbox.business.entity.enums;

import lombok.Getter;

@Getter
public enum OutboxType {

  DATA_PLATFORM("데이터플랫폼");

  private final String label;

  OutboxType(String label) {
    this.label = label;
  }
}
