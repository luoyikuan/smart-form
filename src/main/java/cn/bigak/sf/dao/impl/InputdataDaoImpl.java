package cn.bigak.sf.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.bigak.sf.dao.InputdataDao;
import cn.bigak.sf.entity.Form;
import cn.bigak.sf.entity.Input;
import cn.bigak.sf.entity.Inputdata;

@Repository
public class InputdataDaoImpl implements InputdataDao {
	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	@Override
	public boolean saveInputdata(Inputdata inputdata) {
		try {
			session = sessionFactory.getCurrentSession();
			session.save(inputdata);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean delOneRowInputdata(int formId, int rowIndex) {
		try {
			session = sessionFactory.getCurrentSession();
			List<Inputdata> dbInputdatas = getOneRowInputdatas(formId, rowIndex);
			for (Inputdata inputdata : dbInputdatas) {
				session.delete(inputdata);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Inputdata getInputdataById(int inputdataId) {
		Inputdata inputdata = null;
		try {
			session = sessionFactory.getCurrentSession();
			inputdata = session.get(Inputdata.class, inputdataId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputdata;
	}

	@Override
	public List<Inputdata> getOneRowInputdatas(int formId, int rowIndex) {
		List<Inputdata> list = null;
		try {
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Inputdata.class);
			criteria.add(Restrictions.eq("rowIndex", rowIndex));
			criteria.createCriteria("input").createCriteria("form").add(Restrictions.eq("formId", formId));
			
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int countRowInputdatasByFormId(int formId) {
		int count = 0;
		try {
			session = sessionFactory.getCurrentSession();
			Form form = session.get(Form.class, formId);

			Set<Input> inputs = form.getInputs();
			Input oneInput = inputs.iterator().next();

			Input input = session.get(Input.class, oneInput.getInputId());
			count = input.getInputdatas().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean updateValue(int inputdataId, String value) {
		try {
			session = sessionFactory.getCurrentSession();
			Inputdata dbInputdata = session.load(Inputdata.class, inputdataId);
			dbInputdata.setValue(value);
			session.update(dbInputdata);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Inputdata> getInputdataByFormId(int formId) {
		List<Inputdata> list = null;
		try {
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Inputdata.class);
			criteria.createCriteria("input").createCriteria("form").add(Restrictions.eq("formId", formId));

			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Inputdata> getInputdataByFormId(int formId, String like) {
		List<Inputdata> list = null;
		try {
			session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(Inputdata.class)
					.add(Restrictions.like("value", like, MatchMode.ANYWHERE));
			criteria.createCriteria("input").createCriteria("form").add(Restrictions.eq("formId", formId));
			list = criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

}
