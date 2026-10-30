package truonggg.DTO.response;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionReponseDTO {
	private Integer id;
	private String name;
	private String description;
	private Double discountPercent;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean isActive;
	private List<ProductResponseDTO> products;

}
