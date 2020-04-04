root = location.href;
url = root + 'quarkus';
memcachedurl = url + '/memcached';
redisurl = url + '/redis';
rabbitmqurl = url + '/rabbitmq';
activemqurl = url + '/activemq';
hazelcasturl = url + '/hazelcast';
mysqlurl = url + '/mysql';
postgresurl = url + '/postgres';
mongodburl = url + '/mongodb';

$(function () {
	$("#response").html("Response Values");

	$("#setmemcached").click(function () {
		$.ajax({
			type: 'post',
			url: memcachedurl + '/set',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#getmemcached").click(function () {
		$.ajax({
			type: 'get',
			url: memcachedurl + '/get',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#setredis").click(function () {
		$.ajax({
			type: 'post',
			url: redisurl + '/set',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#getredis").click(function () {
		$.ajax({
			type: 'get',
			url: redisurl + '/get',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#publishredis").click(function () {
		$.ajax({
			type: 'post',
			url: redisurl + '/publish',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#putrabbitmq").click(function () {
		$.ajax({
			type: 'post',
			url: rabbitmqurl + '/put',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#getrabbitmq").click(function () {
		$.ajax({
			type: 'get',
			url: rabbitmqurl + '/get',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#publishrabbitmq").click(function () {
		$.ajax({
			type: 'post',
			url: rabbitmqurl + '/publish',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#putactivemq").click(function () {
		$.ajax({
			type: 'post',
			url: activemqurl + '/put',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#getactivemq").click(function () {
		$.ajax({
			type: 'get',
			url: activemqurl + '/get',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#publishactivemq").click(function () {
		$.ajax({
			type: 'post',
			url: activemqurl + '/publish',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#putcachehazelcast").click(function () {
		$.ajax({
			type: 'post',
			url: hazelcasturl + '/putcache',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#getcachehazelcast").click(function () {
		$.ajax({
			type: 'get',
			url: hazelcasturl + '/getcache',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#publishhazelcast").click(function () {
		$.ajax({
			type: 'post',
			url: hazelcasturl + '/publish',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#putqueuehazelcast").click(function () {
		$.ajax({
			type: 'post',
			url: hazelcasturl + '/putqueue',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#getqueuehazelcast").click(function () {
		$.ajax({
			type: 'get',
			url: hazelcasturl + '/getqueue',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#insertmysql").click(function () {
		$.ajax({
			type: 'post',
			url: mysqlurl + '/insert',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#selectmysql").click(function () {
		$.ajax({
			type: 'get',
			url: mysqlurl + '/select',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#deletemysql").click(function () {
		$.ajax({
			type: 'post',
			url: mysqlurl + '/delete',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#insertpostgres").click(function () {
		$.ajax({
			type: 'post',
			url: postgresurl + '/insert',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#selectpostgres").click(function () {
		$.ajax({
			type: 'get',
			url: postgresurl + '/select',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#deletepostgres").click(function () {
		$.ajax({
			type: 'post',
			url: postgresurl + '/delete',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#insertmongodb").click(function () {
		$.ajax({
			type: 'post',
			url: postgresurl + '/insert',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#selectmongodb").click(function () {
		$.ajax({
			type: 'get',
			url: postgresurl + '/select',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})

	$("#deletemongodb").click(function () {
		$.ajax({
			type: 'post',
			url: postgresurl + '/delete',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#response").html(resp);
		})
	})
})
