package practice.hhplusecommerce.app.infrastrucure.dataPlatform;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.app.service.dataPlatform.DataPlatform;

@Slf4j
@Service
public class DataPlatformImpl implements DataPlatform {

  @Override
  public String send(Long id, Long userId, Integer orderTotalPrice) {
    log.info("주문고유번호 : " + id);
    log.info("유저고유번호 : " + userId);
    log.info("총주문금액 : " + orderTotalPrice);
    log.info("전송완료");
    return "OK 200";
  }
}
