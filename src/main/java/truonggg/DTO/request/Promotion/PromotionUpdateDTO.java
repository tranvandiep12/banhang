package truonggg.DTO.request.Promotion;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PromotionUpdateDTO {
	private String name;
	private String description;
	private Double discountPercent;
	private LocalDate startDate;
	private LocalDate endDate;
	private Boolean isActive;
}
