package cn.bigak.sf.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.bigak.sf.dao.InputDao;
import cn.bigak.sf.entity.Input;

@Repository
public class InputDaoImpl implements InputDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@Override
	public boolean saveInput(Input input) {
		try {
			session = sessionFactory.getCurrentSession();
			session.save(input);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Input getInputById(int inputId) {
		Input input = null;
		try {
			session = sessionFactory.getCurrentSession();
			input = session.get(Input.class, inputId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return input;
	}

	@Override
	public List<Input> getInputsByFormId(int formId) {
		List<Input> list = null;
		try {
			session = sessionFactory.getCurrentSession();
			list = session.createCriteria(Input.class).add(Restrictions.eq("form.formId", formId)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
