package truonggg.DTO.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRoleReponseDTO {

	private Integer id; // ID của bảng UserRole

	private Integer userId; // ID người dùng
	private String username; // Tên người dùng

	private Integer roleId; // ID vai trò
	private String roleName; // Tên vai trò

	private LocalDateTime assignedAt; // Thời điểm gán role
}
