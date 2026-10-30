package truonggg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import truonggg.model.Cart;
import truonggg.model.CartItem;
import truonggg.model.Product;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

	// Lấy tất cả CartItem theo CartId
	@EntityGraph(attributePaths = { "product", "cart" })
	List<CartItem> findByCartId(Integer cartId);

	@EntityGraph(attributePaths = { "product", "cart" })
	Optional<CartItem> findById(Integer id);

	// Tuỳ chọn: tìm theo cartId + productId (tránh thêm trùng)
	boolean existsByCartIdAndProductId(Integer cartId, Integer productId);

	Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

	List<CartItem> findAllByIdIn(List<Integer> ids);

}