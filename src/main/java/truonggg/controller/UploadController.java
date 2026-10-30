package truonggg.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/upload")
public class UploadController {

	private final String UPLOAD_DIR = "uploads/images/";

	@PostConstruct
	public void init() throws IOException {
		// Đảm bảo thư mục tồn tại khi khởi động app
		Files.createDirectories(Paths.get(UPLOAD_DIR));
	}

	@PostMapping("/image")
	public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
		try {
			String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());
			Path path = Paths.get("uploads/images/" + fileName);

			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

			String fileUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/images/")
					.path(fileName).toUriString();

			return ResponseEntity.ok(fileUrl);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Lỗi khi upload ảnh: " + e.getMessage());
		}
	}

}
