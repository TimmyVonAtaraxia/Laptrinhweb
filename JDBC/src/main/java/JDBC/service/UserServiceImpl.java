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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User get(String username) {
		return userdao.get(username);
		// TODO Auto-generated method stub
	}

}
