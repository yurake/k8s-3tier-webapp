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

	function dispMsg(type, url, respid) {
		$.ajax({
			type: type,
			url: url,
			cache: false,
			timeout: 10000,
		}).always(function (data) {
			const resp = JSON.stringify(data, null, 4)
			const respj = JSON.parse(resp)
			console.log(resp);
			$(respid).html(respj.responseText + '\nstatus: ' + respj.status);
		})
	}

	$("#getkafka").click(function () {
		dispMsg(get, kafkaurl + get, respkafka);
	})

	$("#publishkafka").click(function () {
		dispMsg(post, kafkaurl + publish, respkafka);
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
		dispMsg(post, memcachedurl + set, respmemcached);
	})

	$("#getmemcached").click(function () {
		dispMsg(get, memcachedurl + get, respmemcached);
	})

	$("#setredis").click(function () {
		dispMsg(post, redisurl + set, respredis);
	})

	$("#getredis").click(function () {
		dispMsg(get, redisurl + get, respredis);
	})

	$("#publishredis").click(function () {
		dispMsg(post, redisurl + publish, respredis);
	})

	$("#putrabbitmq").click(function () {
		dispMsg(post, rabbitmqurl + put, resprabbitmq);
	})

	$("#getrabbitmq").click(function () {
		dispMsg(get, rabbitmqurl + get, resprabbitmq);
	})

	$("#publishrabbitmq").click(function () {
		dispMsg(post, rabbitmqurl + publish, resprabbitmq);
	})

	$("#putactivemq").click(function () {
		dispMsg(post, activemqurl + put, respactivemq);
	})

	$("#getactivemq").click(function () {
		dispMsg(get, activemqurl + get, respactivemq);
	})

	$("#publishactivemq").click(function () {
		dispMsg(post, activemqurl + publish, respactivemq);
	})

	$("#putcachehazelcast").click(function () {
		dispMsg(post, hazelcasturl + 'putcache', respcachehazelcast);
	})

	$("#getcachehazelcast").click(function () {
		dispMsg(get, hazelcasturl + 'getcache', respcachehazelcast);
	})

	$("#publishhazelcast").click(function () {
		dispMsg(post, hazelcasturl + publish, respcachehazelcast);
	})

	$("#putqueuehazelcast").click(function () {
		dispMsg(post, hazelcasturl + 'putqueue', respqueuehazelcast);
	})

	$("#getqueuehazelcast").click(function () {
		dispMsg(get, hazelcasturl + 'getqueue', respqueuehazelcast);
	})

	$("#insertmysql").click(function () {
		dispMsg(post, mysqlurl + insert, respmysql);
	})

	$("#selectmysql").click(function () {
		dispMsg(get, mysqlurl + select, respmysql);
	})

	$("#deletemysql").click(function () {
		dispMsg(post, mysqlurl + delt, respmysql);
	})

	$("#insertpostgres").click(function () {
		dispMsg(post, postgresurl + insert, resppostgres);
	})

	$("#selectpostgres").click(function () {
		dispMsg(get, postgresurl + select, resppostgres);
	})

	$("#deletepostgres").click(function () {
		dispMsg(post, postgresurl + delt, resppostgres);
	})

	$("#insertmongodb").click(function () {
		dispMsg(post, mongodburl + insert, respmongodb);
	})

	$("#selectmongodb").click(function () {
		dispMsg(get, mongodburl + select, respmongodb);
	})

	$("#deletemongodb").click(function () {
		dispMsg(post, mongodburl + delt, respmongodb);
	})
})
