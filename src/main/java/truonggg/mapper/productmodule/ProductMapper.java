package truonggg.mapper.productmodule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import truonggg.DTO.request.ProductRequestDTO;
import truonggg.DTO.request.ProductUpdateDTO;
import truonggg.DTO.response.ProductResponseDTO;
import truonggg.model.Product;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

	@Mapping(source = "category.id", target = "categoryId")
	@Mapping(source = "promotion.id", target = "promotionId")
	@Mapping(source = "promotion.discountPercent", target = "discountPercent") // 👉 Thêm dòng này
	ProductResponseDTO toDTO(Product product);

	@Mapping(source = "categoryId", target = "category.id")
	@Mapping(source = "promotionId", target = "promotion.id")
	Product toModel(ProductRequestDTO dto);

	// @Mapping(source = "categoryId", target = "category.id")
	// @Mapping(source = "promotionId", target = "promotion.id")
	@Mapping(target = "category", ignore = true)
	@Mapping(target = "promotion", ignore = true)
	void updateEntityFromRequest(ProductUpdateDTO dto, @MappingTarget Product product);
}
