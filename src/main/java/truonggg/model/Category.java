package truonggg.model;

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
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự động tăng
	private Integer id;

	@Column(nullable = false, unique = true)
	private String name;

	private String description;
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
	private boolean deleted = true;

	@OneToMany(mappedBy = "category")
	List<Product> list = new ArrayList<>();

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
