root = location.href;
url = root + 'api/mysql';

$(function () {
	$("#response").html("Response Values");

	$("#insert").click(function () {
		$.ajax({
			type: 'post',
			url: url + '/insert',
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

	$("#select").click(function () {
		$.ajax({
			type: 'get',
			url: url + '/select',
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
