package truonggg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import truonggg.model.VariantProduct;

@Repository
public interface VariantProductRepository extends JpaRepository<VariantProduct, Integer> {

}
