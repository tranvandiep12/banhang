package truonggg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import truonggg.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	List<Role> findByName(final String name);

	Optional<Role> findFirstByName(String name);
}
