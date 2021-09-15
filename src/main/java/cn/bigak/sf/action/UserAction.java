package cn.bigak.sf.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.bigak.sf.entity.User;
import cn.bigak.sf.service.UserService;
import cn.bigak.sf.util.AjaxData;

@Controller("userAction")
public class UserAction extends ActionSupport {
	@Autowired
	private UserService userService;

	private AjaxData ajaxData;

	private User user;

	private String newPwd;

	private String reNewPwd;

	public AjaxData getAjaxData() {
		return ajaxData;
	}

	public void setAjaxData(AjaxData ajaxData) {
		this.ajaxData = ajaxData;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public String getReNewPwd() {
		return reNewPwd;
	}

	public void setReNewPwd(String reNewPwd) {
		this.reNewPwd = reNewPwd;
	}

	public String login() {
		ajaxData = new AjaxData();
		User dbuser = userService.getUserByLogin(user);
		if (dbuser == null) {
			ajaxData.error = 1;
			ajaxData.msg = "邮箱或者密码错误";
		} else if (dbuser.getRole().intValue() != 1) {
			ajaxData.error = 1;
			ajaxData.msg = "用户角色不合法";
		} else {
			ajaxData.error = 0;
			ajaxData.msg = "登录成功";
			Map<String, Object> session = getSession();
			session.put("user", dbuser);
		}
		user = null;
		return SUCCESS;
	}

	public String register() {
		ajaxData = new AjaxData();
		int i = userService.saveUser(user);
		if (i == -1) {
			ajaxData.error = 1;
			ajaxData.msg = "该邮箱已经存在";
		} else if (i == 0) {
			ajaxData.error = 1;
			ajaxData.msg = "注册失败";
		} else {
			ajaxData.error = 0;
			ajaxData.msg = "注册成功";
		}
		user = null;
		return SUCCESS;
	}

	public String logout() {
		getSession().put("user", null);
		return SUCCESS;
	}

	public String updateUserPwd() {
		ajaxData = null;
		user = (User) getSession().get("user");
		return SUCCESS;
	}

	public String doUpdateUserPwd() {
		ajaxData = new AjaxData();
		
		if (newPwd == null || reNewPwd == null || newPwd.trim().equals("") || reNewPwd.trim().equals("")) {
			ajaxData.msg = "密码不能为空";
			return ERROR;
		}

		if (!newPwd.trim().equals(reNewPwd.trim())) {
			ajaxData.msg = "两次密码输入不一致";
			return ERROR;
		}
		
		if (newPwd.length() < 6 || newPwd.length() > 16) {
			ajaxData.msg = "密码长度必须在[6,16]内";
			return ERROR;
		}
		
		int i = userService.updateUserPwd(user.getEmail(), user.getPwd(), newPwd);
		if (i == -1) {
			ajaxData.msg = "旧密码不正确";
			return ERROR;
		}
		
		if (i == 0) {
			ajaxData.msg = "修改失败";
			return ERROR;
		} else {
			ajaxData.msg = "修改成功";
			return SUCCESS;
		}
	}

	private Map<String, Object> getSession() {
		return ActionContext.getContext().getSession();
	}

	private Map getRequest() {
		return (Map) ActionContext.getContext().get("request");
	}

}
