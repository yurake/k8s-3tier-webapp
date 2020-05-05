root = location.href;
url = root + 'quarkus';
kafkaurl = url + '/kafka/';
memcachedurl = url + '/memcached/';
redisurl = url + '/redis/';
rabbitmqurl = url + '/rabbitmq/';
activemqurl = url + '/activemq/';
hazelcasturl = url + '/hazelcast/';
mysqlurl = url + '/mysql/';
postgresurl = url + '/postgres/';
mongodburl = url + '/mongodb/';
isOpenOnce = false;
initialvalue = "Response Values";
set = 'set';
put = 'put';
get = 'get';
post = 'post';
publish = 'publish';
insert = 'insert';
select = 'select';
delt = 'delete';

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

	function dispMsg(type, url) {
		$.ajax({
			type: type,
			url: url,
		}).always(function (data) {
			const resp = JSON.stringify(data, null, 4)
			const respj = JSON.parse(resp)
			console.log(resp);
			$("#respmemcached").html(respj.responseText + '\nstatus: ' + respj.status);
		})
	}

	$("#getkafka").click(function () {
		dispMsg(get, kafkaurl + get);
	})

	$("#publishkafka").click(function () {
		dispMsg(post, kafkaurl + publish);
	})

	$("#subscribekafka").click(function (event) {
		if (isOpenOnce) {
			console.log("Already connected.");
		} else {
			console.log("Connection to server opened.");
			isOpenOnce = true;
			sse = new EventSource(kafkaurl + 'subscribe')
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
		dispMsg(post, memcachedurl + set);
	})

	$("#getmemcached").click(function () {
		dispMsg(get, memcachedurl + get);
	})

	$("#setredis").click(function () {
		dispMsg(post, redisurl + set);
	})

	$("#getredis").click(function () {
		dispMsg(get, redisurl + get);
	})

	$("#publishredis").click(function () {
		dispMsg(post, redisurl + publish);
	})

	$("#putrabbitmq").click(function () {
		dispMsg(post, rabbitmqurl + put);
	})

	$("#getrabbitmq").click(function () {
		dispMsg(get, rabbitmqurl + get);
	})

	$("#publishrabbitmq").click(function () {
		dispMsg(post, rabbitmqurl + publish);
	})

	$("#putactivemq").click(function () {
		dispMsg(post, activemqurl + put);
	})

	$("#getactivemq").click(function () {
		dispMsg(get, activemqurl + get);
	})

	$("#publishactivemq").click(function () {
		dispMsg(post, activemqurl + publish);
	})

	$("#putcachehazelcast").click(function () {
		dispMsg(post, hazelcasturl + 'putcache');
	})

	$("#getcachehazelcast").click(function () {
		dispMsg(get, hazelcasturl + 'getcache');
	})

	$("#publishhazelcast").click(function () {
		dispMsg(post, hazelcasturl + publish);
	})

	$("#putqueuehazelcast").click(function () {
		dispMsg(post, hazelcasturl + 'putqueue');
	})

	$("#getqueuehazelcast").click(function () {
		dispMsg(get, hazelcasturl + 'getqueue');
	})

	$("#insertmysql").click(function () {
		dispMsg(post, mysqlurl + insert);
	})

	$("#selectmysql").click(function () {
		dispMsg(get, mysqlurl + select);
	})

	$("#deletemysql").click(function () {
		dispMsg(post, mysqlurl + delt);
	})

	$("#insertpostgres").click(function () {
		dispMsg(post, postgresurl + insert);
	})

	$("#selectpostgres").click(function () {
		dispMsg(get, postgresurl + select);
	})

	$("#deletepostgres").click(function () {
		dispMsg(post, postgresurl + delt);
	})

	$("#insertmongodb").click(function () {
		dispMsg(post, mongodburl + insert);
	})

	$("#selectmongodb").click(function () {
		dispMsg(get, mongodburl + select);
	})

	$("#deletemongodb").click(function () {
		dispMsg(post, mongodburl + delt);
	})
})
