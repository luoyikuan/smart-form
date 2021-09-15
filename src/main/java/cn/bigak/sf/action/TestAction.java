package cn.bigak.sf.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.bigak.sf.service.FormService;
import cn.bigak.sf.service.UserService;
import cn.bigak.sf.service.impl.FormServiceImpl;
import cn.bigak.sf.util.AjaxData;

@Controller(value = "testAction")
public class TestAction extends ActionSupport {
	@Autowired
	private UserService userService;

	@Autowired
	private FormService formService;
	
	private AjaxData ajaxData;

	public AjaxData getAjaxData() {
		return ajaxData;
	}

	public void setAjaxData(AjaxData ajaxData) {
		this.ajaxData = ajaxData;
	}

	public String test() {
		ajaxData = new AjaxData();
		ajaxData.error = 0;
		Object t =  formService.getFormModelByPage(16, 5, 0, "28");
		ajaxData.data = t;
		return SUCCESS;
	}

}
