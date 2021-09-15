package cn.bigak.sf.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.bigak.sf.entity.Form;
import cn.bigak.sf.entity.User;

public interface FormService {

	boolean saveForm(String formHtml, User user);

	Map<String, Object> getSubmit(int formId);

	boolean saveFormData(HttpServletRequest request, int formId);

	boolean updateForm(Form form);

	List<Form> getFormsByUserId(int userId);
	
	Form getFormById(int formId);

	int delForm(int formId, User user);

	int countDataRows(int formId);

	public Map<String, Object> getFormModel(int formId);

	public Map<String, Object> getFormModelByPage(int formId, int pageSize, int pageIndex);

	public Map<String, Object> getFormModelByPage(int formId, int pageSize, int pageIndex, String like);
	
}
