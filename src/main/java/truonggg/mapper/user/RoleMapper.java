package truonggg.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import truonggg.DTO.request.RoleRequestDTO;
import truonggg.DTO.response.RoleResponseDTO;
import truonggg.model.Role;

@Mapper
public interface RoleMapper {
	RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

	Role toModel(RoleRequestDTO dto);

	RoleResponseDTO toDTO(Role role);
}
