package truonggg.service;

import java.util.List;
import java.util.Optional;

import truonggg.model.Role;

public interface RoleService {
	List<Role> getAllRole();

	Role save(Role newRole);

	Optional<Role> findById(Integer id);
}
