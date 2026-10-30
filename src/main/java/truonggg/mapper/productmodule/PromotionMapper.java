package truonggg.mapper.productmodule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import truonggg.DTO.request.Promotion.PromotionRequestDTO;
import truonggg.DTO.request.Promotion.PromotionUpdateDTO;
import truonggg.DTO.response.PromotionReponseDTO;
import truonggg.model.Promotion;

@Mapper(componentModel = "spring", uses = {
		ProductMapper.class }, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE // ← Quan
																											// trọng!
)
public interface PromotionMapper {

	Promotion toEntity(PromotionRequestDTO dto);

	@Mapping(target = "products", source = "products")
	PromotionReponseDTO toDTO(Promotion promotion);

	void updateEntityFromRequest(PromotionUpdateDTO dto, @MappingTarget Promotion entity);
}