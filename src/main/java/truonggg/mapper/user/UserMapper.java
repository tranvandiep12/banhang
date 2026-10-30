package truonggg.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import truonggg.DTO.request.SignUpRequest;
import truonggg.DTO.request.UserRequestDTO;
import truonggg.DTO.request.UserUpdateDTO;
import truonggg.DTO.response.UserResponseDTO;
import truonggg.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

//	@Mapping(target = "roleId", expression = "java(user.getList().isEmpty() ? null : user.getList().get(0).getRole().getId())")
//	UserResponseDTO toDTO(User user);

	@Mapping(source = "roleId", target = "roleId")
	User toModel(UserRequestDTO dto);

	@Mapping(source = "roleId", target = "roleId")
	User toModel(UserUpdateDTO dto);

	@Mapping(target = "isActive", constant = "true")
	@Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "list", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	User toModel(SignUpRequest dto);

	@Mapping(target = "roleId", expression = "java((user.getList() != null && !user.getList().isEmpty()) ? user.getList().get(0).getRole().getId() : null)")
	@Mapping(target = "roleName", expression = "java((user.getList() != null && !user.getList().isEmpty()) ? user.getList().get(0).getRole().getName() : null)")
	UserResponseDTO toDTO(User user);

}
