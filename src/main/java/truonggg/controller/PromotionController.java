package truonggg.controller;

import java.util.List;

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
import truonggg.DTO.request.Promotion.PromotionRequestDTO;
import truonggg.DTO.request.Promotion.PromotionUpdateDTO;
import truonggg.DTO.response.PromotionReponseDTO;
import truonggg.response.SuccessReponse;
import truonggg.service.PromotionService;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/promotions")
@RequiredArgsConstructor
public class PromotionController {

	private final PromotionService promotionService;

	@GetMapping("")
	public SuccessReponse<List<PromotionReponseDTO>> getAllPromotions() {
		return SuccessReponse.of(promotionService.getAll());
	}

	@GetMapping("/{id}")
	public SuccessReponse<PromotionReponseDTO> getPromotionById(@PathVariable Integer id) {
		return SuccessReponse.of(promotionService.getById(id));
	}

	@PostMapping("")
	public SuccessReponse<PromotionReponseDTO> createPromotion(@RequestBody @Valid PromotionRequestDTO dto) {
		return SuccessReponse.of(promotionService.save(dto));
	}

	@PutMapping("/{id}")
	public SuccessReponse<PromotionReponseDTO> updatePromotion(@PathVariable Integer id,
			@RequestBody @Valid PromotionUpdateDTO dto) {
		return SuccessReponse.of(promotionService.update(id, dto));
	}

	@DeleteMapping("/{id}")
	public SuccessReponse<String> deletePromotion(@PathVariable Integer id) {
		boolean result = promotionService.delete(id);
		if (!result) {
			return SuccessReponse.of("Khuyến mãi đã bị vô hiệu hóa trước đó.");
		}
		return SuccessReponse.of("Đã xóa khuyến mãi thành công.");
	}

	@DeleteMapping("/promotions/{id}")
	public SuccessReponse<String> deletePromotion1(@PathVariable Integer id) {
		promotionService.deleteById(id);
		return SuccessReponse.of("Xóa khuyến mãi thành công!");
	}

}
