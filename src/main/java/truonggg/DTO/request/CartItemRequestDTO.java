package truonggg.DTO.request;

import lombok.Data;

@Data
public class CartItemRequestDTO {
	private Integer quantity;
	private Integer cartId;
	private Integer productId;
}
