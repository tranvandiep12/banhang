package truonggg.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import truonggg.model.Category;
import truonggg.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByCategory(Category category);

	List<Product> findByCategoryAndIsActiveTrue(Category category);

	Page<Product> findByCategoryId(Integer categoryId, Pageable pageable);

	Page<Product> findByCategoryIdAndIsActiveTrue(Integer categoryId, Pageable pageable);

	Page<Product> findByCategoryIdAndIsActiveTrueAndNameContainingIgnoreCase(Integer categoryId, String name,
			Pageable pageable);

	@Query("SELECT p FROM Product p WHERE " + "LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) "
			+ "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
	Page<Product> searchByNameOrDescription(@Param("keyword") String keyword, Pageable pageable);

}
