package truonggg.DTO.response;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
	private Integer productId;
	private String productName;
	private Double price;
	private Integer quantity;
	private String imageUrl;
	private Integer discountPercent;
}
