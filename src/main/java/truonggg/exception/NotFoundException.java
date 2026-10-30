package truonggg.exception;

import lombok.Getter;
import lombok.Setter;
import truonggg.response.ErrorCode;

@Setter
@Getter
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final ErrorCode errorCode = ErrorCode.NOT_FOUND;
	private final String domain = "user";// thay domain=entity tên hợp lí
	private String message;

	public NotFoundException(final String message) {
		this.message = message;
	}
}