package cn.bigak.sf.dao;

import java.util.List;

import cn.bigak.sf.entity.User;

public interface UserDao {

	List<User> getAllUser();

	User getUserById(int uId);

	User getUserByEmail(String email);

	User getUserByLogin(String email, String pwd);

	boolean saveUser(User user);

	boolean updateUser(User user);

	boolean delUser(User user);

}
