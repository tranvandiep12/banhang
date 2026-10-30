package truonggg.service;

import java.util.List;

import truonggg.DTO.request.VariantProductRequestDTO;
import truonggg.DTO.request.VariantProductUpdateDTO;
import truonggg.DTO.response.VariantProductResponseDTO;

public interface VariantProductService {
	VariantProductResponseDTO create(VariantProductRequestDTO dto);

	VariantProductResponseDTO update(Integer id, VariantProductUpdateDTO dto);

	boolean delete(Integer id); // soft delete nếu cần

	VariantProductResponseDTO getById(Integer id);

	List<VariantProductResponseDTO> getAll();
}
