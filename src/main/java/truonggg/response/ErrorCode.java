package truonggg.response;

//thay tên class =resultCode
public enum ErrorCode {
	OK, NOT_FOUND, INVALID, MAX, MIN, INTERNAL_SERVER, ALREADY_EXIT;
	// INTERNAL_SERVER:gửi lên id null thì hiển thị
	// INVALID:không đúng chuẩn ví dụ như không nhập tên...
}