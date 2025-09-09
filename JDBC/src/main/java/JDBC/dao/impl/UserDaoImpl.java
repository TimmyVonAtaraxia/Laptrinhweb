package JDBC.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import JDBC.DBConnection;
import JDBC.dao.userdao;
import JDBC.model.User;

public class UserDaoImpl implements userdao{
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public User get(String username) {
		String sql = "SELECT * FROM [User] WHERE username = ? ";
		try {
			conn = new DBConnection().getConnectionW();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUserName(rs.getString("username"));
				user.setFullName(rs.getString("fullname"));
				user.setPassWord(rs.getString("password"));
				user.setAvatar(rs.getString("avatar"));

				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void insert(User user) {
		String sql = "INSERT INTO [User](email, username, fullname, password, avatar, phone ) VALUES (?,?,?,?,?,?)";
		try {
			conn = new DBConnection().getConnectionW();
			ps = conn.prepareStatement(sql);
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getUserName());
			ps.setString(3, user.getFullName());
			ps.setString(4, user.getPassWord());
			ps.setString(5, user.getAvatar());
			ps.setString(7, user.getPhone());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkExistEmail(String email) {
		boolean duplicate = false;
		String query = "select * from [user] where email = ?";
		try {
			conn = new DBConnection().getConnectionW();
			ps = conn.prepareStatement(query);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;
	}

	@Override
	public boolean checkExistUsername(String username) {
		boolean duplicate = false;
		String query = "select * from [User] where username = ?";
		try {
			conn = new DBConnection().getConnectionW();
			ps = conn.prepareStatement(query);
			ps.setString(1, username);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;
	}

	@Override
	public boolean checkExistPhone(String phone) {
		boolean duplicate = false;
		String query = "select * from [User] where phone = ?";
		try {
			conn = new DBConnection().getConnectionW();
			ps = conn.prepareStatement(query);
			ps.setString(1, phone);
			rs = ps.executeQuery();
			if (rs.next()) {
				duplicate = true;
			}
			ps.close();
			conn.close();
		} catch (Exception ex) {
		}
		return duplicate;
		}

// Các phương thức cũ giữ nguyên

    @Override
    public boolean updateResetToken(String email, String token, Date expiry, String otp) {
        String combinedToken = token + ":" + otp;  // Kết hợp token và OTP
        String sql = "UPDATE [User] SET reset_token = ?, reset_expiry = ? WHERE email = ?";
        try {
            conn = new DBConnection().getConnectionW();
            ps = conn.prepareStatement(sql);
            ps.setString(1, combinedToken);
            ps.setDate(2, (java.sql.Date) expiry);
            ps.setString(3, email);
            int rows = ps.executeUpdate();
            ps.close();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updatePasswordByToken(String token, String newPassword) {
        String sql = "UPDATE [User] SET password = ?, reset_token = NULL, reset_expiry = NULL WHERE reset_token LIKE ?";
        try {
            conn = new DBConnection().getConnectionW();
            ps = conn.prepareStatement(sql);
            ps.setString(1, newPassword);  // Khuyến nghị hash bằng BCrypt
            ps.setString(2, token + ":%");  // Tìm token bất kể OTP
            int rows = ps.executeUpdate();
            ps.close();
            conn.close();
            return rows > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User findByResetToken(String token) {
        String sql = "SELECT * FROM [User] WHERE reset_token LIKE ?";
        try {
            conn = new DBConnection().getConnectionW();
            ps = conn.prepareStatement(sql);
            ps.setString(1, token + ":%");  // Tìm token bất kể OTP
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUserName(rs.getString("username"));
                user.setFullName(rs.getString("fullname"));
                user.setPassWord(rs.getString("password"));
                user.setAvatar(rs.getString("avatar"));
                user.setRoleid(rs.getInt("roleid"));
                user.setPhone(rs.getString("phone"));
                user.setCreatedDate(rs.getDate("createddate"));
                user.setResetToken(rs.getString("reset_token"));
                user.setResetExpiry(rs.getDate("reset_expiry"));
                rs.close();
                ps.close();
                conn.close();
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
