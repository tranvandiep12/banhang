package truonggg.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.CartItemRequestDTO;
import truonggg.DTO.response.CartItemResponseDTO;
import truonggg.exception.NotFoundException;
import truonggg.mapper.productmodule.CartItemMapper;
import truonggg.model.Cart;
import truonggg.model.CartItem;
import truonggg.model.Product;
import truonggg.repository.CartItemRepository;
import truonggg.repository.CartRepository;
import truonggg.repository.ProductRepository;
import truonggg.service.CartItemService;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final CartItemMapper cartItemMapper;

	@Override
	public CartItemResponseDTO create(CartItemRequestDTO dto) {
		// Kiểm tra cart & product tồn tại
		Cart cart = cartRepository.findById(dto.getCartId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy giỏ hàng"));
		Product product = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm"));

		// Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
		Optional<CartItem> optionalItem = cartItemRepository.findByCartAndProduct(cart, product);

		CartItem entity;
		if (optionalItem.isPresent()) {
			entity = optionalItem.get();
			entity.setQuantity(entity.getQuantity() + dto.getQuantity()); // Cộng dồn số lượng
		} else {
			entity = cartItemMapper.toModel(dto);
			entity.setCart(cart);
			entity.setProduct(product);
		}

		return cartItemMapper.toResponseDTO(cartItemRepository.save(entity));
	}

	@Override
	public CartItemResponseDTO update(Integer id, CartItemRequestDTO dto) {
		CartItem entity = cartItemRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy cart item"));

		cartItemMapper.updateModel(entity, dto);

		return cartItemMapper.toResponseDTO(cartItemRepository.save(entity));
	}

	@Override
	public void delete(Integer id) {
		CartItem item = cartItemRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy cart item"));
		cartItemRepository.delete(item);
	}

	@Override
	public CartItemResponseDTO getById(Integer id) {
		CartItem item = cartItemRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy cart item"));
		return cartItemMapper.toResponseDTO(item);
	}

	@Override
	public List<CartItemResponseDTO> getAll() {
		return cartItemMapper.toResponseDTOs(cartItemRepository.findAll());
	}

	@Override
	public List<CartItemResponseDTO> getCartByUserId(Integer userId) {
		// Tìm cart theo userId
		Cart cart = cartRepository.findByUserId(userId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy giỏ hàng của người dùng"));

		// Lấy các item trong cart
		List<CartItem> items = cartItemRepository.findByCartId(cart.getId());

		return cartItemMapper.toResponseDTOs(items);
	}

	@Override
	public void deleteBatch(List<Integer> ids) {
		List<CartItem> items = cartItemRepository.findAllById(ids);
		if (items.isEmpty()) {
			throw new NotFoundException("Không có cart item nào được tìm thấy để xóa");
		}
		cartItemRepository.deleteAll(items);
	}

}
