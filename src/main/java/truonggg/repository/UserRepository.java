package truonggg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import truonggg.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(final String username);

	boolean existsByUsername(String adminUsername);

//	@Query("SELECT u FROM User u LEFT JOIN FETCH u.list WHERE u.id = :id")
//	Optional<User> findByIdWithRoles(@Param("id") Integer id);

}
