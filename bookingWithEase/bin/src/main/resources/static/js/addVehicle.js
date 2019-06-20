$(document).on('submit', '#addVehicleForm', function(e) {
	
	e.preventDefault();
	
	var formData = getFormData("#addVehicleForm");
	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/vehicles",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		success : function(response) {
			alert("Vehicle saved :)");
			window.location.href = "vehicles.html";
		},
		error : function(response) {
			alert("Something went wrong! :(");
		}
	});

});
