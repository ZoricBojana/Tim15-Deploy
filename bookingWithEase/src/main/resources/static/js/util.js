$(document)
		.ready(
				function() {

					var currentUser = JSON.parse(localStorage
							.getItem('currentUser'));
					if (currentUser == null) {
						var nav_ul = $('#nav_ul');
						nav_ul
								.append('<li style="border-left:1px solid gray;"><a href="javascript:login_clicked()">Log in</li></a>');
						nav_ul
								.append('<li><a href="javascript:register_clicked()">Register</li></a>');
					} else {
						var nav_ul = $('#nav_ul');
						nav_ul
								.append('<li style="border-left:1px solid gray;"><a href="javascript:homepage_clicked()">'
										+ '<img src="../images/user.png" width=20 height= 20>&nbsp;&nbsp;'
										+ currentUser.username + '</li></a>');
						nav_ul
								.append('<li><a href="javascript:logout()">Log out</li></a>');

					}

				});

function openCity(evt, cityName) {
	if(cityName === "roomReservationsDiv")
		getMyRoomReservations(fillRoomReservations);
	if(cityName === "myprofilediv")
		getMyProfileData();
	if(cityName === "vehicleReservationsDiv")
		getMyVehicleReservations();
	if(cityName === "flightReservationsDiv")
		getMyFlightReservations();
	var i, tabcontent, tablinks;
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(
				" active", "");
	}
	$('#' + cityName).slideToggle(1000);
	if(evt != null && evt != undefined)
		evt.currentTarget.className += " active";
}

const arrayToObject = (array, keyField) =>
array.reduce((obj, item) => {
  obj[item[keyField]] = item
  return obj
}, {})


function checkCompany()
{	
	// Ako adminu nije dodeljena kompanija ne moze nista da radi, sakri opcije
	if(JSON.parse(localStorage.getItem('currentUser')).company == null)
	{	
		$('.tab').css("display", "none");
	}
}

function getFormData(formId) {
	var formData = {};
	var s_data = $(formId).serializeArray();

	for (var i = 0; i < s_data.length; i++) {
		var record = s_data[i];
		formData[record.name] = record.value;
	}

	return formData;
}

function check_dates(start, end, future)
{	
	if(start === undefined || end === undefined)
	{
		return true;
	}
	
	var now = new Date();
	if(future){
	if(start.getTime() >= end.getTime() || (start.getTime() < now.getTime() && future ))
	{
		return false;
	}
	}
	if(!future)
	{	
		if(start.getTime() >= end.getTime() || start.getTime() > now.getTime())
		return false;
	}
	
	return true;
	
}

function util_login() {
	sessionStorage.setItem('openLoginForm', true);
	window.location.href = "index.html";
}

function util_login() {
	sessionStorage.setItem('openLoginForm', true);
	window.location.href = "index.html";
}