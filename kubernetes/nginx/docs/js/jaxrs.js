var root = location.href;
var url = root + "quarkus";
var kafkaurl = url + "/kafka/";
var memcachedurl = url + "/memcached/";
var redisurl = url + "/redis/";
var rabbitmqurl = url + "/rabbitmq/";
var activemqurl = url + "/activemq/";
var hazelcasturl = url + "/hazelcast/";
var mysqlurl = url + "/mysql/";
var postgresurl = url + "/postgres/";
var mongodburl = url + "/mongodb/";
var cassandraurl = url + "/cassandra/";
var initialvalue = "Response Values";
var set = "set";
var put = "put";
var get = "get";
var post = "post";
var publish = "publish";
var insert = "insert";
var select = "select";
var delt = "delete";
var respkafka = "#respkafka";
var respmemcached = "#respmemcached";
var respredis = "#respredis";
var resprabbitmq = "#resprabbitmq";
var respactivemq = "#respactivemq";
var respcachehazelcast = "#respcachehazelcast";
var respqueuehazelcast = "#respqueuehazelcast";
var respmysql = "#respmysql";
var resppostgres = "#resppostgres";
var respmongodb = "#respmongodb";
var respcassandra = "#respcassandra";
let isopenkafka = false;
let isopenredis = false;
let isopenrabbitmq = false;
let isopenactivemq = false;
let isopenhazelcast = false;

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
  $(respcassandra).html(initialvalue);

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
      $(respid).html(resp.responseText + "\nstatus: " + resp.status);
    });
  }

  function dispMsgFromJson(type, url, respid) {
    $.ajax({
      type: type,
      url: url,
      cache: false,
      timeout: 10000,
    }).always(function (data, status, xhr) {
      console.log(data);
      $(respid).html(data.fullmsg + "\nstatus: " + xhr.status);
    });
  }

  function dispMsgFromJsonArray(type, url, respid) {
    $.ajax({
      type: type,
      url: url,
      cache: false,
      timeout: 10000,
    }).always(function (data, status, xhr) {
      var resparray = "";
      for (i = 0; i < data.length; i++) {
        if (data[i] != null) {
          resparray += data[i].fullmsg + "\n";
        }
      }
      $(respid).html(resparray + "\nstatus: " + xhr.status);
    });
  }

  function subscribeWebSocketRedis(svc) {
    if (isopenredis) {
      console.log("Already connected.");
    } else {
      ws = new WebSocket("ws://k8s.3tier.webapp/quarkus/" + svc + "/subscribe");
      isopenredis = true;
      console.log("Connection to server opened.");
      ws.onopen = function () {
        ws.onmessage = function (receive) {
          $(respredis).text(receive.data);
        };
      };
    }
  }

  function subscribeWebSocketRabbitmq(svc) {
    if (isopenrabbitmq) {
      console.log("Already connected.");
    } else {
      ws = new WebSocket("ws://k8s.3tier.webapp/quarkus/" + svc + "/subscribe");
      isopenrabbitmq = true;
      console.log("Connection to server opened.");
      ws.onopen = function () {
        ws.onmessage = function (receive) {
          $(resprabbitmq).text(receive.data);
        };
      };
    }
  }

  function subscribeWebSocketActivemq(svc) {
    if (isopenactivemq) {
      console.log("Already connected.");
    } else {
      ws = new WebSocket("ws://k8s.3tier.webapp/quarkus/" + svc + "/subscribe");
      isopenactivemq = true;
      console.log("Connection to server opened.");
      ws.onopen = function () {
        ws.onmessage = function (receive) {
          $(respactivemq).text(receive.data);
        };
      };
    }
  }

  function subscribeWebSocketHazelcast(svc) {
    if (isopenhazelcast) {
      console.log("Already connected.");
    } else {
      ws = new WebSocket("ws://k8s.3tier.webapp/quarkus/" + svc + "/subscribe");
      isopenhazelcast = true;
      console.log("Connection to server opened.");
      ws.onopen = function () {
        ws.onmessage = function (receive) {
          $(respcachehazelcast).text(receive.data);
        };
      };
    }
  }

  function stopSweSocketRedis() {
    if (isopenredis) {
      ws.close();
      console.log("Connection to server closed.");
      isopenredis = false;
      $(respredis).html(initialvalue);
    } else {
      console.log("Already closed.");
    }
  }

  function stopSweSocketRabbitmq() {
    if (isopenrabbitmq) {
      ws.close();
      console.log("Connection to server closed.");
      isopenrabbitmq = false;
      $(resprabbitmq).html(initialvalue);
    } else {
      console.log("Already closed.");
    }
  }

  function stopSweSocketActivemq() {
    if (isopenactivemq) {
      ws.close();
      console.log("Connection to server closed.");
      isopenactivemq = false;
      $(respactivemq).html(initialvalue);
    } else {
      console.log("Already closed.");
    }
  }

  function stopSweSocketHazelcast() {
    if (isopenhazelcast) {
      ws.close();
      console.log("Connection to server closed.");
      isopenhazelcast = false;
      $(respcachehazelcast).html(initialvalue);
    } else {
      console.log("Already closed.");
    }
  }

  $("#getkafka").click(function () {
    dispMsgFromString(get, kafkaurl + get, respkafka);
  });

  $("#publishkafka").click(function () {
    dispMsgFromJson(post, kafkaurl + publish, respkafka);
  });

  $("#subscribekafka").click(function () {
    if (isopenkafka) {
      console.log("Already connected.");
    } else {
      console.log("Connection to server opened.");
      isopenkafka = true;
      sse = new EventSource(kafkaurl + "subscribe");
      sse.onmessage = function (event) {
        const resp = event.data;
        console.log(resp);
        document.getElementById("respkafka").innerHTML = resp;
      };
    }
  });

  $("#stopkafka").click(function () {
    if (isopenkafka) {
      console.log("Connection to server closed.");
      sse.close();
      isopenkafka = false;
      $(respkafka).html(initialvalue);
    }
  });

  $("#setmemcached").click(function () {
    dispMsgFromJson(post, memcachedurl + set, respmemcached);
  });

  $("#getmemcached").click(function () {
    dispMsgFromJson(get, memcachedurl + get, respmemcached);
  });

  $("#putredis").click(function () {
    dispMsgFromJson(post, redisurl + put, respredis);
  });

  $("#getredis").click(function () {
    dispMsgFromJsonArray(get, redisurl + get, respredis);
  });

  $("#subscriberedis").click(function () {
    subscribeWebSocketRedis("redis");
  });

  $("#stopredis").click(function () {
    stopSweSocketRedis();
  });

  $("#publishredis").click(function () {
    dispMsgFromJson(post, redisurl + publish, respredis);
  });

  $("#putrabbitmq").click(function () {
    dispMsgFromJson(post, rabbitmqurl + put, resprabbitmq);
  });

  $("#getrabbitmq").click(function () {
    dispMsgFromJson(get, rabbitmqurl + get, resprabbitmq);
  });

  $("#publishrabbitmq").click(function () {
    dispMsgFromJson(post, rabbitmqurl + publish, resprabbitmq);
  });

  $("#subscriberabbitmq").click(function () {
    subscribeWebSocketRabbitmq("rabbitmq");
  });

  $("#stoprabbitmq").click(function () {
    stopSweSocketRabbitmq();
  });

  $("#putactivemq").click(function () {
    dispMsgFromJson(post, activemqurl + put, respactivemq);
  });

  $("#getactivemq").click(function () {
    dispMsgFromJson(get, activemqurl + get, respactivemq);
  });

  $("#publishactivemq").click(function () {
    dispMsgFromJson(post, activemqurl + publish, respactivemq);
  });

  $("#subscribeactivemq").click(function () {
    subscribeWebSocketActivemq("activemq");
  });

  $("#stopactivemq").click(function () {
    stopSweSocketActivemq();
  });

  $("#setcachehazelcast").click(function () {
    dispMsgFromJson(post, hazelcasturl + "setcache", respcachehazelcast);
  });

  $("#getcachehazelcast").click(function () {
    dispMsgFromJsonArray(get, hazelcasturl + "getcache", respcachehazelcast);
  });

  $("#publishhazelcast").click(function () {
    dispMsgFromJson(post, hazelcasturl + publish, respcachehazelcast);
  });

  $("#subscribehazelcast").click(function () {
    subscribeWebSocketHazelcast("hazelcast");
  });

  $("#stophazelcast").click(function () {
    stopSweSocketHazelcast();
  });

  $("#putqueuehazelcast").click(function () {
    dispMsgFromJson(post, hazelcasturl + "putqueue", respqueuehazelcast);
  });

  $("#getqueuehazelcast").click(function () {
    dispMsgFromJson(get, hazelcasturl + "getqueue", respqueuehazelcast);
  });

  $("#insertmysql").click(function () {
    dispMsgFromJson(post, mysqlurl + insert, respmysql);
  });

  $("#selectmysql").click(function () {
    dispMsgFromJsonArray(get, mysqlurl + select, respmysql);
  });

  $("#deletemysql").click(function () {
    dispMsgFromString(post, mysqlurl + delt, respmysql);
  });

  $("#insertpostgres").click(function () {
    dispMsgFromJson(post, postgresurl + insert, resppostgres);
  });

  $("#selectpostgres").click(function () {
    dispMsgFromJsonArray(get, postgresurl + select, resppostgres);
  });

  $("#deletepostgres").click(function () {
    dispMsgFromString(post, postgresurl + delt, resppostgres);
  });

  $("#insertmongodb").click(function () {
    dispMsgFromJson(post, mongodburl + insert, respmongodb);
  });

  $("#selectmongodb").click(function () {
    dispMsgFromJsonArray(get, mongodburl + select, respmongodb);
  });

  $("#deletemongodb").click(function () {
    dispMsgFromString(post, mongodburl + delt, respmongodb);
  });

  $("#insertcassandra").click(function () {
    dispMsgFromJson(post, cassandraurl + insert, respcassandra);
  });

  $("#selectcassandra").click(function () {
    dispMsgFromJsonArray(get, cassandraurl + select, respcassandra);
  });

  $("#deletecassandra").click(function () {
    dispMsgFromString(post, cassandraurl + delt, respcassandra);
  });
})
