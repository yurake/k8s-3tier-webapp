root = location.href;
url = root + 'quarkus';
kafkaurl = url + '/kafka';
memcachedurl = url + '/memcached';
redisurl = url + '/redis';
rabbitmqurl = url + '/rabbitmq';
activemqurl = url + '/activemq';
hazelcasturl = url + '/hazelcast';
mysqlurl = url + '/mysql';
postgresurl = url + '/postgres';
mongodburl = url + '/mongodb';
isOpenOnce = false;
initialvalue = "Response Values"

$(function () {
	$("#respkafka").html(initialvalue);
	$("#respmemcached").html(initialvalue);
	$("#respredis").html(initialvalue);
	$("#resprabbitmq").html(initialvalue);
	$("#respactivemq").html(initialvalue);
	$("#respcachehazelcast").html(initialvalue);
	$("#respqueuehazelcast").html(initialvalue);
	$("#respmysql").html(initialvalue);
	$("#resppostgres").html(initialvalue);
	$("#respmongodb").html(initialvalue);

	var sse;

	$("#getkafka").click(function () {
		$.ajax({
			type: 'get',
			url: kafkaurl + '/get',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respkafka").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respkafka").html(resp);
		})
	})

	$("#publishkafka").click(function () {
		$.ajax({
			type: 'post',
			url: kafkaurl + '/publish',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respkafka").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respkafka").html(resp);
		})
	})

	$("#subscribekafka").click(function (event) {
		if (isOpenOnce) {
			console.log("Already connected.");
		} else {
			console.log("Connection to server opened.");
			isOpenOnce = true;
			sse = new EventSource(kafkaurl + '/subscribe')
			sse.onmessage = function (event) {
				const resp = event.data;
				console.log(resp);
				document.getElementById("respkafka").innerHTML = resp;
			};
		}
	})

	$("#stopkafka").click(function () {
		if (isOpenOnce) {
			console.log("Connection to server closed.");
			sse.close();
			isOpenOnce = false;
			$("#respkafka").html(initialvalue);
		}
	})

	$("#setmemcached").click(function () {
		$.ajax({
			type: 'post',
			url: memcachedurl + '/set',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmemcached").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmemcached").html(resp);
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
			$("#respmemcached").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmemcached").html(resp);
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
			$("#respredis").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respredis").html(resp);
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
			$("#respredis").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respredis").html(resp);
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
			$("#respredis").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respredis").html(resp);
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
			$("#resprabbitmq").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#resprabbitmq").html(resp);
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
			$("#resprabbitmq").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#resprabbitmq").html(resp);
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
			$("#resprabbitmq").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#resprabbitmq").html(resp);
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
			$("#respactivemq").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respactivemq").html(resp);
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
			$("#respactivemq").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respactivemq").html(resp);
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
			$("#respactivemq").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respactivemq").html(resp);
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
			$("#respcachehazelcast").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respcachehazelcast").html(resp);
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
			$("#respcachehazelcast").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respcachehazelcast").html(resp);
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
			$("#respcachehazelcast").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respcachehazelcast").html(resp);
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
			$("#respqueuehazelcast").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respqueuehazelcast").html(resp);
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
			$("#respqueuehazelcast").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respqueuehazelcast").html(resp);
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
			$("#respmysql").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmysql").html(resp);
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
			$("#respmysql").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmysql").html(resp);
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
			$("#respmysql").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmysql").html(resp);
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
			$("#resppostgres").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#resppostgres").html(resp);
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
			$("#resppostgres").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#resppostgres").html(resp);
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
			$("#resppostgres").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#resppostgres").html(resp);
		})
	})

	$("#insertmongodb").click(function () {
		$.ajax({
			type: 'post',
			url: mongodburl + '/insert',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmongodb").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmongodb").html(resp);
		})
	})

	$("#selectmongodb").click(function () {
		$.ajax({
			type: 'get',
			url: mongodburl + '/select',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmongodb").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmongodb").html(resp);
		})
	})

	$("#deletemongodb").click(function () {
		$.ajax({
			type: 'post',
			url: mongodburl + '/delete',
			contentType: 'application/json',
			scriptCharset: 'utf-8',
		}).done(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmongodb").html(resp);
		}).fail(function (data) {
			const resp = JSON.stringify(data, null, 4)
			console.log(resp);
			$("#respmongodb").html(resp);
		})
	})
})
