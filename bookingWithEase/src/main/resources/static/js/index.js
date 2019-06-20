$(document).ready(function(e) {
	// alert(sessionStorage.getItem('openLoginForm'));
	if (sessionStorage.getItem('openLoginForm') == "true") {
		sessionStorage.setItem('openLoginForm', false);
		login_clicked();
	}
});

$(document).on('click', '#loginbutton', function(e) {
	e.preventDefault();
	window.location.href = "login.html";
});

$(document).on('click', '#new_company', function(e) {
	e.preventDefault();
	window.location.href = "companyRegistration.html";
});

$(document).on('click', '#new_admin', function(e) {
	e.preventDefault();
	window.location.href = "adminRegistration.html";
});

$(document).on('click', '#users', function(e) {
	e.preventDefault();
	window.location.href = "users.html";
});

$(document).on('click', '#airlines', function(e) {
	e.preventDefault();
	window.location.href = "airlines.html";
});

$(document).on('click', '#hotels', function(e) {
	e.preventDefault();
	window.location.href = "hotels.html";
});

$(document).on('click', '#rent-a-car', function(e) {
	e.preventDefault();
	window.location.href = "rentacar.html";
});

$(document).on('click', '#vehicles', function(e) {
	e.preventDefault();
	window.location.href = "vehicles.html";
});

$(document).on('click', '#flights', function(e) {
	e.preventDefault();
	window.location.href = "flights.html";
});

$(document).on('click', '#cancel_login', function(e) {
	e.preventDefault();
	$('.main-box').css('opacity', '0.7');
	$('#transbox-login').css('opacity', '0');
});

function homepage() {
	window.location.href = "index.html";
}

function login_clicked() {
	if (window.location.href != "http://localhost:8080/index.html") {
		sessionStorage.setItem('openLoginForm', true);
		window.location.href = "index.html";
	}

	$('.main-box').css('opacity', '0');
	$('#transbox-login').css('opacity', '0.9');
}

function login_cancel() {
	// window.location.href = "login.html";
	$('.main-box').css('opacity', '0.7');
	$('#transbox-login').css('opacity', '0');
}

function register_clicked() {
	window.location.href = "registration.html";
}

function homepage_clicked() {
	var homepage = localStorage.getItem('userHomepage');
	if (homepage != null) {
		window.location.href = homepage;
	} else {
		alert("Error happened");
	}
}

function company_registration() {

}

function admin_registration() {

}
