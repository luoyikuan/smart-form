package cn.bigak.sf.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.bigak.sf.dao.UserDao;
import cn.bigak.sf.entity.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@Override
	public List<User> getAllUser() {
		List<User> list = null;
		try {
			session = sessionFactory.getCurrentSession();
			list = session.createCriteria(User.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public User getUserById(int uId) {
		User user = null;
		try {
			session = sessionFactory.getCurrentSession();
			user = session.get(User.class, uId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;
		try {
			session = sessionFactory.getCurrentSession();
			user = (User) session.createCriteria(User.class).add(Restrictions.eq("email", email)).setMaxResults(1)
					.uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean saveUser(User user) {
		try {
			session = sessionFactory.getCurrentSession();
			return (Integer) session.save(user) > 0 ? true : false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean updateUser(User user) {
		try {
			session = sessionFactory.getCurrentSession();
			session.update(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public User getUserByLogin(String email, String pwd) {
		User user = null;
		try {
			session = sessionFactory.getCurrentSession();
			user = (User) session.createCriteria(User.class)
					.add(Restrictions.and(Restrictions.eq("email", email), Restrictions.eq("pwd", pwd)))
					.setMaxResults(1).uniqueResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public boolean delUser(User user) {
		try {
			session = sessionFactory.getCurrentSession();
			User dbUser = session.load(User.class, user.getId());
			session.delete(dbUser);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
