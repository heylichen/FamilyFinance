<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
<title>Home</title>

<%@ include file="/WEB-INF/views/commons/util/GlobalSupport.jsp"%>
</head>
<body>
	<h2>公共服务平台测试辅助-用户管理</h2>

	<a href="${pageContext.request.contextPath}/psp/user/addUser">添加个人用户</a>
	<a href="${pageContext.request.contextPath}/psp/user/addCompanyUser">添加单位用户</a>

 



	<script type="text/javascript">
		function test() { 
		 var path = '${pageContext.request.contextPath}/psp/user/users';
			$.ajax({
				type : 'POST',
				url : path,
				dataType : 'json',
				contentType : "application/json"
			}).done(function(data, textStatus, jqXHR) {
				var response = jqXHR.responseJSON;   
					alert("用户添加成功"); 
			}).fail(function(jqXHR, textStatus, errorThrown) {
				console.log("fail");
				
				console.log(jqXHR.responseJSON);
				var response = jqXHR.responseJSON;
				if (response && response.jsonResponse && !response.success) {
					alert(response.msg);
				}
			}); 

		}
	</script>
</body>
</html>
