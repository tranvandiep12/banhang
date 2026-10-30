package truonggg.controller;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.ProductRequestDTO;
import truonggg.DTO.request.ProductStatusDTO;
import truonggg.DTO.request.ProductUpdateDTO;
import truonggg.DTO.response.ProductResponseDTO;
import truonggg.response.ErrorCode;
import truonggg.response.SuccessReponse;
import truonggg.service.ProductService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	// API cho người dùng (public)
	@GetMapping("/public/category/{categoryId}")
	public SuccessReponse<List<ProductResponseDTO>> getActiveProductsByCategory(@PathVariable Integer categoryId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponseDTO> result = productService.getActiveProductsByCategory(categoryId, pageable);

		return SuccessReponse.<List<ProductResponseDTO>>builder().data(result.getContent()).page(result.getNumber())
				.size((int) result.getTotalElements()).code(ErrorCode.OK).build();
	}

	// API cho admin
	@GetMapping("/admin/category/{categoryId}")
	public SuccessReponse<List<ProductResponseDTO>> getAllProductsByCategoryAdmin(@PathVariable Integer categoryId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponseDTO> resultPage = productService.getAllProductsByCategory(categoryId, pageable);
		return SuccessReponse.of(resultPage.getContent(), resultPage.getNumber());
	}

	@GetMapping("/{id}")
	public SuccessReponse<ProductResponseDTO> getProductDetail(@PathVariable Integer id) {
		ProductResponseDTO dto = productService.getProductDetail(id);
		return SuccessReponse.of(dto);
	}

	// Thêm sản phẩm mới
//	@PostMapping("")
//	public SuccessReponse<ProductResponseDTO> save(@Valid @RequestBody ProductRequestDTO dto) {
//		ProductResponseDTO saved = productService.saveProduct(dto); // ID có thể null nếu chưa dùng
//		return SuccessReponse.of(saved);
//	}
	@PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public SuccessReponse<ProductResponseDTO> save(@Valid @ModelAttribute ProductRequestDTO dto) {
		ProductResponseDTO saved = productService.saveProductWithImage(dto);
		return SuccessReponse.of(saved);
	}

	@PutMapping("/{id}")
	public SuccessReponse<ProductResponseDTO> update(@PathVariable Integer id,
			@Valid @ModelAttribute ProductUpdateDTO request) {

		ProductResponseDTO dto = productService.updateProduct(id, request);
		return SuccessReponse.of(dto);
	}

	@DeleteMapping("/{id}")
	public SuccessReponse<Boolean> softDelete(@PathVariable Integer id) {
		boolean result = productService.softDeleteProduct(id);
		return SuccessReponse.of(result);
	}

	@DeleteMapping("/permanent/{id}")
	public SuccessReponse<String> deleteProductPermanently(@PathVariable Integer id) {
		boolean deleted = productService.deleteProductPermanently(id);
		if (deleted) {
			return SuccessReponse.of("Xóa vĩnh viễn sản phẩm thành công");
		} else {
			return SuccessReponse.of("Xóa thất bại, vui lòng thử lại");
		}
	}

	@PutMapping("/status/{id}")
	public SuccessReponse<String> updateProductStatus(@PathVariable Integer id, @RequestBody ProductStatusDTO dto)
			throws BadRequestException {
		productService.updateStatus(id, dto.getIsActive());
		return SuccessReponse.of("Cập nhật trạng thái thành công");
	}

	@GetMapping("/search")
	public SuccessReponse<List<ProductResponseDTO>> searchProducts(@RequestParam String keyword,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

		Pageable pageable = PageRequest.of(page, size);
		Page<ProductResponseDTO> resultPage = productService.searchProducts(keyword, pageable);
		return SuccessReponse.of(resultPage.getContent(), resultPage.getNumber());
	}

}
