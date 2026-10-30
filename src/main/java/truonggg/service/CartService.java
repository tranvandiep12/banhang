package truonggg.service;

import truonggg.DTO.request.CartRequestDTO;
import truonggg.DTO.request.UpdateCartRequestDTO;
import truonggg.DTO.response.CartResponseDTO;

public interface CartService {
	CartResponseDTO getCartByUserId(Integer userId);

	CartResponseDTO createCart(CartRequestDTO dto);

	CartResponseDTO updateCart(Integer id, UpdateCartRequestDTO dto);

	void deleteCart(Integer id);
}