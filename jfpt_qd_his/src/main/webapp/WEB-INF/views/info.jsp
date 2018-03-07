<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test</title>
</head>
<body>
	<div>
		<form id="mainForm" action="insert" method="post">
			<input type="hidden" id="id" name="id" value="${userInfo.id}"/>
			<input type="hidden" value="${testList}"/>
			<table>
				<tr>
					<td>用户名称</td>
					<td><input type="text" id="name" name="name"></td>
				</tr>
				<tr>
					<td><input type="button" value="提交" onclick="submit()"/></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>