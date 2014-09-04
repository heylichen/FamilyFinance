<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
<title>Home</title>
<%@ include file="/WEB-INF/views/commons/util/GlobalSupport.jsp"%>

<!-- 表单工具 -->
<%@ include file="/WEB-INF/views/commons/util/EvensForm.jsp"%>
<%@ include file="/WEB-INF/views/commons/util/jqGridInclude.jsp"%>
<style type="text/css">
.center-title {
	text-align: center;
	width: 600px;
	margin: 20px auto;
}
</style>
</head>
<body>
	<div class="main-area">
		<div class="center-title">
			<h2>新增单位测试用户预览</h2>
		</div>

		<table id="list">
			<tr>
			</tr>
		</table>
		<div id="pager"></div>

		<div class="main-area" id="userForm" evens_form style="width: 693px;">

			<div class="center_container">
				<input type="button" submitButton class="clean-nr-td1-an"
					onclick="register();" />
					<input type="button"  
					onclick="toMenu();" class="fwpt-nr-td1-an2" /> 
			</div>
		</div>
	</div>
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
				codes : [ "idType", "userType" ],
				data : {
					populate : graduateInfo
				},
				afterFormLoaded : loadGrid
			});

			graduateInfo = null;
		});

		function loadGrid() {
			var jsonStr = '${usersJson}';
			var json = $.parseJSON(jsonStr);
			console.log($.parseJSON(jsonStr));

			//初始化列岗位列表
			$("#list").jqGrid({
				datatype : "jsonstring",
				datastr : json,
				autowidth : true,
				pager : "#pager",
				rowNum : 10,
				rowList : [ 10, 50, 100 ],
				viewrecords : true,
				gridview : true,
				multiselect : true,
				autoencode : true,
				jsonReader : {
					root : "rows",
					page : "currentPage",
					total : "pageCount",
					records : "rowCount",
					repeatitems : true
				},
				prmNames : {
					page : "page.page",
					rows : "page.limit",
					sort : 'sort',
					order : 'order'
				},//参数名称
				shrinkToFit : true,
				height : 'auto',
				loadonce : false,

				//caption : '申请本单位毕业生名单',
				colModel : [ {
					name : "username",
					label : "组织机构代码",
					width : 75,
					viewable : true,
					align : "center",
					'editrules' : {
						edithidden : true
					}
				}, {
					name : "password",
					label : "密码",
					width : 50,
					align : "center"
				}, {
					name : "companyName",
					label : "单位名称",
					width : 120,
					align : "center"
				}, {
					name : "name",
					label : "法定代表",
					width : 70,
					align : "center"
				}, {
					name : "contactName",
					label : "联系人姓名",
					width : 50,
					hidden : true,
					align : "center"
				}, {
					name : "idType",
					label : "联系人证件类型",
					valueField : "idType",
					codeType : "idType",
					width : 70,
					hidden:true,
					align : "center",
					viewable : true,
					'editrules' : {
						edithidden : true
					},
					formatter : GridSelect.prototype.codeFormatter
				}, {
					name : "idCard",
					label : "联系人证件号",
					width : 120,
					search : true,
					stype : 'text',
					viewable : true,
					align : "center",
					'editrules' : {
						edithidden : true
					}
				}, {
					name : "cellPhone",
					label : "联系人手机",
					width : 90,
					align : "center",
					viewable : true,
					'editrules' : {
						edithidden : true
					}
				}, {
					name : "email",
					label : "单位邮箱",
					width : 100,
					viewable : true, 
					'editrules' : {
						edithidden : true
					}
				}, {
					name : "userType",
					label : "用户类型",
					valueField : "userType",
					codeType : "userType",
					align : "center",
					viewable : true,
					'editrules' : {
						edithidden : true
					},
					hidden : true,
					formatter : GridSelect.prototype.codeFormatter
				} ]
			});

		}

		function register() {
			var url = "${pageContext.request.contextPath}/psp/user/add"; 
			$.ajax({
				type : 'POST',
				url : url, 
				dataType : 'json',
				contentType : "application/json"
			}).done(function(data, textStatus, jqXHR) { 
					alert("用户添加成功"); 
			}).fail(function(jqXHR, textStatus, errorThrown) {
				var response = jqXHR.responseJSON;
				if (response.msg) {
					alert(response.msg);
				} else{
					alert("系统异常！");
				}
			});
		}
		function toMenu(){
			window.location.href='${pageContext.request.contextPath}/psp/user/';
		}
	</script>
</body>
</html>
