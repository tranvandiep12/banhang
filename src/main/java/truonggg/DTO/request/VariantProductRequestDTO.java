package truonggg.DTO.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantProductRequestDTO {

	@NotBlank(message = "Tên biến thể không được để trống")
	private String variantName;

	@NotNull(message = "Số lượng không được để trống")
	@Min(value = 0, message = "Số lượng phải lớn hơn hoặc bằng 0")
	private Integer quantity;

	@NotNull(message = "Giá không được để trống")
	@Min(value = 0, message = "Giá phải lớn hơn hoặc bằng 0")
	private Double price;

	private String imageUrl;

	@NotNull(message = "Phải có Product ID")
	private Integer productId;
}
