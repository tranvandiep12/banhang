package truonggg.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Role {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(unique = true, nullable = false)
	private String name; // ROLE_USER, ROLE_ADMIN...

	private String description;

	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "role")
	private List<UserRole> list = new ArrayList<>();
}
