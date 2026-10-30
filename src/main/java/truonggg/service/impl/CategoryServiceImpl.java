package truonggg.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.CategoryRequestDTO;
import truonggg.DTO.request.CategoryUpdateDTO;
import truonggg.DTO.response.CategoryResponseDTO;
import truonggg.exception.NotFoundException;
import truonggg.mapper.productmodule.CategoryMapper;
import truonggg.model.Category;
import truonggg.repository.CategoryRepository;
import truonggg.service.CategoryService;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	private final CategoryMapper categoryMapper;

	@Override
	public CategoryResponseDTO save(CategoryRequestDTO dto) {
		Category category = categoryMapper.toModel(dto);

		// Gán rõ ràng giá trị true
		category.setDeleted(true);

		Category saved = categoryRepository.save(category);
		return categoryMapper.toDTO(saved);
	}

	@Override
	public CategoryResponseDTO update(Integer id, CategoryUpdateDTO dto) {
		Category existing = categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

		categoryMapper.updateEntityFromRequest(dto, existing);

		// Nếu DTO có trường isDeleted thì cập nhật nó
		if (dto.getIsDeleted() != null) {
			existing.setDeleted(dto.getIsDeleted());
		}

		Category updated = categoryRepository.save(existing);
		return categoryMapper.toDTO(updated);
	}

	@Override
	public boolean deleteCategory(Integer id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Category not found with id: " + id));

		// Nếu đã bị xóa trước đó (isDeleted = false) thì không làm lại nữa
		if (!category.isDeleted()) {
			return false;
		}

		// Đánh dấu là đã xóa (chuyển thành false)
		category.setDeleted(false);
		categoryRepository.save(category);
		return true;
	}

	@Override
	public CategoryResponseDTO getById(Integer id) {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
		return categoryMapper.toDTO(category);
	}

	@Override
	public List<CategoryResponseDTO> getAll() {
		List<Category> categories = categoryRepository.findAll();
		List<CategoryResponseDTO> dtos = new ArrayList<>();

		for (Category category : categories) {
			int totalProducts = category.getList() != null ? category.getList().size() : 0;

			// Thủ công set giá trị phụ thuộc nếu mapper không tự ánh xạ
			category.setList(category.getList()); // hoặc category.setTotalProducts(totalProducts) nếu bạn có field
													// riêng

			CategoryResponseDTO dto = categoryMapper.toDTO(category);

			// Nếu mapper không tự ánh xạ, bạn có thể set sau:
			// dto.setTotalProducts(totalProducts);

			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public boolean deleteCategoryPermanently(Integer id) {
		Optional<Category> optional = categoryRepository.findById(id);
		if (!optional.isPresent()) {
			throw new NotFoundException("Không tìm thấy danh mục với id = " + id);
		}

		categoryRepository.deleteById(id);
		return true;
	}

	@Override
	public void updateStatus(Integer id, Boolean isDeleted) throws BadRequestException {
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục với id = " + id));

		if (Boolean.valueOf(category.isDeleted()).equals(isDeleted)) {
			throw new BadRequestException("Danh mục đã ở trạng thái yêu cầu");
		}
		category.setDeleted(isDeleted);
		categoryRepository.save(category);

		System.out.println("Đã cập nhật trạng thái danh mục " + id + " thành " + isDeleted);
	}

}
