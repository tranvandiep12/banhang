package truonggg.controller;

import java.util.List;

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
import truonggg.DTO.request.CartItemRequestDTO;
import truonggg.DTO.response.CartItemResponseDTO;
import truonggg.response.SuccessReponse;
import truonggg.service.CartItemService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

	private final CartItemService cartItemService;

	@PostMapping
	public SuccessReponse<CartItemResponseDTO> create(@RequestBody CartItemRequestDTO dto) {
		return SuccessReponse.of(cartItemService.create(dto));
	}

	@GetMapping("/{id}")
	public SuccessReponse<CartItemResponseDTO> getById(@PathVariable Integer id) {
		return SuccessReponse.of(cartItemService.getById(id));
	}

	@GetMapping
	public SuccessReponse<List<CartItemResponseDTO>> getAll() {
		return SuccessReponse.of(cartItemService.getAll());
	}

	@PutMapping("/{id}")
	public SuccessReponse<CartItemResponseDTO> update(@PathVariable Integer id, @RequestBody CartItemRequestDTO dto) {
		return SuccessReponse.of(cartItemService.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public SuccessReponse<String> delete(@PathVariable Integer id) {
		cartItemService.delete(id);
		return SuccessReponse.of("Đã xóa thành công");
	}

	// Lấy giỏ hàng theo userId
	@GetMapping("/user/{userId}")
	public SuccessReponse<List<CartItemResponseDTO>> getCartByUserId(@PathVariable Integer userId) {
		return SuccessReponse.of(cartItemService.getCartByUserId(userId));
	}

	// Xóa nhiều mục trong giỏ hàng
	@DeleteMapping("/batch")
	public SuccessReponse<String> deleteBatch(@RequestBody List<Integer> ids) {
		cartItemService.deleteBatch(ids);
		return SuccessReponse.of("Đã xóa các mục thành công");
	}

}
