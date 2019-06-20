function showPswFunction() {
	var x = document.getElementById("loginpsw");
	if (x.type === "password") {
		x.type = "text";
	} else {
		x.type = "password";
	}
}

$(document).on('submit', '#loginform', function(e) {

	e.preventDefault();

	var formData = getFormData("#loginform");
	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/auth/login",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		success : function(response) {
			saveToken(response);
		},
		statusCode : {
			401 : function() {
				alertify.alert('Error', 'Bad credentials');
			},
			403 : function() {
				alertify.alert('Error', 'Account is not enabled');
			},
			422 : function() {
				alertify.error('Please enter all required fields');
			}
		}
	});

});

function saveToken(data) {
	console.log(data);
	if (localStorage) {
		console.log(data.accessToken);
		setJwtToken(data.accessToken);

		$
				.ajax({
					type : 'GET',
					url : "/users/myprofile",
					dataType : "json",
					beforeSend : function(xhr) {
						/* Authorization header */
						xhr.setRequestHeader("Authorization", "Bearer "
								+ getJwtToken());
					},
					success : function(data) {
						if (localStorage) {
							localStorage.setItem("userId", data.id);
							localStorage.setItem("currentUser", JSON
									.stringify(data));
							//	alert("saved " + localStorage.getItem('currentUser'));
							if (data.company != null) {
								localStorage.setItem("userCompanyId",
										data.company.id);
							}
						}
						if (data.authorities[0].authority === "ROLE_ADMINRAC") {

							if (data.passwordChanged) {
								localStorage.setItem("userHomepage",
										"homePageRAC.html");
								window.location.href = "homePageRAC.html";

							} else {
								localStorage.setItem("userHomepage",
								"changePassword.html");
								window.location.href = "changePassword.html";
							}
						} else if (data.authorities[0].authority === "ROLE_ADMINHOTEL") {

							if (data.passwordChanged) {
								localStorage.setItem("userHomepage",
										"homePageHotel.html");
								window.location.href = "homePageHotel.html";

							} else {
								
								localStorage.setItem("userHomepage",
								"changePassword.html");
								window.location.href = "changePassword.html";
							}
						} else if (data.authorities[0].authority === "ROLE_ADMINAIRLINE") {

							if (data.passwordChanged) {
								localStorage.setItem("userHomepage",
										"homePageAirline.html");
								window.location.href = "homePageAirline.html";

							} else {
						
								localStorage.setItem("userHomepage",
								"changePassword.html");
								window.location.href = "changePassword.html";
							}
						} else if (data.authorities[0].authority === "ROLE_ADMIN") {
							localStorage.setItem("userHomepage",
									"homePageAdmin.html");
							window.location.href = "homePageAdmin.html";
						} else if (data.authorities[0].authority === "ROLE_USER") {
							localStorage.setItem("userHomepage",
									"userProfile.html");
							window.location.href = "homePageUser.html";
						}
					},
					error : function(data) {
						alert(data);
					}
				});
	} else {
		alert("Sorry, your browser does not support session storage.");
	}
}