var M = {};

M.delForm = function(formId) {
	$('#my-confirm').modal({
		relatedTarget : this,
		onConfirm : function(options) {
			M.doDelForm(formId);
		},
		onCancel : function() {
		}
	});
}

M.doDelForm = function(formId) {
	var req = {}
	req.formId = formId;
	$.post({
		url : "delForm.api.action",
		data : req,
		dataType : "json",
		success : function(resp) {
			if (resp.error == 0) {
				$("li[smartform-data-form-id='" + formId + "']").remove();
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
