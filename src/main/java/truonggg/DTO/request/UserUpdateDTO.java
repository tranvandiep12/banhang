package truonggg.DTO.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import truonggg.response.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {

	private String fullName;

	private String username;

	private String password;

	private String email;

	private String phone;

	private String cccd;

	private String address;

	private LocalDate dob;

	private Gender gender;

	private String avatarUrl;

	private Boolean isActive;

	private Integer roleId;
}
