package truonggg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import truonggg.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
	void deleteById(Integer id);

}
