package cn.bigak.sf.service;

import java.util.List;

import cn.bigak.sf.entity.User;

public interface UserService {

	List<User> getAllUser();

	User getUserById(int uId);

	int saveUser(User user);

	User getUserByLogin(User user);

	int updateUserPwd(String email, String oldPwd, String newPwd);

	boolean delUser(int userId);
}
