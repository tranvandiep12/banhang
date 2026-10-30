package truonggg.DTO.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class CartResponseDTO {
	private Integer id;
	private String name;
	private String discription;
	private Integer userId;
	private List<CartItemResponseDTO> items;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
