package practice.hhplusecommerce.user.business.dto;

public record ChargeUserAmountDto(Long userId, Integer amount) {

	public ChargeUserAmountDto(Long userId, Integer amount) {
		this.userId = userId;
		this.amount = amount;
	}
}
