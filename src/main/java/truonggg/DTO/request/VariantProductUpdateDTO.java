package truonggg.DTO.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VariantProductUpdateDTO {
	private String variantName;
	private Integer quantity;
	private Double price;
	private String imageUrl;
	private Integer productId;
}
