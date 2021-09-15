package cn.bigak.sf.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.bigak.sf.entity.Inputdata;
import cn.bigak.sf.entity.User;
import cn.bigak.sf.service.FormService;
import cn.bigak.sf.service.InputdataService;
import cn.bigak.sf.util.AjaxData;

@Controller("inputdataAction")
public class InputdataAction extends ActionSupport {
	@Autowired
	private InputdataService inputdataService;
	@Autowired
	private FormService formService;

	private int formId;
	private int rowIndex;

	private Inputdata inputdata;

	private AjaxData ajaxData;

	public int getFormId() {
		return formId;
	}

	public void setFormId(int formId) {
		this.formId = formId;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Inputdata getInputdata() {
		return inputdata;
	}

	public void setInputdata(Inputdata inputdata) {
		this.inputdata = inputdata;
	}

	public AjaxData getAjaxData() {
		return ajaxData;
	}

	public void setAjaxData(AjaxData ajaxData) {
		this.ajaxData = ajaxData;
	}

	public String delOneRowData() {
		ajaxData = new AjaxData();
		User user = (User) ActionContext.getContext().getSession().get("user");
		if (formService.getFormById(formId).getUser().getId().intValue() != user.getId().intValue()) {
			ajaxData.error = 1;
			ajaxData.msg = "非法请求";
			return SUCCESS;
		}

		if (inputdataService.delOneRow(formId, rowIndex)) {
			ajaxData.error = 0;
			ajaxData.msg = "删除成功";
		} else {
			ajaxData.error = 1;
			ajaxData.msg = "删除失败";
		}

		return SUCCESS;
	}

	public String updateOneCellData() {
		ajaxData = new AjaxData();
		User user = (User) ActionContext.getContext().getSession().get("user");
		Inputdata dbInputdata = inputdataService.getInputdataById(inputdata.getInputDataId());

		if (dbInputdata.getInput().getForm().getUser().getId().intValue() != user.getId().intValue()) {
			ajaxData.error = 1;
			ajaxData.msg = "非法请求";
			return SUCCESS;
		}

		if (inputdataService.updateInputdataValueById(inputdata.getInputDataId(), inputdata.getValue().trim())) {
			ajaxData.error = 0;
			ajaxData.msg = "更新成功";
		} else {
			ajaxData.error = 1;
			ajaxData.msg = "更新失败";
		}

		return SUCCESS;
	}

}
