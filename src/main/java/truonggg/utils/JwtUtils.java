package truonggg.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import truonggg.model.Role;
import truonggg.sercurity.CustomUserDetails;

//(3)
@Component
public class JwtUtils {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${jwt.duration}") // kĩ thuật đọc dữ liệu từ file propeties or yml.
	private long jwtDuration;// thời gian tồn tại của token

	public String generateToken(final CustomUserDetails user) {
		Map<String, Object> claims = new HashMap<>();

		// ✅ Thêm authorities đúng định dạng
		claims.put("authorities", user.getAuthorities().stream().map(auth -> auth.getAuthority()) // ví dụ: "USER",
																									// "ADMIN"
				.toList());

		// Bạn vẫn có thể giữ roles nếu cần dùng riêng
		claims.put("roles", user.getRoles().stream().map(Role::getName).toList());

		var expirationMillis = new Date(System.currentTimeMillis() + 1000 * jwtDuration); // 7 days

		return Jwts.builder().setClaims(claims).setSubject(user.getUsername()).setIssuedAt(new Date())
				.setExpiration(expirationMillis).signWith(this.getSignKey()).compact();
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(this.getSignKey()).build().parseClaimsJws(token).getBody();
	}

	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
}