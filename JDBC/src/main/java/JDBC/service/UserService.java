package JDBC.service;

import JDBC.model.User;

public interface UserService {User login(String username, String password);
User get(String username);
void insert(User user);
boolean register(String email, String password, String username, String fullname, String phone);
boolean checkExistEmail(String email);
boolean checkExistUsername(String username);
boolean checkExistPhone(String phone);
String requestPasswordReset(String email);  // Trả về OTP thay vì gửi email
boolean resetPassword(String token, String newPassword, String otp);  // Thêm kiểm tra OTP
User findByResetToken(String token);  // Tìm user qua token
boolean isTokenValid(String token);   // Kiểm tra token hợp lệ và chưa hết hạn
}
