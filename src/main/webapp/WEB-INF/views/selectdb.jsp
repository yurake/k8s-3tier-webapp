<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>Hello k8s-3tier-webapp</title>
</head>
<body bgcolor=white>

	<table>
		<tr>
			<td>
				<h1>selectdb</h1>
			</td>
		</tr>
		<c:forEach var="allMessage" items="${allMessageList}">
			<tr>
				<td><c:out value="${allMessage}" /></td>
			</tr>
		</c:forEach>
	</table>

	<hr />

	<img alt="boraji.com" src="<spring:url value="/images/deer.png"/>"
		width="200">
</body>
</html>