package truonggg.service;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import truonggg.DTO.request.ProductRequestDTO;
import truonggg.DTO.request.ProductUpdateDTO;
import truonggg.DTO.response.ProductResponseDTO;

public interface ProductService {
	List<ProductResponseDTO> getActiveProductsByCategory(Integer categoryId);

	List<ProductResponseDTO> getAllProductsByCategory(Integer categoryId);

	ProductResponseDTO saveProductWithImage(ProductRequestDTO dto);

	ProductResponseDTO updateProduct(Integer id, ProductUpdateDTO dto);

	boolean softDeleteProduct(Integer id);

	boolean deleteProductPermanently(Integer id);

	void updateStatus(Integer id, Boolean isActive) throws BadRequestException;

	Page<ProductResponseDTO> getActiveProductsByCategory(Integer categoryId, Pageable pageable);

	Page<ProductResponseDTO> getAllProductsByCategory(Integer categoryId, Pageable pageable);

	Page<ProductResponseDTO> getFilteredProducts(Integer categoryId, String keyword, Pageable pageable);

	ProductResponseDTO getProductDetail(Integer id);

	Page<ProductResponseDTO> searchProducts(String keyword, Pageable pageable);

}
