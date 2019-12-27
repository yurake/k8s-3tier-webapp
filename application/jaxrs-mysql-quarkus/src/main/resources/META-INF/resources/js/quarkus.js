root = location.href;
url = root + 'mysql';

$(function () {
	$("#response").html("Response Values");

	$("#insert").click(function () {
		$.ajax({
			type: 'post',
			url: url + '/insert',
			contentType: 'application/JSON',
			dataType: 'JSON',
			scriptCharset: 'utf-8',
			success: function (data) {
				const resp = JSON.stringify(data, null, 4)
				console.log(resp);
				$("#response").html(resp);
			},
			error: function (data) {
				console.log(data);
				alert("Server Error. Pleasy try again.");
			}
		});
	})

	$("#select").click(function () {
		$.ajax({
			type: 'post',
			url: url + '/select',
			contentType: 'application/JSON',
			dataType: 'JSON',
			scriptCharset: 'utf-8',
			success: function (data) {
				const resp = JSON.stringify(data, null, 4)
				console.log(resp);
				$("#response").html(resp);
			},
			error: function (data) {
				console.log(data);
				alert("Server Error. Pleasy try again.");
			}
		});
	})
})
