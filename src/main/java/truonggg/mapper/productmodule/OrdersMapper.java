package truonggg.mapper.productmodule;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import truonggg.DTO.request.OrderRequestDTO;
import truonggg.DTO.response.OrderItemResponseDTO;
import truonggg.DTO.response.OrderResponseDTO;
import truonggg.model.OrderItem;
import truonggg.model.Orders;

@Mapper(componentModel = "spring")
public interface OrdersMapper {

	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "orderItems", target = "items")
	OrderResponseDTO toDTO(Orders order);

	@Mapping(source = "product.id", target = "productId")
	@Mapping(source = "product.name", target = "productName")
	@Mapping(source = "product.imageUrl", target = "imageUrl")
	@Mapping(source = "product.promotion.discountPercent", target = "discountPercent")
	OrderItemResponseDTO toOrderItemDetailDTO(OrderItem item);

	List<OrderItemResponseDTO> toOrderItemDetailDTOList(List<OrderItem> items);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "orderItems", ignore = true)
	@Mapping(target = "orderDate", ignore = true)
	@Mapping(target = "totalAmount", ignore = true)
	@Mapping(target = "status", ignore = true)
	Orders toModel(OrderRequestDTO request);
}
