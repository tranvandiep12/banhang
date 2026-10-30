package truonggg.DTO.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductResponseDTO {
	private Integer id;
	private String name;
	private String description;
	private Double price;
	private Integer quantity;
	private String imageUrl;
	private Boolean isActive;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer categoryId;
	private Integer promotionId;
	private Double discountPercent;

}