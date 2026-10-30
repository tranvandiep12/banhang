package truonggg.handler;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;
import truonggg.exception.NotFoundException;
import truonggg.response.ErrorCode;
import truonggg.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(exception = { NotFoundException.class })
	// tương tự(1)
	public ErrorResponse handle(final NotFoundException exception) {
		return ErrorResponse.of(exception.getErrorCode(), exception.getDomain(), exception.getMessage());
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(exception = { Exception.class })
	// không cần gọi thủ công vì có exceptionhandler nên system tự xử lí(1)
	public ErrorResponse handle(final Exception exception) {
		return ErrorResponse.of(ErrorCode.INTERNAL_SERVER, "system", exception.getMessage());
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = { MethodArgumentNotValidException.class })
	public ErrorResponse handle(final MethodArgumentNotValidException exception) {
		Map<String, Object> details = exception.getBindingResult().getFieldErrors().stream()
				.collect(Collectors.toMap(x -> x.getField().toString(), x -> x.getDefaultMessage(), (a, b) -> b));

		return ErrorResponse.of(ErrorCode.INTERNAL_SERVER, "request", "Validation failed", details);
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(exception = { ConstraintViolationException.class })
	public ErrorResponse handle(final ConstraintViolationException exception) {
		Map<String, Object> details = exception.getConstraintViolations().stream()
				.collect(Collectors.toMap(x -> x.getPropertyPath().toString(), x -> x.getMessage(), (a, b) -> b));

		return ErrorResponse.of(ErrorCode.INTERNAL_SERVER, "request", "Validation failed", details);
	}
}