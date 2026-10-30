package truonggg.DTO.response;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInResponse {

	private String token;
	private Date expiredDate;
	private UserResponseDTO user;
	private CartResponseDTO cart;
}
