package truonggg.sercurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	private final JwtAuthenticationFilter jwtRequestFilter;
	private final UserDetailsService userDetailsService;
	private final CustomAccessDeniedHandler accessDeniedHandler;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;

	@Lazy
	public SecurityConfiguration(JwtAuthenticationFilter jwtRequestFilter, UserDetailsService userDetailsService,
			CustomAccessDeniedHandler accessDeniedHandler, CustomAuthenticationEntryPoint authenticationEntryPoint) {
		this.jwtRequestFilter = jwtRequestFilter;
		this.userDetailsService = userDetailsService;
		this.accessDeniedHandler = accessDeniedHandler;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf(csrf -> csrf.disable());

		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(authz -> authz
				// Public APIs
				.requestMatchers("/auth/**", "/products/public/**", "/products/{id}", "/categories/**",
						"/promotions/**", "/images/**", "/", "/home")
				.permitAll()

				// ADMIN & EMPLOYEE - Ưu tiên viết trước
				.requestMatchers("/api/orders/admin/**", "/admin/orders/**", "/admin/promotions/**")
				.hasAnyAuthority("ROLE_ADMIN", "ROLE_EMPLOYEE")

				// CUSTOMER - Viết sau
				.requestMatchers("/api/cart/**", "/profile/**", "/checkout/**", "/api/orders/**")
				.hasAuthority("ROLE_CUSTOMER")

				// ADMIN only
				.requestMatchers("/users/**", "/admin/**").hasAuthority("ROLE_ADMIN")

				// Các API còn lại yêu cầu đăng nhập
				.anyRequest().authenticated());

		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

		http.exceptionHandling(
				ex -> ex.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint));

		return http.build();
	}

}
