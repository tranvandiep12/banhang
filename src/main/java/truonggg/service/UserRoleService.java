package truonggg.service;

import java.util.List;

import truonggg.model.UserRole;

public interface UserRoleService {
	List<UserRole> getAll();

	UserRole save(UserRole newUserRole);
}
