$(document).on('submit', '#registrationform', function(e) {

	e.preventDefault();

	if (!validateRegForm()) {
		return;
	}

	var formData = getFormData("#registrationform");
	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/users/registration",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function() {
			window.location.href = "index.html";
		},
		statusCode : {
			409 : function() {
				alertify.alert('Error', 'This email is already taken!');
			},
			412 : function() {
				alertify.alert('Error', 'Passwords do not match!');
			},
			422 : function() {
				alertify.alert('Error', 'Please enter all required fields');
			}
		}
	});

});

function validateRegForm() {
	if ($('#registrationpassword').val() === $('#registrationconfpassword')
			.val()) {
		if ($('#registrationpassword').val().length > 5) {
			return true;
		} else {
			alertify.alert("Error", "Password is too short!");
			return false;
		}
	} else {
		alertify.alert("Error", "Password and confirmation password do not match!");
		return false;
	}
}

function showPswRegFunction() {
	var x = document.getElementById("registrationpassword");
	var y = document.getElementById("registrationconfpassword");
	if (x.type === "password") {
		x.type = "text";
		y.type = "text";
	} else {
		x.type = "password";
		y.type = "password";
	}
}

$(document).on('click', '#cancel_registration', function(e) {
	window.location.href = "index.html";
});