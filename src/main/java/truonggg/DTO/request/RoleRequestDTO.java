package truonggg.DTO.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequestDTO {

	private Integer id;

	@NotBlank(message = "Tên vai trò không được để trống")
	private String name; // Ví dụ: ROLE_USER, ROLE_ADMIN

	private String description;
}