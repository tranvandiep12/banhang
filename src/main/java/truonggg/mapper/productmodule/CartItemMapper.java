package truonggg.mapper.productmodule;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import truonggg.DTO.request.CartItemRequestDTO;
import truonggg.DTO.response.CartItemResponseDTO;
import truonggg.model.CartItem;
import truonggg.model.Product;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
	@Mapping(source = "cart.id", target = "cartId")
	@Mapping(source = "product.id", target = "productId")
	@Mapping(source = "product.name", target = "productName")
	@Mapping(source = "product.price", target = "price")
	@Mapping(source = "product.imageUrl", target = "imageUrl")
	@Mapping(expression = "java(getDiscountPercent(item.getProduct()))", target = "discountPercent")
	CartItemResponseDTO toResponseDTO(CartItem item);

	List<CartItemResponseDTO> toResponseDTOs(List<CartItem> items);

	@Mapping(source = "cartId", target = "cart.id")
	@Mapping(source = "productId", target = "product.id")
	CartItem toModel(CartItemRequestDTO dto);

	@Mapping(source = "cartId", target = "cart.id")
	@Mapping(source = "productId", target = "product.id")
	void updateModel(@MappingTarget CartItem entity, CartItemRequestDTO dto);

	default Double getDiscountPercent(Product product) {
		if (product.getPromotion() != null && Boolean.TRUE.equals(product.getPromotion().getIsActive())) {
			return product.getPromotion().getDiscountPercent();
		}
		return 0.0;
	}
}
