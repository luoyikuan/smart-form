package cn.bigak.sf.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.bigak.sf.dao.FormDao;
import cn.bigak.sf.entity.Form;

@Repository
public class FormDaoImpl implements FormDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@Override
	public boolean saveForm(Form form) {
		try {
			session = sessionFactory.getCurrentSession();
			session.save(form);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delForm(int formId) {
		try {
			session = sessionFactory.getCurrentSession();
			Form dbForm = session.load(Form.class, formId);
			session.delete(dbForm);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Form getFormById(int formId) {
		Form form = null;
		try {
			session = sessionFactory.getCurrentSession();
			form = session.get(Form.class, formId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public List<Form> getFormsByUserId(int userId) {
		List<Form> list = null;
		try {
			session = sessionFactory.getCurrentSession();
			list = session.createCriteria(Form.class).add(Restrictions.eq("user.id", userId)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean updateForm(Form form) {
		try {
			session = sessionFactory.getCurrentSession();
			session.update(form);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
