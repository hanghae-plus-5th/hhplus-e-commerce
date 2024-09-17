package practice.hhplusecommerce.user.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.user.business.dto.ChargeUserAmountDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscribeListener implements MessageListener {

	private final RedisTemplate<String, Object> template;
	private final ObjectMapper objectMapper;

	@Override
	public void onMessage(CharSequence charSequence, Object pattern) {
		try {
			ChargeUserAmountDto chargeUserAmountDto = new ObjectMapper().readValue(message.toString(), ChargeUserAmountDto.class);

			log.info("Redis Subscribe Channel : {}", channel);
			log.info("Redis SUB Message : {}", chargeUserAmountDto);

			// 메시지 처리 로직 (예: DB에 저장 등)

		} catch (JsonProcessingException e) {
			log.error("Failed to parse message: {}", e.getMessage());
		}
	}
	
}
