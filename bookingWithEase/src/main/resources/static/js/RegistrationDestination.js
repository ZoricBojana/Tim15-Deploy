$(document).on('submit', '#destRegForm', function(e) {

	e.preventDefault();

	var formData = getFormData("#destRegForm");
	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/destination",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		success : function(response) {
			alert("Airport saved :)");
			window.location.href = "destination.html";
		},
		error : function(response) {
			alert("Something went wrong! :(");
		}
	});

});
