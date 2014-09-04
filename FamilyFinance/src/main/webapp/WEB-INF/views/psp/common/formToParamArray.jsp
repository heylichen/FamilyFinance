<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

<script type="text/javascript"> 
	function getFormAsParamArray(formData) {
		var map = new Object();
		var numberOfRow = 0;

		$.each(formData, function(k, v) {
			var delim = "，";
			if (v.indexOf(delim) == -1) {
				delim = ",";
			}
			var fieldArr = v.split(delim);
			if (fieldArr.length > numberOfRow) {
				numberOfRow = fieldArr.length;
			}

			map[k] = fieldArr;
		});

		var result = new Array();
		for (var i = 0; i < numberOfRow; i++) {
			var param = new Object();
			param.userType = formData.userType;
			$.each(map, function(k, v) {
				 
				var value = v[i];
				if (value) {
					param[k] = value;
				}

			});
			result.push(param);
		}
		return result;
	}
	function toMenu(){
		window.location.href='${pageContext.request.contextPath}/psp/user/';
	}
</script>

