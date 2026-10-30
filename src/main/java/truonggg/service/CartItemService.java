package truonggg.service;

import java.util.List;

import truonggg.DTO.request.CartItemRequestDTO;
import truonggg.DTO.response.CartItemResponseDTO;

public interface CartItemService {
	CartItemResponseDTO create(CartItemRequestDTO dto);

	CartItemResponseDTO update(Integer id, CartItemRequestDTO dto);

	void delete(Integer id);

	CartItemResponseDTO getById(Integer id);

	List<CartItemResponseDTO> getAll();

	List<CartItemResponseDTO> getCartByUserId(Integer userId);

	void deleteBatch(List<Integer> ids);
}
