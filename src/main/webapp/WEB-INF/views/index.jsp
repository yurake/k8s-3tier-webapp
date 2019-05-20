<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>Hello k8s-3tier-webapp</title>
<link rel="icon" type="image/png" sizes="16x16" href="icon-16x16.png?" />
<link href='<spring:url value="/resources/css/style.css"/>'
	rel="stylesheet" />
<script type="text/javascript"
	src='<spring:url value="/resources/js/app.js"/>'></script>
</head>

<body bgcolor=white>

	<h1 id="title" class="color1">Hello k8s-3tier-webapp</h1>

	<button onclick="changeColor()">Change Color</button>

	<p>To prove that they work, you can execute either of the following
		links:
	<table>
		<tr>
			<td><b>Target</b></td>
			<td><b>CREATE</b></td>
			<td><b>SELECT</b></td>
			<td><b>DELETE</b></td>
		</tr>
		<tr>
			<td>Memcached</td>
			<td><a href="SetCache">Set Cache</a></td>
			<td><a href="GetCache">Get Cache</a></td>
			<td></td>
		</tr>
		<tr>
			<td>RabbitMQ</td>
			<td><a href="PutMQ">Put MQ</a></td>
			<td><a href="GetMQ">Get MQ</a></td>
			<td></td>
		</tr>
		<tr>
			<td>RabbitMQ Batch</td>
			<td><a href="PutMQBatch">Put MQ</a></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>MySQL</td>
			<td><a href="InsertDB">Insert DB</a></td>
			<td><a href="SelectDB">Select DB</a></td>
			<td><a href="DeleteDB">Delete DB</a></td>
		</tr>
	</table>

	<hr />

	<img alt="boraji.com" src="<spring:url value="/images/deer.png"/>"
		width="200">
</body>
</html>
