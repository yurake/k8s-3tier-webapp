<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html>
<head>
<title>Sample "Hello, World" Application</title>
</head>
<body bgcolor=white>

	<table>
		<tr>
			<td>
				<h1>Sample "Hello, World" Application</h1>
			</td>
		</tr>
	</table>

	<p>To prove that they work, you can execute either of the following
		links:
	<table>
		<tr>
			<td>Target</td>
			<td>CREATE</td>
			<td>SELECT</td>
			<td>DELETE</td>
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
			<td><a href="InsertDb">Insert DB</a></td>
			<td><a href="SelectDB">Select DB</a></td>
			<td><a href="DeleteDB">Delete DB</a></td>
		</tr>
	</table>

</body>
</html>