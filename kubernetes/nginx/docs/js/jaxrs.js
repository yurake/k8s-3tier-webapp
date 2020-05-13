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
isopenkafka = false;
isopenredis = false;
isopenrabbitmq = false;
isopenactivemq = false;
isopenhazelcast = false;

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
		if (isopenkafka) {
			console.log("Already connected.");
		} else {
			console.log("Connection to server opened.");
			isopenkafka = true;
			sse = new EventSource(kafkaurl + 'subscribe')
			sse.onmessage = function (event) {
				const resp = event.data;
				console.log(resp);
				document.getElementById("respkafka").innerHTML = resp;
			};
		}
	})

	$("#stopkafka").click(function () {
		if (isopenkafka) {
			console.log("Connection to server closed.");
			sse.close();
			isopenkafka = false;
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

	$("#subscriberedis").click(function (event) {
		if (isopenredis) {
			console.log("Already connected.");
		} else {
			console.log("Connection to server opened.");
			isopenredis = true;
			sse = new EventSource(redisurl + 'subscribe')
			sse.onmessage = function (event) {
				const resp = event.data;
				console.log(resp);
				document.getElementById("respredis").innerHTML = resp;
			};
		}
	})

	$("#stopredis").click(function () {
		if (isopenredis) {
			console.log("Connection to server closed.");
			sse.close();
			isopenredis = false;
			$(respredis).html(initialvalue);
		}
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

	$("#subscriberabbitmq").click(function (event) {
		if (isopenredis) {
			console.log("Already connected.");
		} else {
			console.log("Connection to server opened.");
			isopenredis = true;
			sse = new EventSource(rabbitmqurl + 'subscribe')
			sse.onmessage = function (event) {
				const resp = event.data;
				console.log(resp);
				document.getElementById("resprabbitmq").innerHTML = resp;
			};
		}
	})

	$("#stoprabbitmq").click(function () {
		if (isopenredis) {
			console.log("Connection to server closed.");
			sse.close();
			isopenredis = false;
			$(resprabbitmq).html(initialvalue);
		}
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

	var wsactivemq;

	$("#subscribeactivemq").click(function () {
		if (isopenactivemq) {
			console.log("Already connected.");
		} else {
			wsactivemq = new WebSocket('ws://k8s.3tier.webapp/quarkus/activemq/subscribe');
			isopenactivemq = true;
			console.log("Connection to server opened.");
			wsactivemq.onopen = function (e) {
				wsactivemq.onmessage = function (receive) {
					$(respactivemq).text(receive.data);
				};
			};
		}
	})

	$("#stopactivemq").click(function () {
		if (isopenactivemq) {
			wsactivemq.close();
			console.log("Connection to server closed.");
			isopenactivemq = false;
		} else {
			console.log("Already closed.");
		}
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

	$("#subscribehazelcast").click(function (event) {
		if (isopenhazelcast) {
			console.log("Already connected.");
		} else {
			console.log("Connection to server opened.");
			isopenhazelcast = true;
			sse = new EventSource(hazelcasturl + 'subscribe')
			sse.onmessage = function (event) {
				const resp = event.data;
				console.log(resp);
				document.getElementById("respcachehazelcast").innerHTML = resp;
			};
		}
	})

	$("#stophazelcast").click(function () {
		if (isopenhazelcast) {
			console.log("Connection to server closed.");
			sse.close();
			isopenhazelcast = false;
			$(respcachehazelcast).html(initialvalue);
		}
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
