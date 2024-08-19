package practice.hhplusecommerce.order.business.publisher;

import practice.hhplusecommerce.order.business.event.DataPlatformEvent;

public interface OrderMessagePublisher {

  void publish(DataPlatformEvent dataPlatformEvent);
}
