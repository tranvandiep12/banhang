package truonggg.DTO.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDTO {
	@NotBlank(message = "Tên danh mục không được để trống")
	@Size(max = 100, message = "Tên danh mục không vượt quá 100 ký tự")
	private String name;

	@Size(max = 255, message = "Mô tả không vượt quá 255 ký tự")
	private String description;
}