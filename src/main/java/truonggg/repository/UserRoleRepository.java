package truonggg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import truonggg.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
	@Modifying
	@Transactional
	@Query("DELETE FROM UserRole ur WHERE ur.user.id = :userId")
	void deleteAllByUserId(@Param("userId") Integer userId);

	Optional<UserRole> findFirstByUserId(Integer userId);

}
