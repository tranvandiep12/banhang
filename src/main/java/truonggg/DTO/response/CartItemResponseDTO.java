package truonggg.DTO.response;

import lombok.Data;

@Data
public class CartItemResponseDTO {
	private Integer id;
	private Integer quantity;
	private Integer cartId;
	private Integer productId;
	private String productName; // option: dùng khi muốn hiển thị tên sản phẩm
	private Double price; // giá sản phẩm
	private String imageUrl;
	private Double discountPercent;
}
