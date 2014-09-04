<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
<title>Home</title>
<%@ include file="/WEB-INF/views/commons/util/GlobalSupport.jsp"%>

<!-- 表单工具 -->
<%@ include file="/WEB-INF/views/commons/util/EvensForm.jsp"%>
<style type="text/css">
.center-title{
text-align:center;
width:600px;
margin :20px auto; 

}

</style>
</head>
<body>
	<div class="center-title">
		<h2>新增单位用户</h2>
	</div>

	<div class="main-area" id="userForm" evens_form style="width: 693px;">
		<table border="0" cellpadding="0" cellspacing="0" class="clean-table">
			<tr>
				<td class="clean-table-td1">组织机构代码</td>
				<td class="clean-table-td2"><input name="username" type="text"
					class="clean-table-input" /></td>

				<td class="clean-table-td1-right">单位名称</td>
				<td class="clean-table-td2"><input type="text"
					name="companyName" class="clean-table-input" /></td>
			</tr>
			<tr>
				<td class="clean-table-td1">密&nbsp;&nbsp;码</td>
				<td class="clean-table-td2"><input type="text" name="password"
					class="clean-table-input" /></td>

				<td class="clean-table-td1-right">法定代表人（单位负责人）</td>
				<td class="clean-table-td2"><input type="text" name="name"
					class="clean-table-input" /></td>
			</tr>

			<tr>
				<td class="clean-table-td1">单位邮箱</td>
				<td class="clean-table-td2"><input type="text" name="email"
					id="email" class="clean-table-input" /></td>

				<td class="clean-table-td1-right">联系人姓名</td>
				<td class="clean-table-td2"><input type="text"
					onchange="checkInput(this)" name="contactName"
					class="clean-table-input" /></td>
			</tr>
			<tr>
				<td class="clean-table-td1">联系人证件类型</td>
				<td class="clean-table-td2"><select name="idType" code
					class="clean-table-input">
				</select></td>

				<td class="clean-table-td1-right">联系人证件号码</td>
				<td class="clean-table-td2"><input type="text" name="idCard"
					class="clean-table-input" /></td>
			</tr>
			<tr>
				<td class="clean-table-td1">联系人手机号码</td>
				<td class="clean-table-td2"><input type="text" name="cellPhone"
					class="clean-table-input" /></td>
			</tr>

			
		</table>
		<input type="hidden" value="2" name="userType"/>
		<div class="center_container">
			<input type="button" submitButton class="clean-nr-td1-an"
				onclick="register();"/>  
				<input type="button"  
					onclick="toMenu();" class="fwpt-nr-td1-an2" /> 
		</div>
		
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
			formData.userType = "2";
			var paramArray = getFormAsParamArray(formData); 
		  
			var paramStr = JSON.stringify(paramArray);
			EvensForm.prototype.postForm( "${pageContext.request.contextPath}/psp/user/addUserPreview",{usersJson:paramStr,userType:'2'});
			
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
