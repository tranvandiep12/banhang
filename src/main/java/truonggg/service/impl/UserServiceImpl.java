package truonggg.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.UserRequestDTO;
import truonggg.DTO.request.UserUpdateDTO;
import truonggg.DTO.response.UserResponseDTO;
import truonggg.constant.SecurityRole;
import truonggg.exception.NotFoundException;
import truonggg.exception.UserAlreadyExistException;
import truonggg.mapper.user.UserMapper;
import truonggg.model.Role;
import truonggg.model.User;
import truonggg.model.UserRole;
import truonggg.repository.RoleRepository;
import truonggg.repository.UserRepository;
import truonggg.repository.UserRoleRepository;
import truonggg.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final UserRoleRepository userRoleRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public List<UserResponseDTO> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserResponseDTO> dtos = new ArrayList<>();

		for (User user : users) {
			Integer roleId = userRoleRepository.findFirstByUserId(user.getId())
					.map(userRole -> userRole.getRole().getId()).orElse(null);

			user.setRoleId(roleId); // gán thủ công để mapper có thể lấy được

			UserResponseDTO dto = userMapper.toDTO(user);
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public Optional<User> getById(Integer id) {
		return this.userRepository.findById(id);
	}

	@Override
	public UserResponseDTO saveUser(UserRequestDTO dto) {
		// B1: Kiểm tra roleId có null không
		if (dto.getRoleId() == null) {
			throw new IllegalArgumentException("RoleId is required!");
		}

		// B2: Tìm Role theo roleId
		Role role = roleRepository.findById(dto.getRoleId()).orElseThrow(
				() -> new truonggg.exception.NotFoundException("Role not found with id: " + dto.getRoleId()));

		// B3: Chuyển DTO thành Entity
		User user = userMapper.toModel(dto);
		user.setCreatedAt(LocalDateTime.now());

		// B4: Lưu User
		User savedUser = userRepository.save(user);

		// B5: Tạo UserRole mới để gán role
		UserRole userRole = new UserRole();
		userRole.setUser(savedUser);
		userRole.setRole(role);
		userRole.setAssignedAt(LocalDateTime.now());

		// B6: Lưu UserRole
		userRoleRepository.save(userRole);

		// Optional: Gán ngược lại vào User nếu muốn phản hồi gồm Role
		savedUser.getList().add(userRole);

		// B7: Trả về
		return userMapper.toDTO(savedUser);
	}

	@Override
	public UserResponseDTO updateUser(Integer id, UserUpdateDTO dto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found with id: " + id));

		if (dto.getFullName() != null)
			user.setFullName(dto.getFullName());
		if (dto.getEmail() != null)
			user.setEmail(dto.getEmail());
		if (dto.getPhone() != null)
			user.setPhone(dto.getPhone());
		if (dto.getAddress() != null)
			user.setAddress(dto.getAddress());
		if (dto.getCccd() != null)
			user.setCccd(dto.getCccd());
		if (dto.getDob() != null)
			user.setDob(dto.getDob());
		if (dto.getGender() != null)
			user.setGender(dto.getGender());
		if (dto.getAvatarUrl() != null)
			user.setAvatarUrl(dto.getAvatarUrl());
		if (dto.getIsActive() != null)
			user.setIsActive(dto.getIsActive());

		// Cập nhật Role nếu có
		if (dto.getRoleId() != null) {
			Role role = roleRepository.findById(dto.getRoleId())
					.orElseThrow(() -> new NotFoundException("Role not found"));

			userRoleRepository.deleteAllByUserId(user.getId());

			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			userRole.setAssignedAt(LocalDateTime.now());
			userRoleRepository.save(userRole);
		}

		user.setUpdatedAt(LocalDateTime.now());

		// Gán lại đúng tên biến
		User savedUser = userRepository.save(user);

		// Lấy roleId và gán thủ công vì field này là @Transient
		Integer roleId = userRoleRepository.findFirstByUserId(savedUser.getId())
				.map(userRole -> userRole.getRole().getId()).orElse(null);
		savedUser.setRoleId(roleId);

		return userMapper.toDTO(savedUser);
	}

	@Override
	public boolean deleteUser(Integer id) {
		// B1: Tìm user theo id
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Can not found User by id: " + id));

		// B2: Kiểm tra nếu đã bị vô hiệu hóa rồi thì return false
		if (Boolean.FALSE.equals(user.getIsActive())) {
			return false; // Đã bị xóa mềm trước đó
		}

		// B3: Xóa mềm bằng cách cập nhật isActive = false
		user.setIsActive(false);
		user.setUpdatedAt(LocalDateTime.now());

		// B4: Lưu lại
		userRepository.save(user);

		return true; // Xóa mềm thành công
	}

	@Override
	public void deleteUserPermanently(Integer id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng có ID: " + id));
		userRepository.delete(user);
	}

	@Override
	public Boolean signUp(User user) {
		this.userRepository.findByUsername(user.getUsername()).ifPresent(u -> {
			throw new UserAlreadyExistException("User with username: %s already existed!".formatted(u.getUsername()));
		});

		user.setIsActive(true);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setCreatedAt(LocalDateTime.now());

		// 🔽 Gán mặc định role là CUSTOMER
		Role defaultRole = roleRepository.findFirstByName(SecurityRole.ROLE_CUSTOMER)
				.orElseThrow(() -> new RuntimeException("Default role not found"));

		UserRole userRole = UserRole.builder().user(user).role(defaultRole).assignedAt(LocalDateTime.now())
				.grantedBy(null).build();

		user.getList().add(userRole);
		userRepository.save(user);
		return true;
	}

	@Override
	public UserResponseDTO getUserById(Integer id) {
		User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng"));
		return userMapper.toDTO(user);
	}
}
