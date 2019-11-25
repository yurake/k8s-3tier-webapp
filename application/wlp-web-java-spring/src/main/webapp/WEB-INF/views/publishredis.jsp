<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>Hello k8s-3tier-webapp</title>
<link rel="icon" type="image/png" sizes="16x16" href="./images/icon-16x16.png" />
</head>
<body bgcolor=white>

	<table>
		<tr>
			<td>
				<h1>Publish Redis</h1>
				<p>${publishRedis}</p>
			</td>
		</tr>
	</table>

	<hr />

	<img src="<spring:url value="./images/deer.png"/>" width="200">
</body>
</html>
