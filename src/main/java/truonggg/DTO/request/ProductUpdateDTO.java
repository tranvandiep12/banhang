package truonggg.DTO.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductUpdateDTO {
	private String name;

	private String description;

	@DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
	private Double price;

	@Min(value = 0, message = "Số lượng phải >= 0")
	private Integer quantity;

	// Dùng MultipartFile để nhận hình ảnh mới nếu có
	private MultipartFile image;

	// Giữ lại đường dẫn hình cũ nếu không cập nhật ảnh
	private String imageUrl;

	private Boolean isActive = true;

	private Integer categoryId;

	private Integer promotionId; // Không bắt buộc có khuyến mãi
}
