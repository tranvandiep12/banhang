package truonggg.DTO.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import truonggg.response.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequestDTO {

	@NotBlank(message = "Họ tên không được để trống")
	private String fullName;

	@NotBlank(message = "Username không được để trống")
	private String username;

	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
	private String password;

	@Email(message = "Email không hợp lệ")
	@NotBlank(message = "Email không được để trống")
	private String email;

	@NotBlank(message = "Số điện thoại không được để trống")
	private String phone;

	@NotBlank(message = "CCCD không được để trống")
	private String cccd;

	private String address;

	private LocalDate dob;
	private LocalDateTime CreatedAt;

	@NotNull(message = "Giới tính không được để trống")
	private Gender gender;

	private String avatarUrl;

	private Boolean isActive = true;
	private Integer roleId;
}