package truonggg.DTO.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CategoryResponseDTO {
	private Integer id;
	private String name;
	private String description;
	private boolean isDeleted;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	// private List<ProductResponseDTO> products;
}
