package truonggg.DTO.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truonggg.response.Gender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("username")
	private String username;

	private String password;

	private String email;

	private String phone;

	private String cccd;

	private String address;

	private LocalDate dob;

	private Gender gender;
}