root = location.href;
url = root + 'api';
memcachedurl = url + '/memcached';
redisurl = url + '/redis';
rabbitmqurl = url + '/rabbitmq';
hazelcasturl = url + '/hazelcast';
mysqlurl = url + '/mysql';
quarkusurl = root + 'quarkus/mysql';

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

	$("#insertquarkus").click(function () {
		$.ajax({
			type: 'post',
			url: quarkusurl + '/insert',
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

	$("#selectquarkus").click(function () {
		$.ajax({
			type: 'get',
			url: quarkusurl + '/select',
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
