package JDBC.service;

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

}
