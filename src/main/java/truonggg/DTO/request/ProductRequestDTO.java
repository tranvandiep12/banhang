package truonggg.DTO.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequestDTO {

	@NotBlank(message = "Tên sản phẩm không được để trống")
	private String name;

	private String description;

	@NotNull(message = "Giá không được để trống")
	@DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
	private Double price;

	@Min(value = 0, message = "Số lượng phải >= 0")
	private Integer quantity;
	private Boolean isActive = true;
	private MultipartFile image;
	@NotNull(message = "Danh mục (categoryId) không được để trống")
	private Integer categoryId;
	private Integer promotionId; // Không bắt buộc có khuyến mãi
}