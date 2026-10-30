package truonggg.service;

import java.util.List;

import org.apache.coyote.BadRequestException;

import truonggg.DTO.request.CategoryRequestDTO;
import truonggg.DTO.request.CategoryUpdateDTO;
import truonggg.DTO.response.CategoryResponseDTO;

public interface CategoryService {
	CategoryResponseDTO save(CategoryRequestDTO categoryRequestDTO);

	CategoryResponseDTO update(Integer id, CategoryUpdateDTO dto);

	boolean deleteCategory(Integer id);

	boolean deleteCategoryPermanently(Integer id);

	CategoryResponseDTO getById(Integer id);

	List<CategoryResponseDTO> getAll();

	void updateStatus(Integer id, Boolean isActive) throws BadRequestException;
}
