package truonggg.mapper.productmodule;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import truonggg.DTO.request.CartRequestDTO;
import truonggg.DTO.request.UpdateCartRequestDTO;
import truonggg.DTO.response.CartResponseDTO;
import truonggg.model.Cart;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

	@Mapping(source = "user.id", target = "userId")
	CartResponseDTO toDTO(Cart cart);

	List<CartResponseDTO> toDTOs(List<Cart> carts);

	@Mapping(source = "userId", target = "user.id")
	Cart toModel(CartRequestDTO dto); // CartRequestDTO chứa userId

	@Mapping(source = "userId", target = "user.id")
	void updateModel(UpdateCartRequestDTO dto, @MappingTarget Cart cart);

}
