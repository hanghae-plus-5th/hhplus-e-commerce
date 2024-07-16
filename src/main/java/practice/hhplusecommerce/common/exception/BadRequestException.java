package practice.hhplusecommerce.common.exception;

import net.sf.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private String message;

	public BadRequestException() {
	}

	public BadRequestException(String message) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(message);
			if (jsonObject.has("error_description")) {
				this.message = jsonObject.getString("error_description");
			} else {
				this.message = message;
			}
		} catch (Exception ioe) {
			this.message = message;
		}
	}

	@Override
	public String getMessage() {
		return message;
	}
}
