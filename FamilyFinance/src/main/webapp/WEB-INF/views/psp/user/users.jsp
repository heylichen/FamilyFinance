<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
<title>Home</title>
<%@ include file="/WEB-INF/views/commons/util/GlobalSupport.jsp"%>

<!-- 表单工具 -->
<%@ include file="/WEB-INF/views/commons/util/EvensForm.jsp"%>
<style type="text/css">
.center-title {
	text-align: center;
	width: 600px;
	margin: 20px auto;
}
</style>
</head>
<body>
	<div class="center-title">
		<h2>用户列表</h2>
	</div>

	 <div>
	 
	 ${user.name}
	 </div>
	 
	 
	 
	 
	<%@ include file="/WEB-INF/views/psp/common/formToParamArray.jsp"%>
	<script type="text/javascript">
		var path = '${pageContext.request.contextPath}';

		$(function() {
			var graduateInfo = {
				idType : '01',
				password : "123456",
				email : "kuachen@qq.com"
			};
			console.log();
			EvensForm.prototype.init({
				data : {
					populate : graduateInfo
				}
			});

			graduateInfo = null;
		});

		function register() {
			var result = FormValidation.prototype.checkBeforeSubmit();
			if (result && !result.success) {
				Dialog.prototype.alert("消息", result.msg);
				return;
			}

			var formData = EvensForm.prototype.getFormData();
			formData.userType = "1";
			var paramArray = getFormAsParamArray(formData);

			var paramStr = JSON.stringify(paramArray);
			
			EvensForm.prototype.postForm( "${pageContext.request.contextPath}/psp/user/addUserPreview",{usersJson:paramStr,userType:'1'});
			
			/* var url = "${pageContext.request.contextPath}/psp/user/add";

			$.ajax({
				type : 'POST',
				url : url,
				data : paramStr,
				dataType : 'json',
				contentType : "application/json"
			}).done(function(data, textStatus, jqXHR) {
				var response = jqXHR.responseJSON;
				if(response&&response.jsonResponse&&response.success){
					alert("success");
				}
			}).fail(function(jqXHR, textStatus, errorThrown) {
				var response = jqXHR.responseJSON;
				if(response&&response.jsonResponse&&!response.success){
					alert(response.msg);
				}
			}); */
		}
	</script>
</body>
</html>
