var M = {};
M.init = function() {
	// 登录按钮点击
	$('.btn_login').on('click', function() {
		$('#login-prompt').modal({
			relatedTarget : this,
			onConfirm : function(e) {
				M.dologin(e.data[0], e.data[1]);
			},
			onCancel : function(e) {

			}
		});
	});
	// register按钮点击
	$('.btn_register').on('click', function() {
		$('#register-prompt').modal({
			relatedTarget : this,
			onConfirm : function(e) {
				if (!M.checkEmail(e.data[0])) {
					M.alert("邮箱格式不符合要求！");
					return;
				}

				if (e.data[1] != e.data[2]) {
					M.alert("两次密码输入不一致！");
					return;
				}

				if (!M.checkPwd(e.data[1])) {
					M.alert("密码只能输入6-20个字母、数字、下划线！");
					return;
				}

				M.doRegister(e.data[0], e.data[1]);
			},
			onCancel : function(e) {

			}
		});
	});
}

M.dologin = function(email, password) {
	var req = {
		"user.email" : email,
		"user.pwd" : password
	};

	$.post({
		url : "login.action",
		data : req,
		dataType : "json",
		success : function(resp) {
			if (resp.error == 0) {
				window.location.href = "formList.api.action";
			} else if (resp.error == 1) {
				M.alert(resp.msg);
			}
		}
	});
}

M.doRegister = function(email, password) {
	var req = {
		"user.email" : email,
		"user.pwd" : password
	};

	$.post({
		url : "register.action",
		data : req,
		dataType : "json",
		success : function(resp) {
			if (resp.error == 0) {
				M.alert(resp.msg);
			} else if (resp.error == 1) {
				M.alert(resp.msg);
			}
		}
	});
}

M.alert = function(alert_data, callback) {
	$("#my-alert .alert_data").html(alert_data);
	$('#my-alert').modal({
		relatedTarget : this,
		onConfirm : function(options) {
			if (callback) {
				callback();
			}
		}
	});
}

M.checkPwd = function(pwd) {
	var patrn = /^(\w){6,16}$/;
	if (patrn.exec(pwd)) {
		return true;
	} else {
		return false;
	}
}

M.checkEmail = function(email) {
	var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
	if (reg.exec(email)) {
		return true;
	} else {
		return false;
	}
}

M.init();