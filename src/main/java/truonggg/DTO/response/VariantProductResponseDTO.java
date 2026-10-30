package truonggg.DTO.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VariantProductResponseDTO {

	private Integer id;

	private String variantName;

	private Integer quantity;

	private Double price;

	private String imageUrl;

	private Integer productId; // Để tham chiếu ngược lại product nếu cần
}