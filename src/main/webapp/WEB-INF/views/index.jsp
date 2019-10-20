<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>Hello k8s-3tier-webapp</title>
<link rel="icon" type="image/png" sizes="16x16" href="./images/icon-16x16.png" />
<link href='<spring:url value="./resources/css/style.css"/>'
	rel="stylesheet" />
<script type="text/javascript"
	src='<spring:url value="./resources/js/app.js"/>'></script>
</head>

<body bgcolor=white>

	<h1 id="title" class="color1">Hello k8s-3tier-webapp</h1>

	<button onclick="changeColor()">Change Color</button>

	<p>To prove that they work, you can execute either of the following links:</p>

	<table>
		<tr>
			<td><b>Target</b></td>
			<td><b>CREATE</b></td>
			<td><b>SELECT</b></td>
			<td><b>DELETE</b></td>
		</tr>
		<tr>
			<td>Memcached</td>
			<td><a href="SetMemcached">Set Memcached</a></td>
			<td><a href="GetMemcached">Get Memcached</a></td>
			<td></td>
		</tr>
		<tr>
			<td>RabbitMQ</td>
			<td><a href="PutRabbitmq">Put RabbitMQ</a></td>
			<td><a href="GetRabbitmq">Get RabbitMQ</a></td>
			<td></td>
		</tr>
		<tr>
			<td>RabbitMQ Consumer</td>
			<td><a href="PutRabbitmqConsumer">Put RabbitMQ Consumer</a></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td>MySQL</td>
			<td><a href="InsertMysql">Insert MySQL</a></td>
			<td><a href="SelectMysql">Select MySQL</a></td>
			<td><a href="DeleteMysql">Delete MySQL</a></td>
		</tr>
	</table>

	<hr />

	<p><a href="docs">About k8s-3tier-webapp</a></p>

	<img src="<spring:url value="./images/deer.png"/>" width="200">
</body>
</html>
