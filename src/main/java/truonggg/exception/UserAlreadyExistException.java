package truonggg.exception;

import lombok.Getter;
import lombok.Setter;
import truonggg.response.ErrorCode;

@Getter
@Setter
public class UserAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode = ErrorCode.ALREADY_EXIT;
	private final String domain = "user";
	private String message;

	public UserAlreadyExistException(final String message) {
		this.message = message;
	}

}