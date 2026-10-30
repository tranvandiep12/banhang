package truonggg.mapper.productmodule;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import truonggg.DTO.request.CategoryRequestDTO;
import truonggg.DTO.request.CategoryUpdateDTO;
import truonggg.DTO.response.CategoryResponseDTO;
import truonggg.model.Category;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = ProductMapper.class)
public interface CategoryMapper {
	// @Mapping(source = "list", target = "products")
	CategoryResponseDTO toDTO(Category category);

	Category toModel(CategoryRequestDTO dto);

	void updateEntityFromRequest(CategoryUpdateDTO dto, @MappingTarget Category existing);
}
