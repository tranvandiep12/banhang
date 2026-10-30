package truonggg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import truonggg.model.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
	Optional<Cart> findByUserId(Integer userId);
}
