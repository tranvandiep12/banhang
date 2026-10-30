package truonggg.service;

import java.util.List;
import java.util.Optional;

import truonggg.DTO.request.UserRequestDTO;
import truonggg.DTO.request.UserUpdateDTO;
import truonggg.DTO.response.UserResponseDTO;
import truonggg.model.User;

public interface UserService {
	List<UserResponseDTO> getAllUsers();

	Optional<User> getById(Integer id);

	UserResponseDTO saveUser(UserRequestDTO dto);

	UserResponseDTO updateUser(Integer id, UserUpdateDTO dto);

	boolean deleteUser(Integer id);

	void deleteUserPermanently(Integer id);

	Boolean signUp(final User user);

	UserResponseDTO getUserById(Integer userId);
}
