package truonggg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import truonggg.model.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
	List<Orders> findByUserId(Integer userId);

//	List<Orders> findAllByOrderByCreatedDateDesc();
}
