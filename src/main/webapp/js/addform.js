var M = {};
M.init = function() {
	M.source = $("#source");

	$("#btn_save").click(function() {
		M.doAddForm(M.source.val());
	});

}

M.back = function() {
	window.history.back();
}

M.doAddForm = function(formHtml) {
	var req = {};
	req.formHtml = formHtml;
	$.post({
		url : "addForm.api.action",
		data : req,
		dataType : "json",
		success : function(resp) {
			if (resp.error == 0) {
				alert(resp.msg);
				window.location.href = "formList.api.action";
			} else if (resp.error == 1) {
				alert(resp.msg);
			}
		},
		error : function(XMLHttpRequest) {
			if (XMLHttpRequest.status == 1000) {
				alert("请先登录！");
				window.location.href = "index.html";
				return;
			}
			alert("错误：" + XMLHttpRequest.statusText);
		}
	});
}

M.init();