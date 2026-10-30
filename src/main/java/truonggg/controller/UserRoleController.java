package truonggg.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.UserRoleRequestDTO;
import truonggg.DTO.response.UserRoleReponseDTO;
import truonggg.mapper.user.UserRoleMapper;
import truonggg.model.UserRole;
import truonggg.response.SuccessReponse;
import truonggg.service.UserRoleService;

@RestController
@RequestMapping("/user-roles")
@RequiredArgsConstructor
public class UserRoleController {

	private final UserRoleService userRoleService;
	private final UserRoleMapper userRoleMapper;

	@GetMapping
	public SuccessReponse<List<UserRoleReponseDTO>> getAllUserRoles() {
		List<UserRole> roles = userRoleService.getAll();
		List<UserRoleReponseDTO> response = roles.stream().map(userRoleMapper::toDTO).collect(Collectors.toList());
		return SuccessReponse.of(response);
	}

	@PostMapping(path = "")
	public SuccessReponse<UserRoleReponseDTO> save(@RequestBody @Valid UserRoleRequestDTO dto) {
		UserRole newUserRole = userRoleMapper.toModel(dto);
		newUserRole = this.userRoleService.save(newUserRole);
		return SuccessReponse.of(userRoleMapper.toDTO(newUserRole));
	}
}
