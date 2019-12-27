root = location.href;
url = root + 'mysql';

$(function () {
	$("#response").html("Response Values");

	$("#insert").click(function () {
		$.ajax({
			type: 'post',
			url: url,
			contentType: 'application/JSON',
			dataType: 'JSON',
			scriptCharset: 'utf-8',
			success: function (data) {
				console.log(data);
				// $("#response").html(JSON.stringify(data));
				for (var i in data) {
					$("#response").html("<li>" + data[i].success.responseText + "</li>");
				}
			},
			error: function (data) {
				console.log(data);
				alert("Server Error. Pleasy try again.");
			}
		});
	})

	$("#select").click(function () {
		$.ajax({
			type: 'get',
			url: url,
			contentType: 'application/JSON',
			dataType: 'JSON',
			scriptCharset: 'utf-8',
			success: function (data) {
				console.log(data);
				// $("#response").html(JSON.stringify(data));
								for (var i in data) {
					$("#response").html("<li>" + data[i].success.responseText + "</li>");
				}
			},
			error: function (data) {
				console.log(data);
				alert("Server Error. Pleasy try again.");
			}
		});
	})
})
