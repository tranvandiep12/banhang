package truonggg.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import truonggg.response.Gender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String fullName;

	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;

	@Column(unique = true, nullable = false)
	private String email;

	private String phone;

	private String cccd;

	private String address;

	private LocalDate dob;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	private String avatarUrl;

	private Boolean isActive = true;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	@Transient
	private Integer roleId;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@Builder.Default
	private List<UserRole> list = new ArrayList<>();

	@Transient
	public Set<Role> getRoles() {
		return this.list.stream().map(UserRole::getRole).collect(Collectors.toSet());
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}
}
