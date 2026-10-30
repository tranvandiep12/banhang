package truonggg.response;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuccessReponse<T> {
	private final OperationType OPERATION_TYPE = OperationType.Success;
	private final String message = "success";
	private ErrorCode code;
	private T data;
	private int size;
	// private final OffsetDateTime timestamp = OffsetDateTime.now();
	@JsonFormat(timezone = "Asia/Saigon", pattern = "dd/MM/yyyy hh:mm:ss")
	@JsonProperty(value = "thoi gian")
	private final Date timestamp = new Date();
	private int page;

	public static <T> SuccessReponse<T> of(final T data) {
		return SuccessReponse.<T>builder().data(data).code(ErrorCode.OK).size(getSize(data)).build();
	}

	public static <T> SuccessReponse<T> of(final T data, final int page) {
		return SuccessReponse.<T>builder().data(data).code(ErrorCode.OK).size(getSize(data)).page(page).build();
	}

	private static <T> int getSize(final T data) {
		if (Objects.nonNull(data) && data instanceof Collection<?>) {
			return ((Collection<?>) data).size();
		}
		return 0;
	}
}
