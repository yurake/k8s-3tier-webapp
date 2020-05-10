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
			const resp = JSON.parse(JSON.stringify(data, null, 4));
			console.log(resp);
			$(respid).html(resp.responseText + '\nstatus: ' + resp.status);
		})
	}

	function dispMsgFromJson(type, url, respid) {
		$.ajax({
			type: type,
			url: url,
			cache: false,
			timeout: 10000,
		}).always(function (data, textStatus, jqXHR) {
			console.log(data);
			$(respid).html(data.fullmsg + '\nstatus: ' + jqXHR.status);
		})
	}

	function dispMsgFromJsonArray(type, url, respid) {
		$.ajax({
			type: type,
			url: url,
			cache: false,
			timeout: 10000,
		}).always(function (data, textStatus, jqXHR) {
			var resparray = "";
			for (i = 0; i < data.length; i++) {
				if (data[i] != null) {
					resparray += data[i].fullmsg + '\n';
				}
			}
			$(respid).html(resparray + jqXHR.status);
		})
	}

	$("#getkafka").click(function () {
		dispMsgFromString(get, kafkaurl + get, respkafka);
	})

	$("#publishkafka").click(function () {
		dispMsgFromJson(post, kafkaurl + publish, respkafka);
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

	$("#putredis").click(function () {
		dispMsgFromJson(post, redisurl + put, respredis);
	})

	$("#getredis").click(function () {
		dispMsgFromJsonArray(get, redisurl + get, respredis);
	})

	$("#publishredis").click(function () {
		dispMsgFromJson(post, redisurl + publish, respredis);
	})

	$("#putrabbitmq").click(function () {
		dispMsgFromJson(post, rabbitmqurl + put, resprabbitmq);
	})

	$("#getrabbitmq").click(function () {
		dispMsgFromJson(get, rabbitmqurl + get, resprabbitmq);
	})

	$("#publishrabbitmq").click(function () {
		dispMsgFromJson(post, rabbitmqurl + publish, resprabbitmq);
	})

	$("#putactivemq").click(function () {
		dispMsgFromJson(post, activemqurl + put, respactivemq);
	})

	$("#getactivemq").click(function () {
		dispMsgFromJson(get, activemqurl + get, respactivemq);
	})

	$("#publishactivemq").click(function () {
		dispMsgFromJson(post, activemqurl + publish, respactivemq);
	})

	$("#setcachehazelcast").click(function () {
		dispMsgFromJson(post, hazelcasturl + 'setcache', respcachehazelcast);
	})

	$("#getcachehazelcast").click(function () {
		dispMsgFromJsonArray(get, hazelcasturl + 'getcache', respcachehazelcast);
	})

	$("#publishhazelcast").click(function () {
		dispMsgFromJson(post, hazelcasturl + publish, respcachehazelcast);
	})

	$("#putqueuehazelcast").click(function () {
		dispMsgFromJson(post, hazelcasturl + 'putqueue', respqueuehazelcast);
	})

	$("#getqueuehazelcast").click(function () {
		dispMsgFromJson(get, hazelcasturl + 'getqueue', respqueuehazelcast);
	})

	$("#insertmysql").click(function () {
		dispMsgFromJson(post, mysqlurl + insert, respmysql);
	})

	$("#selectmysql").click(function () {
		dispMsgFromJsonArray(get, mysqlurl + select, respmysql);
	})

	$("#deletemysql").click(function () {
		dispMsgFromString(post, mysqlurl + delt, respmysql);
	})

	$("#insertpostgres").click(function () {
		dispMsgFromJson(post, postgresurl + insert, resppostgres);
	})

	$("#selectpostgres").click(function () {
		dispMsgFromJsonArray(get, postgresurl + select, resppostgres);
	})

	$("#deletepostgres").click(function () {
		dispMsgFromString(post, postgresurl + delt, resppostgres);
	})

	$("#insertmongodb").click(function () {
		dispMsgFromJson(post, mongodburl + insert, respmongodb);
	})

	$("#selectmongodb").click(function () {
		dispMsgFromJsonArray(get, mongodburl + select, respmongodb);
	})

	$("#deletemongodb").click(function () {
		dispMsgFromString(post, mongodburl + delt, respmongodb);
	})
})
