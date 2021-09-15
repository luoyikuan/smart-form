package cn.bigak.sf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bigak.sf.dao.UserDao;
import cn.bigak.sf.entity.User;
import cn.bigak.sf.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

	@Override
	public User getUserById(int uId) {
		return userDao.getUserById(uId);
	}

	@Override
	public int saveUser(User user) {
		user.setEmail(user.getEmail().trim().toLowerCase());
		user.setPwd(user.getPwd().trim());
		user.setRole((short) 1);

		User dbUser = userDao.getUserByEmail(user.getEmail());

		if (dbUser != null) {
			return -1;
		}

		return userDao.saveUser(user) ? user.getId() : 0;
	}

	@Override
	public User getUserByLogin(User user) {
		user.setEmail(user.getEmail().toLowerCase().trim());
		user.setPwd(user.getPwd().trim());
		User dbUser = userDao.getUserByLogin(user.getEmail(), user.getPwd());
		return dbUser;
	}

	/**
	 * 旧密码不正确方法 -1
	 * 成功返回 1
	 * 失败返回 0
	 */
	@Override
	public int updateUserPwd(String email, String oldPwd, String newPwd) {
		User user = new User();
		user.setEmail(email);
		user.setPwd(oldPwd);

		newPwd = newPwd.trim();

		User dbUser = getUserByLogin(user);
		if (dbUser == null) {
			return -1;
		}
		
		dbUser.setPwd(newPwd);

		return userDao.updateUser(dbUser) ? 1 : 0;
	}

	@Override
	public boolean delUser(int userId) {
		User user = new User();
		user.setId(userId);
		return userDao.delUser(user);
	}

}
