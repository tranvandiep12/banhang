package truonggg.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import truonggg.exception.NotFoundException;
import truonggg.model.Role;
import truonggg.model.User;
import truonggg.model.UserRole;
import truonggg.repository.RoleRepository;
import truonggg.repository.UserRepository;
import truonggg.repository.UserRoleRepository;
import truonggg.service.UserRoleService;

@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl implements UserRoleService {
	private final UserRoleRepository userRoleRepository;
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Override
	public List<UserRole> getAll() {
		// TODO Auto-generated method stub
		return this.userRoleRepository.findAll();
	}

	@Override
	public UserRole save(UserRole newUserRole) {
		// B1: Kiểm tra user và role có tồn tại không
		Integer userId = newUserRole.getUser().getId();
		Integer roleId = newUserRole.getRole().getId();

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

		Role role = roleRepository.findById(roleId)
				.orElseThrow(() -> new NotFoundException("Role not found with id: " + roleId));

		// B2: Thiết lập lại user và role chuẩn (từ DB)
		newUserRole.setUser(user);
		newUserRole.setRole(role);
		newUserRole.setAssignedAt(LocalDateTime.now());

		// B3: Lưu UserRole
		return userRoleRepository.save(newUserRole);
	}

}
