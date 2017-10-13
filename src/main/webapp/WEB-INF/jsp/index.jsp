<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
	<h1>欢迎${user.userName }光临!请选择你的操作:</h1><br>
    <ul>
        <shiro:hasPermission name="userInfo:add"><li>增加</li></shiro:hasPermission>
        <shiro:hasPermission name="userInfo:del"><li>删除</li></shiro:hasPermission>
        <shiro:hasPermission name="userInfo:update"><li>修改</li></shiro:hasPermission>
        <shiro:hasPermission name="userInfo:query"><li>查询</li></shiro:hasPermission>
    </ul>
    <a href="/logOut">点我注销</a>
</body>
</html>