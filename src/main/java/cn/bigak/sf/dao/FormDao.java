package cn.bigak.sf.dao;

import java.util.List;

import cn.bigak.sf.entity.Form;

public interface FormDao {

	boolean saveForm(Form form);
	
	boolean updateForm(Form form);
	
	boolean delForm(int formId);
	
	Form getFormById(int formId);
	
	List<Form> getFormsByUserId(int userId);

}
