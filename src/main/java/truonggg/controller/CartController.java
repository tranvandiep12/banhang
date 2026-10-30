package truonggg.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.CartRequestDTO;
import truonggg.DTO.request.UpdateCartRequestDTO;
import truonggg.DTO.response.CartResponseDTO;
import truonggg.response.SuccessReponse;
import truonggg.service.CartService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;

	@GetMapping("/user/{userId}")
	public SuccessReponse<CartResponseDTO> getCartByUserId(@PathVariable Integer userId) {
		return SuccessReponse.of(cartService.getCartByUserId(userId));
	}

	@PostMapping
	public SuccessReponse<CartResponseDTO> createCart(@RequestBody CartRequestDTO dto) {
		return SuccessReponse.of(cartService.createCart(dto));
	}

	@PutMapping("/{id}")
	public SuccessReponse<CartResponseDTO> updateCart(@PathVariable Integer id, @RequestBody UpdateCartRequestDTO dto) {
		return SuccessReponse.of(cartService.updateCart(id, dto));
	}

	@DeleteMapping("/{id}")
	public SuccessReponse<String> deleteCart(@PathVariable Integer id) {
		cartService.deleteCart(id);
		return SuccessReponse.of("Đã xóa giỏ hàng");
	}
}
