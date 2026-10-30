package truonggg.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.RoleRequestDTO;
import truonggg.DTO.response.RoleResponseDTO;
import truonggg.mapper.user.RoleMapper;
import truonggg.model.Role;
import truonggg.response.SuccessReponse;
import truonggg.service.RoleService;

@RestController
@RequestMapping(path = "/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class RoleController {
	private final RoleService roleService;

	@GetMapping(path = "")
	public SuccessReponse<List<RoleResponseDTO>> displayAll() {
		return SuccessReponse.of(this.roleService.getAllRole().stream().map(RoleMapper.INSTANCE::toDTO).toList());
	}

	@PostMapping(path = "")
	public SuccessReponse<RoleResponseDTO> save(@RequestBody @Valid RoleRequestDTO dto) {
		// b1:convert to user
		Role newRole = RoleMapper.INSTANCE.toModel(dto);
		// b2:save
		newRole = this.roleService.save(newRole);
		// b3:
		return SuccessReponse.of(RoleMapper.INSTANCE.toDTO(newRole));
	}
}
