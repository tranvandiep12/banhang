package truonggg.DTO.request.Promotion;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PromotionRequestDTO {
	@NotBlank(message = "Tên khuyến mãi không được để trống")
	private String name;

	private String description;

	@NotNull(message = "Phần trăm giảm giá không được để trống")
	@Min(value = 0, message = "Phần trăm giảm giá phải >= 0")
	private Double discountPercent;

	@NotNull(message = "Ngày bắt đầu không được để trống")
	@FutureOrPresent(message = "Ngày bắt đầu phải là hiện tại hoặc tương lai")
	private LocalDate startDate;

	@NotNull(message = "Ngày kết thúc không được để trống")
	private LocalDate endDate;
}
