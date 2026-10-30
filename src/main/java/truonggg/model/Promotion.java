package truonggg.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promotion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
	private Integer id;

	// @Column(nullable = false)
	private String name; // Tên chương trình khuyến mãi

	private String description;

	// @Column(nullable = false)
	private Double discountPercent;

	// @Column(nullable = false)
	private LocalDate startDate;

	// @Column(nullable = false)
	private LocalDate endDate;
	@Column(nullable = false)
	private Boolean isActive = true;

	@OneToMany(mappedBy = "promotion")
	private List<Product> products = new ArrayList<>();

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

}