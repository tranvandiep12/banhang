package truonggg.controller;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.CartRequestDTO;
import truonggg.DTO.request.SignInRequest;
import truonggg.DTO.request.SignUpRequest;
import truonggg.DTO.response.CartResponseDTO;
import truonggg.DTO.response.SignInResponse;
import truonggg.DTO.response.UserResponseDTO;
import truonggg.mapper.user.UserMapper;
import truonggg.model.User;
import truonggg.repository.CartRepository;
import truonggg.repository.UserRepository;
import truonggg.response.SuccessReponse;
import truonggg.sercurity.CustomUserDetails;
import truonggg.service.CartService;
import truonggg.service.UserService;
import truonggg.utils.JwtUtils;

//controller dùng để đăng nhập
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/auth")
public class AuthController {

	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final CartService cartService;
	private final CartRepository cartRepository;

	@PostMapping(path = "/signUp")
	public SuccessReponse<Boolean> signUp(@RequestBody SignUpRequest request) {
		User user = UserMapper.INSTANCE.toModel(request);
//		log.info("{}", user.getUserName());
		return SuccessReponse.of(this.userService.signUp(user));
	}

	@PostMapping(path = "/signIn")
	public SuccessReponse<SignInResponse> signIn(@RequestBody SignInRequest request) {
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));

		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		String accessToken = jwtUtils.generateToken(userDetails);
		Date expiredDate = jwtUtils.extractExpiration(accessToken);

		User user = userRepository.findByUsername(request.getUserName()).orElseThrow();
		UserResponseDTO userDTO = userMapper.toDTO(user);

		// Giỏ hàng: nếu chưa có thì tạo
		CartResponseDTO cartDTO;
		if (cartRepository.findByUserId(user.getId()).isEmpty()) {
			CartRequestDTO cartRequest = new CartRequestDTO();
			cartRequest.setUserId(user.getId());
			cartRequest.setName("Giỏ hàng của " + user.getFullName());
			cartRequest.setDiscription("Tự động tạo khi đăng nhập");
			cartDTO = cartService.createCart(cartRequest);
		} else {
			cartDTO = cartService.getCartByUserId(user.getId());
		}

		SignInResponse response = SignInResponse.builder().token(accessToken).expiredDate(expiredDate).user(userDTO)
				.cart(cartDTO) // gắn cart vào response
				.build();

		return SuccessReponse.of(response);
	}

}