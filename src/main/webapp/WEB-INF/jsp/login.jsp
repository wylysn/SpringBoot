<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
错误信息：<h4>${msg}</h4>
<form action="/loginUser" method="post">
    <p>账号：<input type="text" name="username" value="aa"/></p>
    <p>密码：<input type="text" name="password" value="a123456"/></p>
    <p><input type="submit" value="登录"/></p>
</form>
</body>
</html>