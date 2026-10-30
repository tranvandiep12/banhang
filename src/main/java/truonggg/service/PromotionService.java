package truonggg.service;

import java.util.List;

import truonggg.DTO.request.Promotion.PromotionRequestDTO;
import truonggg.DTO.request.Promotion.PromotionUpdateDTO;
import truonggg.DTO.response.PromotionReponseDTO;

public interface PromotionService {
	PromotionReponseDTO save(PromotionRequestDTO dto);

	PromotionReponseDTO update(Integer id, PromotionUpdateDTO dto);

	boolean delete(Integer id);

	PromotionReponseDTO getById(Integer id);

	List<PromotionReponseDTO> getAll();

	void deleteById(Integer id);

}
