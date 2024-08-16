package practice.hhplusecommerce.order.business.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import practice.hhplusecommerce.order.business.command.OrderCommand;

@Getter
@ToString
public class DataPlatformEvent {

  private final Long orderId;
  private final Long userId;
  private final Integer orderTotalPrice;
  private final Long outboxId;

  @JsonCreator
  public DataPlatformEvent(
      @JsonProperty("orderId") Long orderId,
      @JsonProperty("userId") Long userId,
      @JsonProperty("orderTotalPrice") Integer orderTotalPrice,
      @JsonProperty("outboxId") Long outboxId) {
    this.orderId = orderId;
    this.userId = userId;
    this.orderTotalPrice = orderTotalPrice;
    this.outboxId = outboxId;
  }

  // 기존 생성자 유지
  public DataPlatformEvent(OrderCommand.SendDataPlatform sendDataPlatform) {
    this.orderId = sendDataPlatform.orderId();
    this.userId = sendDataPlatform.userId();
    this.orderTotalPrice = sendDataPlatform.orderTotalPrice();
    this.outboxId = sendDataPlatform.outboxId();
  }
}
