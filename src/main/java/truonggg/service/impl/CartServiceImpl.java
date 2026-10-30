package truonggg.service.impl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.CartRequestDTO;
import truonggg.DTO.request.UpdateCartRequestDTO;
import truonggg.DTO.response.CartResponseDTO;
import truonggg.exception.NotFoundException;
import truonggg.mapper.productmodule.CartMapper;
import truonggg.model.Cart;
import truonggg.model.User;
import truonggg.repository.CartRepository;
import truonggg.repository.UserRepository;
import truonggg.service.CartService;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final CartMapper cartMapper;

	@Override
	public CartResponseDTO getCartByUserId(Integer userId) {
		Cart cart = cartRepository.findByUserId(userId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy giỏ hàng của user " + userId));
		return cartMapper.toDTO(cart);
	}

	@Override
	public CartResponseDTO createCart(CartRequestDTO dto) {
		User user = userRepository.findById(dto.getUserId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));

		Cart cart = Cart.builder().name(dto.getName()).discription(dto.getDiscription()).user(user)
				.createdAt(LocalDateTime.now()).build();

		return cartMapper.toDTO(cartRepository.save(cart));
	}

	@Override
	public CartResponseDTO updateCart(Integer id, UpdateCartRequestDTO dto) {
		Cart cart = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy giỏ hàng"));

		cart.setName(dto.getName());
		cart.setDiscription(dto.getDiscription());
		cart.setUpdatedAt(LocalDateTime.now());

		return cartMapper.toDTO(cartRepository.save(cart));
	}

	@Override
	public void deleteCart(Integer id) {
		Cart cart = cartRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy giỏ hàng"));
		cartRepository.delete(cart);
	}
}
