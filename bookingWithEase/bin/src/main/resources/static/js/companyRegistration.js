$(document).on('click', '#cancelCompReg', function(e) {
	e.preventDefault();
	window.location.href = "index.html";
});

$(document).on('click', '#registerCompany', function(e) {
	e.preventDefault();
	registration();
});

function registration() {
	var formData = getFormData("#companyRegForm");

	var validData = Boolean(validateRegistrationData(formData));
	if (validData) {
		var jsonData = JSON.stringify(formData);

		$.ajax({
			url : "/companies",
			type : "POST",
			contentType : "application/json",
			data : jsonData,
			dataType : 'json',
			success : function(response) {
				alert("Company saved :)");
				window.location.href = "index.html";
			},
			error : function(response) {
				alert("Something went wrong! :(");
			}
		});
	} else {
		alert("Please fill in all fields");
	}
}

function validateRegistrationData(formData) {

	var name = formData["name"];
	var address = formData["address"];
	var description = formData["description"];
	var type = formData["cmpType"];
	
	if(name === "" || address === "" || description === "" || type === undefined)
	{
		return false;
	}

	return true;
}