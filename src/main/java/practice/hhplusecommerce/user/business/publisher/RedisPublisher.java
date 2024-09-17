package practice.hhplusecommerce.user.business.publisher;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import practice.hhplusecommerce.user.application.dto.RedisMessageDto;

@Service
public class RedisPublisher {

	private final RedisTemplate<String, Object> template;

	public RedisPublisher(RedisTemplate<String, Object> template) {
		this.template = template;
	}

	/**
	 * Object publish
	 */
	public void publish(ChannelTopic topic, String message) {
		template.convertAndSend(topic.getTopic(), message);
	}
}
