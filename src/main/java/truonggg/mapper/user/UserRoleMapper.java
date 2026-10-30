package truonggg.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import truonggg.DTO.request.UserRoleRequestDTO;
import truonggg.DTO.response.UserRoleReponseDTO;
import truonggg.model.UserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.username", target = "username")
	@Mapping(source = "role.id", target = "roleId")
	@Mapping(source = "role.name", target = "roleName")
	UserRoleReponseDTO toDTO(UserRole userRole);

	@Mapping(source = "userId", target = "user.id")
	@Mapping(source = "roleId", target = "role.id")
	UserRole toModel(UserRoleRequestDTO dto);
}