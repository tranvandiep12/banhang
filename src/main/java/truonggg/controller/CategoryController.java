package truonggg.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.CategoryRequestDTO;
import truonggg.DTO.request.CategoryStatusDTO;
import truonggg.DTO.request.CategoryUpdateDTO;
import truonggg.DTO.response.CategoryResponseDTO;
import truonggg.response.SuccessReponse;
import truonggg.service.CategoryService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping(path = "")
	public SuccessReponse<List<CategoryResponseDTO>> getAll() {
		return SuccessReponse.of(categoryService.getAll());
	}

	@GetMapping(path = "/{id}")
	public SuccessReponse<CategoryResponseDTO> getById(@PathVariable Integer id) {
		return SuccessReponse.of(categoryService.getById(id));
	}

	@PostMapping(path = "")
	public SuccessReponse<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO request) {
		CategoryResponseDTO dto = categoryService.save(request);
		return SuccessReponse.of(dto);
	}

	@PutMapping(path = "/{id}")
	public SuccessReponse<CategoryResponseDTO> update(@PathVariable Integer id,
			@Valid @RequestBody CategoryUpdateDTO dto) {
		return SuccessReponse.of(categoryService.update(id, dto));
	}

	@DeleteMapping(path = "/{id}")
	public SuccessReponse<String> delete(@PathVariable Integer id) {
		categoryService.deleteCategory(id);
		return SuccessReponse.of("Đã xóa danh mục thành công.");
	}

	@DeleteMapping("/permanent/{id}")
	public SuccessReponse<String> deleteCategoryPermanently(@PathVariable Integer id) {
		boolean deleted = categoryService.deleteCategoryPermanently(id);
		if (deleted) {
			return SuccessReponse.of("Xóa vĩnh viễn danh mục thành công!");
		} else {
			return SuccessReponse.of("Không tìm thấy danh mục để xóa.");
		}
	}

	@PutMapping("/status/{id}")
	public SuccessReponse<String> updateCategoryStatus(@PathVariable Integer id, @RequestBody CategoryStatusDTO dto)
			throws BadRequestException {
		categoryService.updateStatus(id, dto.getIsDeleted());
		return SuccessReponse.of("Cập nhật trạng thái danh mục thành công");
	}

}
