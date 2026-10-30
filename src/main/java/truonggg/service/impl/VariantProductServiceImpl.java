package truonggg.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.VariantProductRequestDTO;
import truonggg.DTO.request.VariantProductUpdateDTO;
import truonggg.DTO.response.VariantProductResponseDTO;
import truonggg.exception.NotFoundException;
import truonggg.mapper.productmodule.VariantProductMapper;
import truonggg.model.Product;
import truonggg.model.VariantProduct;
import truonggg.repository.ProductRepository;
import truonggg.repository.VariantProductRepository;
import truonggg.service.VariantProductService;

@Service
@RequiredArgsConstructor
public class VariantProductServiceImpl implements VariantProductService {

	private final VariantProductRepository variantProductRepository;
	private final VariantProductMapper variantProductMapper;
	private final ProductRepository productRepository;

	@Override
	public VariantProductResponseDTO create(VariantProductRequestDTO dto) {
		// 1. Kiểm tra và lấy Product (ràng buộc khóa ngoại)
		Product product = productRepository.findById(dto.getProductId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với ID: " + dto.getProductId()));

		// 2. Map DTO -> Entity
		VariantProduct variant = variantProductMapper.toEntity(dto);

		// 3. Gán Product vào VariantProduct
		variant.setProduct(product);

		// 4. Lưu variant
		VariantProduct saved = variantProductRepository.save(variant);

		// 5. Trả về DTO
		return variantProductMapper.toDTO(saved);
	}

	@Override
	public VariantProductResponseDTO update(Integer id, VariantProductUpdateDTO dto) {
		VariantProduct existing = variantProductRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("VariantProduct not found with id: " + id));
		variantProductMapper.updateEntityFromRequest(dto, existing);
		VariantProduct updated = variantProductRepository.save(existing);
		return variantProductMapper.toDTO(updated);
	}

	@Override
	public boolean delete(Integer id) {
		VariantProduct variant = variantProductRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("VariantProduct not found with id: " + id));
		variantProductRepository.deleteById(id);
		return true;
	}

	@Override
	public VariantProductResponseDTO getById(Integer id) {
		VariantProduct variant = variantProductRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("VariantProduct not found with id: " + id));
		return variantProductMapper.toDTO(variant);
	}

	@Override
	public List<VariantProductResponseDTO> getAll() {
		return variantProductRepository.findAll().stream().map(variantProductMapper::toDTO)
				.collect(Collectors.toList());
	}
}