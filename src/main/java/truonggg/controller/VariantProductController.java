package truonggg.controller;

import java.util.List;

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
import truonggg.DTO.request.VariantProductRequestDTO;
import truonggg.DTO.request.VariantProductUpdateDTO;
import truonggg.DTO.response.VariantProductResponseDTO;
import truonggg.response.SuccessReponse;
import truonggg.service.VariantProductService;

@RestController
@RequestMapping("/variant-products")
@RequiredArgsConstructor
public class VariantProductController {

	private final VariantProductService variantProductService;

	@PostMapping
	public SuccessReponse<VariantProductResponseDTO> create(@RequestBody @Valid VariantProductRequestDTO dto) {
		return SuccessReponse.of(variantProductService.create(dto));
	}

	@PutMapping("/{id}")
	public SuccessReponse<VariantProductResponseDTO> update(@PathVariable Integer id,
			@RequestBody @Valid VariantProductUpdateDTO dto) {
		return SuccessReponse.of(variantProductService.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public SuccessReponse<String> delete(@PathVariable Integer id) {
		variantProductService.delete(id);
		return SuccessReponse.of("Xóa variant product thành công");
	}

	@GetMapping("/{id}")
	public SuccessReponse<VariantProductResponseDTO> getById(@PathVariable Integer id) {
		return SuccessReponse.of(variantProductService.getById(id));
	}

	@GetMapping
	public SuccessReponse<List<VariantProductResponseDTO>> getAll() {
		return SuccessReponse.of(variantProductService.getAll());
	}
}
