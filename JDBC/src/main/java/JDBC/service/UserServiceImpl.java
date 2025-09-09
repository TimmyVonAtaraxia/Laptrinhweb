package JDBC.service;

import java.util.Date;
import java.util.Random;

import JDBC.dao.userdao;
import JDBC.dao.impl.UserDaoImpl;
import JDBC.model.User;

public class UserServiceImpl implements UserService {
	userdao userdao = new UserDaoImpl();

	@Override
	public User login(String username, String password) {
		User user = this.get(username);
		if (user != null && password.equals(user.getPassWord())) {
			return user;
		}
		return null;
	}

	@Override
	public User get(String username) {

		return userdao.get(username);
	}

	@Override
	public boolean register(String username, String password, String email, String fullname, String phone) {
		if (userdao.checkExistUsername(username)) {
			return false;
		}
		long millis = System.currentTimeMillis();
		java.sql.Date date = new java.sql.Date(millis);
		userdao.insert(new User(email, username, fullname, password, null, phone));
		return true;
	}

	public boolean checkExistEmail(String email) {
		return userdao.checkExistEmail(email);
	}

	public boolean checkExistUsername(String username) {
		return userdao.checkExistUsername(username);
	}

	@Override
	public boolean checkExistPhone(String phone) {
		return userdao.checkExistPhone(phone);
	}

	@Override
	public void insert(User user) {
		userdao.insert(user);
	}
	// Implement mới
    @Override
    public String requestPasswordReset(String email) {
        if (!userdao.checkExistEmail(email)) {
            return null;  // Email không tồn tại
        }

        // Tạo token ngẫu nhiên
        String token = java.util.UUID.randomUUID().toString();
        java.sql.Date expiry = new java.sql.Date(System.currentTimeMillis() + 3600000);  // 1 giờ

        // Tạo OTP ngẫu nhiên (6 chữ số)
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Lưu token và OTP vào DB (OTP có thể mã hóa đơn giản)
        if (userdao.updateResetToken(email, token, expiry, otp)) {
            return otp;  // Trả về OTP để hiển thị
        }
        return null;
    }

    @Override
    public boolean resetPassword(String token, String newPassword, String otp) {
        if (isTokenValid(token)) {
            User user = findByResetToken(token);
            if (user != null && otp.equals(user.getResetToken().split(":")[1])) {  // Giả định token chứa OTP
                return userdao.updatePasswordByToken(token, newPassword);
            }
        }
        return false;
    }

    @Override
    public User findByResetToken(String token) {
        return userdao.findByResetToken(token);
    }

    @Override
    public boolean isTokenValid(String token) {
        User user = findByResetToken(token);
        if (user != null && user.getResetExpiry() != null) {
            return new Date().before(user.getResetExpiry());
        }
        return false;
    }

}
