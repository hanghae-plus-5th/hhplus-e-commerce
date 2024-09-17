package practice.hhplusecommerce.user.application.dto;

public record RedisMessageDto<T>(T message) {

	public RedisMessageDto(T message) {
		this.message = message;
	}
}
