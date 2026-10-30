package truonggg.sercurity;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import truonggg.model.Role;
import truonggg.model.User;
import truonggg.model.UserRole;
import truonggg.repository.RoleRepository;
import truonggg.repository.UserRepository;
import truonggg.repository.UserRoleRepository;

@Configuration
public class AdminInitializer {

	@Value("${admin.default.username}")
	private String adminUsername;

	@Value("${admin.default.password}")
	private String adminPassword;

	@Value("${admin.default.email}")
	private String adminEmail;

	@Bean
	public CommandLineRunner initAdmin(UserRepository userRepository, RoleRepository roleRepository,
			UserRoleRepository userRoleRepository, PasswordEncoder encoder) {
		return args -> {
			// Kiểm tra đã có tài khoản admin chưa
			if (!userRepository.existsByUsername(adminUsername)) {

				// Tìm hoặc tạo role ROLE_ADMIN
				Role adminRole = roleRepository.findFirstByName("ROLE_ADMIN").orElseGet(() -> {
					Role newRole = new Role();
					newRole.setName("ROLE_ADMIN");
					return roleRepository.save(newRole);
				});

				// Tạo User
				User admin = User.builder().username(adminUsername).password(encoder.encode(adminPassword))
						.email(adminEmail).fullName("Administrator").isActive(true).createdAt(LocalDateTime.now())
						.build();

				admin = userRepository.save(admin);

				// Gán quyền ROLE_ADMIN
				UserRole userRole = new UserRole();
				userRole.setUser(admin);
				userRole.setRole(adminRole);
				userRoleRepository.save(userRole);

				System.out.println("✅ Tài khoản admin mặc định đã được tạo.");
			} else {
				System.out.println("🔁 Tài khoản admin đã tồn tại.");
			}
		};
	}
}
