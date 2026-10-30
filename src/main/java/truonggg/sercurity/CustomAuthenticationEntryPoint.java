package truonggg.sercurity;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//(5)
@Component
//dung để hanlder lỗi 401/403
//401:chưa đăng nhập/đăng nhập thất bại
//403:đăng nhập rồi mà chưa cấp quyền(hiểu như chưa đủ quyền để truy cập)
//lớp này là xử lí lỗi 401
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.getWriter().write("Authentication Required. Please login to access this resource.");
	}
}