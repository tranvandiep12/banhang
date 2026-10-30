package truonggg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import truonggg.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
