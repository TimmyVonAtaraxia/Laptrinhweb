package JDBC.dao;

import java.sql.Date;

import JDBC.model.User;

public interface userdao {
	User get(String username);
	void insert(User user);
	boolean checkExistEmail(String email);
	boolean checkExistUsername(String username);
	boolean checkExistPhone(String phone);
	boolean updateResetToken(String email, String token, Date expiry, String otp);  // Thêm OTP
    boolean updatePasswordByToken(String token, String newPassword);   // Giữ nguyên
    User findByResetToken(String token);  // Giữ nguyên

}
