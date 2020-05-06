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
respkafka = "#respkafka";
respmemcached = "#respmemcached";
respredis = "#respredis";
resprabbitmq = "#resprabbitmq";
respactivemq = "#respactivemq";
respcachehazelcast = "#respcachehazelcast";
respqueuehazelcast = "#respqueuehazelcast";
respmysql = "#respmysql";
resppostgres = "#resppostgres";
respmongodb = "#respmongodb";

$(function () {
	$(respkafka).html(initialvalue);
	$(respmemcached).html(initialvalue);
	$(respredis).html(initialvalue);
	$(resprabbitmq).html(initialvalue);
	$(respactivemq).html(initialvalue);
	$(respcachehazelcast).html(initialvalue);
	$(respqueuehazelcast).html(initialvalue);
	$(respmysql).html(initialvalue);
	$(resppostgres).html(initialvalue);
	$(respmongodb).html(initialvalue);

	var sse;

	function dispMsgFromString(type, url, respid) {
		$.ajax({
			type: type,
			url: url,
			cache: false,
			timeout: 10000,
		}).always(function (data) {
			console.log(data);
			const resp = JSON.stringify(data, null, 4)
			const respj = JSON.parse(resp)
			console.log(resp);
			$(respid).html(respj.responseText + '\nstatus: ' + respj.status);
		})
	}

	function dispMsgFromJson(type, url, respid) {
		$.ajax({
			type: type,
			url: url,
			cache: false,
			timeout: 10000,
		}).always(function (data, textStatus, jqXHR) {
			console.log(jqXHR.status);
			console.log(data);
			$(respid).html(data.fullmsg + '\nstatus: ' + jqXHR.status);
		})
	}

	$("#getkafka").click(function () {
		dispMsgFromString(get, kafkaurl + get, respkafka);
	})

	$("#publishkafka").click(function () {
		dispMsgFromString(post, kafkaurl + publish, respkafka);
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
			$(respkafka).html(initialvalue);
		}
	})

	$("#setmemcached").click(function () {
		dispMsgFromJson(post, memcachedurl + set, respmemcached);
	})

	$("#getmemcached").click(function () {
		dispMsgFromJson(get, memcachedurl + get, respmemcached);
	})

	$("#setredis").click(function () {
		dispMsgFromString(post, redisurl + set, respredis);
	})

	$("#getredis").click(function () {
		dispMsgFromString(get, redisurl + get, respredis);
	})

	$("#publishredis").click(function () {
		dispMsgFromString(post, redisurl + publish, respredis);
	})

	$("#putrabbitmq").click(function () {
		dispMsgFromString(post, rabbitmqurl + put, resprabbitmq);
	})

	$("#getrabbitmq").click(function () {
		dispMsgFromString(get, rabbitmqurl + get, resprabbitmq);
	})

	$("#publishrabbitmq").click(function () {
		dispMsgFromString(post, rabbitmqurl + publish, resprabbitmq);
	})

	$("#putactivemq").click(function () {
		dispMsgFromString(post, activemqurl + put, respactivemq);
	})

	$("#getactivemq").click(function () {
		dispMsgFromString(get, activemqurl + get, respactivemq);
	})

	$("#publishactivemq").click(function () {
		dispMsgFromString(post, activemqurl + publish, respactivemq);
	})

	$("#putcachehazelcast").click(function () {
		dispMsgFromString(post, hazelcasturl + 'putcache', respcachehazelcast);
	})

	$("#getcachehazelcast").click(function () {
		dispMsgFromString(get, hazelcasturl + 'getcache', respcachehazelcast);
	})

	$("#publishhazelcast").click(function () {
		dispMsgFromString(post, hazelcasturl + publish, respcachehazelcast);
	})

	$("#putqueuehazelcast").click(function () {
		dispMsgFromString(post, hazelcasturl + 'putqueue', respqueuehazelcast);
	})

	$("#getqueuehazelcast").click(function () {
		dispMsgFromString(get, hazelcasturl + 'getqueue', respqueuehazelcast);
	})

	$("#insertmysql").click(function () {
		dispMsgFromString(post, mysqlurl + insert, respmysql);
	})

	$("#selectmysql").click(function () {
		dispMsgFromString(get, mysqlurl + select, respmysql);
	})

	$("#deletemysql").click(function () {
		dispMsgFromString(post, mysqlurl + delt, respmysql);
	})

	$("#insertpostgres").click(function () {
		dispMsgFromString(post, postgresurl + insert, resppostgres);
	})

	$("#selectpostgres").click(function () {
		dispMsgFromString(get, postgresurl + select, resppostgres);
	})

	$("#deletepostgres").click(function () {
		dispMsgFromString(post, postgresurl + delt, resppostgres);
	})

	$("#insertmongodb").click(function () {
		dispMsgFromString(post, mongodburl + insert, respmongodb);
	})

	$("#selectmongodb").click(function () {
		dispMsgFromString(get, mongodburl + select, respmongodb);
	})

	$("#deletemongodb").click(function () {
		dispMsgFromString(post, mongodburl + delt, respmongodb);
	})
})
