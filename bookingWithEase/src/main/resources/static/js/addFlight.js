$(document).on('submit', '#addFlightForm', function(e) {

	e.preventDefault();

	var formData = getFormData("#addFlightForm");
	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/flights",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		success : function(response) {
			alert("Flight saved :)");
			window.location.href = "flights.html";
		},
		error : function(response) {
			alert("Something went wrong! :(");
		}
	});

});
