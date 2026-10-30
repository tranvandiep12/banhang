package truonggg.mapper.productmodule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import truonggg.DTO.request.VariantProductRequestDTO;
import truonggg.DTO.request.VariantProductUpdateDTO;
import truonggg.DTO.response.VariantProductResponseDTO;
import truonggg.model.VariantProduct;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface VariantProductMapper {

	@Mapping(source = "product.id", target = "productId")
	VariantProductResponseDTO toDTO(VariantProduct variant);

	@Mapping(source = "productId", target = "product.id")
	VariantProduct toEntity(VariantProductRequestDTO dto);

	void updateEntityFromRequest(VariantProductUpdateDTO dto, @MappingTarget VariantProduct variant);
}
