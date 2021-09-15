package cn.bigak.sf.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.bigak.sf.entity.Form;
import cn.bigak.sf.entity.User;
import cn.bigak.sf.service.FormService;
import cn.bigak.sf.util.AjaxData;

@Controller("formAction")
public class FormAction extends ActionSupport {

	@Autowired
	private FormService formService;

	private AjaxData ajaxData;
	private String formHtml;
	private int formId;
	private String like;
	private int pageSize;
	private int pageIndex;


	public AjaxData getAjaxData() {
		return ajaxData;
	}

	public void setAjaxData(AjaxData ajaxData) {
		this.ajaxData = ajaxData;
	}

	public String getFormHtml() {
		return formHtml;
	}

	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
	}

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}


	public String addForm() {
		ajaxData = new AjaxData();
		if (formService.saveForm(formHtml, (User) getSession().get("user"))) {
			ajaxData.error = 0;
			ajaxData.msg = "保存成功";
		} else {
			ajaxData.error = 1;
			ajaxData.msg = "保存失败";
		}
		return SUCCESS;
	}

	public String delForm() {
		ajaxData = new AjaxData();
		User user = (User) getSession().get("user");
		int i = formService.delForm(formId, user);
		if (i == -1) {
			ajaxData.error = 1;
			ajaxData.msg = "无权限";
		} else if (i == 0) {
			ajaxData.error = 1;
			ajaxData.msg = "删除失败";
		} else {
			ajaxData.error = 0;
			ajaxData.msg = "删除成功";
		}
		return SUCCESS;
	}

	public String formList() {
		User user = (User) getSession().get("user");
		List<Form> formList = formService.getFormsByUserId(user.getId());
		ActionContext.getContext().put("formList", formList);
		return SUCCESS;
	}

	public String formdata() {
		ajaxData = new AjaxData();
		User user = (User) getSession().get("user");
		Form dbForm = formService.getFormById(formId);
		if (dbForm.getUser().getId().intValue() != user.getId().intValue()) {
			ajaxData.error = 1;
			ajaxData.msg = "请求非法";
			return SUCCESS;
		}

		Map<String, Object> formdata = formService.getFormModelByPage(formId, pageSize, pageIndex, like);
		ajaxData.data = formdata;
		ajaxData.error = 0;

		formdata.put("form", dbForm);
		return SUCCESS;
	}

	private Map<String, Object> getSession() {
		return ActionContext.getContext().getSession();
	}

	public String submit() {
		Map<String, Object> formModel = formService.getSubmit(formId);
		ActionContext.getContext().put("formModel", formModel);
		return SUCCESS;
	}

	public String doSubmit() {
		if (formService.saveFormData(ServletActionContext.getRequest(), formId)) {
			return SUCCESS;
		} else {
			return ERROR;
		}
	}

}
