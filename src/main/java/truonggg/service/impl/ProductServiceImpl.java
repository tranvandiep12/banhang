package truonggg.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import truonggg.DTO.request.ProductRequestDTO;
import truonggg.DTO.request.ProductUpdateDTO;
import truonggg.DTO.response.ProductResponseDTO;
import truonggg.exception.NotFoundException;
import truonggg.mapper.productmodule.ProductMapper;
import truonggg.model.Category;
import truonggg.model.Product;
import truonggg.model.Promotion;
import truonggg.repository.CategoryRepository;
import truonggg.repository.ProductRepository;
import truonggg.repository.PromotionRepository;
import truonggg.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;

	private final ProductMapper productMapper;
	private final CategoryRepository categoryRepository;
	private final PromotionRepository promotionRepository;

//	@Override
//	public List<ProductResponseDTO> getActiveProductsByCategory(Integer categoryId) {
//		Category category = categoryRepository.findById(categoryId)
//				.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục ID: " + categoryId));
//
//		List<Product> products = productRepository.findByCategoryAndIsActiveTrue(category);
//		return products.stream().map(productMapper::toDTO).collect(Collectors.toList());
//	}
	@Override
	public List<ProductResponseDTO> getActiveProductsByCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục ID: " + categoryId));

		List<Product> products = productRepository.findByCategoryAndIsActiveTrue(category);

		return products.stream().map(product -> {
			ProductResponseDTO dto = productMapper.toDTO(product);

			// Gắn giảm giá nếu có khuyến mãi và đang active
			if (product.getPromotion() != null && Boolean.TRUE.equals(product.getPromotion().getIsActive())) {
				dto.setDiscountPercent(product.getPromotion().getDiscountPercent());

				// Optional: Nếu bạn muốn tính giá đã giảm
				dto.setPrice(product.getPrice() * (1 - dto.getDiscountPercent() / 100));
			}

			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public List<ProductResponseDTO> getAllProductsByCategory(Integer categoryId) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục ID: " + categoryId));

		List<Product> products = productRepository.findByCategory(category);
		return products.stream().map(productMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public ProductResponseDTO saveProductWithImage(ProductRequestDTO dto) {
		String imageUrl = null;
		if (dto.getImage() != null && !dto.getImage().isEmpty()) {
			try {
				String fileName = UUID.randomUUID().toString() + "_" + dto.getImage().getOriginalFilename();
				Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "images", "products");

				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				Path filePath = uploadPath.resolve(fileName);
				dto.getImage().transferTo(filePath.toFile());

				imageUrl = "/images/products/" + fileName; // 👉 dùng trong web
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Lỗi khi upload ảnh", e);
			}
		}

		// Tìm category
		Category category = categoryRepository.findById(dto.getCategoryId())
				.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục"));

		// Tìm promotion nếu có
		Promotion promotion = null;
		if (dto.getPromotionId() != null) {
			promotion = promotionRepository.findById(dto.getPromotionId())
					.orElseThrow(() -> new NotFoundException("Không tìm thấy khuyến mãi"));
		}

		// Tạo entity
		Product product = new Product();
		product.setName(dto.getName());
		product.setDescription(dto.getDescription());
		product.setPrice(dto.getPrice());
		product.setQuantity(dto.getQuantity());
		product.setImageUrl(imageUrl); // ✅ lưu imageUrl
		product.setCategory(category);
		product.setPromotion(promotion);
		product.setCreatedAt(LocalDateTime.now());
		product.setUpdatedAt(LocalDateTime.now());

		// Lưu và trả về DTO
		Product saved = productRepository.save(product);
		System.out.println("promotionId được gửi lên: " + dto.getPromotionId());

		return productMapper.toDTO(saved);
	}

	@Override
	public ProductResponseDTO updateProduct(Integer id, ProductUpdateDTO dto) {
		System.out.println("Promotion ID từ DTO: " + dto.getPromotionId());
		Product existingProduct = productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với ID: " + id));

		// Nếu có categoryId mới thì set lại category
		if (dto.getCategoryId() != null) {
			Category category = categoryRepository.findById(dto.getCategoryId())
					.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục với ID: " + dto.getCategoryId()));
			existingProduct.setCategory(category);
		}
		productMapper.updateEntityFromRequest(dto, existingProduct);
		// Nếu có promotionId thì set lại promotion
		if (dto.getPromotionId() != null) {
			Promotion promotion = promotionRepository.findById(dto.getPromotionId()).orElseThrow(
					() -> new NotFoundException("Không tìm thấy khuyến mãi với ID: " + dto.getPromotionId()));
			existingProduct.setPromotion(promotion);
		} else {
			existingProduct.setPromotion(null);
		}

		// ✅ Nếu người dùng có cập nhật ảnh mới
		if (dto.getImage() != null && !dto.getImage().isEmpty()) {
			String imageName = UUID.randomUUID().toString() + "_" + dto.getImage().getOriginalFilename();

			// Dùng đường dẫn tuyệt đối
			Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "images", "products");

			try {
				// Tạo thư mục nếu chưa tồn tại
				if (!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}

				// Lưu file ảnh vào thư mục
				Path filePath = uploadPath.resolve(imageName);
				dto.getImage().transferTo(filePath.toFile());

				// Gán đường dẫn tương đối cho frontend
				existingProduct.setImageUrl("/images/products/" + imageName);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("Lỗi khi lưu hình ảnh", e);
			}
		}

		// ✅ Dùng mapper cập nhật các field khác (nếu không null)
		productMapper.updateEntityFromRequest(dto, existingProduct);
		existingProduct.setUpdatedAt(LocalDateTime.now());
		Product updated = productRepository.save(existingProduct);
		return productMapper.toDTO(updated);
	}
//	@Override
//	public ProductResponseDTO updateProduct(Integer id, ProductUpdateDTO dto) {
//		Product existingProduct = productRepository.findById(id)
//				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với ID: " + id));
//
//		// ✅ Gọi mapper trước, KHÔNG ánh xạ promotionId/categoryId trong mapper
//		productMapper.updateEntityFromRequest(dto, existingProduct);
//
//		// ✅ Xử lý categoryId riêng
//		if (dto.getCategoryId() != null) {
//			Category category = categoryRepository.findById(dto.getCategoryId())
//					.orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục với ID: " + dto.getCategoryId()));
//			existingProduct.setCategory(category);
//		}
//
//		// ✅ Xử lý promotionId riêng
//		if (dto.getPromotionId() != null) {
//			Promotion promotion = promotionRepository.findById(dto.getPromotionId()).orElseThrow(
//					() -> new NotFoundException("Không tìm thấy khuyến mãi với ID: " + dto.getPromotionId()));
//			existingProduct.setPromotion(promotion);
//		} else {
//			existingProduct.setPromotion(null); // Không gửi thì xóa khuyến mãi
//		}
//
//		// ✅ Nếu có ảnh mới thì lưu lại
//		if (dto.getImage() != null && !dto.getImage().isEmpty()) {
//			String imageName = UUID.randomUUID().toString() + "_" + dto.getImage().getOriginalFilename();
//			Path uploadPath = Paths.get(System.getProperty("user.dir"), "uploads", "images", "products");
//
//			try {
//				if (!Files.exists(uploadPath)) {
//					Files.createDirectories(uploadPath);
//				}
//				Path filePath = uploadPath.resolve(imageName);
//				dto.getImage().transferTo(filePath.toFile());
//				existingProduct.setImageUrl("/images/products/" + imageName);
//			} catch (IOException e) {
//				throw new RuntimeException("Lỗi khi lưu hình ảnh", e);
//			}
//		}
//
//		existingProduct.setUpdatedAt(LocalDateTime.now());
//		Product updated = productRepository.save(existingProduct);
//		return productMapper.toDTO(updated);
//	}

	@Override
	public boolean softDeleteProduct(Integer id) {
		Product product = productRepository.findById(id).orElse(null);

		if (product == null || Boolean.FALSE.equals(product.getIsActive())) {
			return false; // Không tìm thấy hoặc đã bị xóa rồi
		}

		product.setIsActive(false); // Gán false để xóa mềm
		product.setUpdatedAt(LocalDateTime.now());
		productRepository.save(product);

		return true; // Xóa mềm thành công
	}

	@Override
	public boolean deleteProductPermanently(Integer id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với id = " + id));

		productRepository.delete(product);
		return true;
	}

	@Override
	public void updateStatus(Integer id, Boolean isActive) throws BadRequestException {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với id = " + id));

		if (product.getIsActive().equals(isActive)) {
			throw new BadRequestException("Trạng thái sản phẩm đã ở trạng thái yêu cầu");
		}

		product.setIsActive(isActive);
		product.setUpdatedAt(LocalDateTime.now());

		productRepository.save(product);

		// Optional: Ghi log
		System.out.println("Đã cập nhật trạng thái sản phẩm " + id + " thành " + isActive);
	}

	public Page<ProductResponseDTO> getActiveProductsByCategory(Integer categoryId, Pageable pageable) {
		return productRepository.findByCategoryIdAndIsActiveTrue(categoryId, pageable).map(productMapper::toDTO);
	}

	public Page<ProductResponseDTO> getAllProductsByCategory(Integer categoryId, Pageable pageable) {
		return productRepository.findByCategoryId(categoryId, pageable).map(productMapper::toDTO);
	}

	@Override
	public Page<ProductResponseDTO> getFilteredProducts(Integer categoryId, String keyword, Pageable pageable) {
		Page<Product> result = productRepository.findByCategoryIdAndIsActiveTrueAndNameContainingIgnoreCase(categoryId,
				keyword, pageable);
		return result.map(productMapper::toDTO);
	}

	@Override
	public ProductResponseDTO getProductDetail(Integer id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Không tìm thấy sản phẩm với ID: " + id));
		return productMapper.toDTO(product);
	}

	@Override
	public Page<ProductResponseDTO> searchProducts(String keyword, Pageable pageable) {
		Page<Product> products = productRepository.searchByNameOrDescription(keyword, pageable);
		return products.map(productMapper::toDTO);
	}

}
