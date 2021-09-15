package cn.bigak.sf.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import cn.bigak.sf.service.FormService;
import cn.bigak.sf.util.FormModel2Excel;

@Controller("downloadDataAction")
public class DownloadDataAction extends ActionSupport {
	@Autowired
	private FormService formService;
	
	public void downloadExcel() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		request.setCharacterEncoding("UTF-8");
		String strFID = request.getParameter("formId");
		int formId = Integer.parseInt(strFID);
		Map<String, Object> formModel = formService.getFormModel(formId);
		
		
		response.setContentType("application/force-download");
		response.addHeader("Content-Disposition", "attachment;fileName=form" + formId + ".xlsx");
		
		new FormModel2Excel().createExcel(response.getOutputStream(), formModel);
	}

}
