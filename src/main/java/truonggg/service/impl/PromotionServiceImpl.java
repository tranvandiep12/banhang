package truonggg.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.Promotion.PromotionRequestDTO;
import truonggg.DTO.request.Promotion.PromotionUpdateDTO;
import truonggg.DTO.response.PromotionReponseDTO;
import truonggg.exception.NotFoundException;
import truonggg.mapper.productmodule.PromotionMapper;
import truonggg.model.Promotion;
import truonggg.repository.PromotionRepository;
import truonggg.service.PromotionService;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
	private final PromotionRepository promotionRepository;
	private final PromotionMapper promotionMapper;

	@Override
	public PromotionReponseDTO save(PromotionRequestDTO dto) {
		Promotion promotion = promotionMapper.toEntity(dto);
		promotion.setIsActive(true);
		Promotion saved = promotionRepository.save(promotion);
		return promotionMapper.toDTO(saved);
	}

	@Override
	public boolean delete(Integer id) {
		Promotion promotion = promotionRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Can not found Promotion ID: " + id));

		if (Boolean.FALSE.equals(promotion.getIsActive())) {
			return false; // đã bị vô hiệu hóa trước đó
		}

		promotion.setIsActive(false); // xóa mềm
		promotionRepository.save(promotion);
		return true;
	}

	@Override
	public PromotionReponseDTO getById(Integer id) {
		Promotion promotion = promotionRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Can not found Promotion ID: " + id));
		return promotionMapper.toDTO(promotion);
	}

	@Override
	public List<PromotionReponseDTO> getAll() {
		return promotionRepository.findAll().stream().filter(p -> Boolean.TRUE.equals(p.getIsActive()))
				.map(promotionMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public PromotionReponseDTO update(Integer id, PromotionUpdateDTO dto) {
		Promotion existing = promotionRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Can not found Promotion ID: " + id));

		promotionMapper.updateEntityFromRequest(dto, existing);
		Promotion updated = promotionRepository.save(existing);
		return promotionMapper.toDTO(updated);
	}

	@Override
	public void deleteById(Integer id) {
		if (!promotionRepository.existsById(id)) {
			throw new NotFoundException("Không tìm thấy khuyến mãi có ID = " + id);
		}
		promotionRepository.deleteById(id);
	}

}
