findUnfilledSeat();
var currentSeat = 0;

function findUnfilledSeat() {

	var jsonData = JSON.stringify({
		"inviterId":JSON.parse(localStorage.getItem('userId')),
		"flightReservationId": localStorage.getItem("flightReservationId"),
		"selectedSeats": JSON.parse(localStorage.getItem('selectedSeats'))
	});

	$.ajax({
		type: 'POST',
		contentType: "application/json",
		url: "/passengers/unfilled",
		dataType: "json",
		data: jsonData,
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: fillTable,
		error: function(data) {
			alert(data);
		}
	});
}

function fillTable(data) {
	var passengersDiv = $('#passengersDiv');
	passengersDiv.empty();

	var passengerDiv = "";

	if (data.currentSeat != null) {
		currentSeat = data.currentSeat;

		passengerDiv += '<h2>Seat: ' + currentSeat.seatNumber +'</h2>';
		passengerDiv += '<select id=selectUser>';

		$.each(data.posibleUsers, function(index, posUser) {
			var possUserOpt = '<option id="selUser_' + posUser.id + '">' + posUser.username + '</option>';

			passengerDiv += possUserOpt;
		});

		passengerDiv += '<option id="selUser_0">Other</option>';
		passengerDiv += '</select>';

		//TODO: prikazati tabelu (formu) samo ako je selektovano Other

		passengerDiv += '<div id="other_user_form"><table>'
		passengerDiv += '<tr><td>Firstname:</td><td><input id="other_user_firstname" type="text"></td></tr>'
		passengerDiv += '<tr><td>Lastname:</td><td><input id="other_user_lastname" type="text"></td></tr>'
		passengerDiv += '<tr><td>Passport:</td><td><input id="other_user_passport" type="text"></td></tr>'
		passengerDiv += '<tr><td>&nbsp;</td></tr></table></div>'

		passengersDiv.append(passengerDiv);
		passengersDiv.append('<button onclick="back();">Back</button>');
		passengersDiv.append('<button onclick="next();">Next</button>');
		
		$('#selectUser').on('change', function(e) {
			var isOther = $(this).val();
			if (isOther == "Other") {
				$("#other_user_form").show();
			} else {
				$("#other_user_form").hide();
			}
		});
	} else {
		passengersDiv.append('<button onclick="finish();">Finish</button>');
		
		var flightAndHotel = localStorage.getItem('flightAndHotel');
		if(flightAndHotel == null || flightAndHotel == -1){
			passengersDiv.append('<button onclick="hotels();">Hotels</button>');		
		}
		var flightAndCar = localStorage.getItem('flightAndCar');
		if(flightAndCar == null || flightAndCar == -1){
			passengersDiv.append('<button onclick="rentACar();">RentACar</button>');
		}
	}
}

function back() {
	window.location.href = "friends.html";
}

function finish() {
	var flightAndHotel = localStorage.getItem('flightAndHotel') != null ? localStorage.getItem('flightAndHotel') : -1;
	var flightAndCar = localStorage.getItem('flightAndCar') != null ? localStorage.getItem('flightAndCar') : -1;
	
	$.ajax({
		type: "GET",
		url: "/users/sendMailReservation/"+localStorage.getItem("flightReservationId")+"/"+ flightAndHotel +"/"+ flightAndCar,
		dataType: "json",
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: function() {
			localStorage.removeItem('flightAndHotel');
			localStorage.removeItem('flightAndCar');
		},
		error: function() {
		}
	});
	
	window.location.href = "index.html";
}

function hotels() {
	localStorage.setItem('flightAndHotel', -1);
	window.location.href = "hotels.html";
}

function rentACar() {
	localStorage.setItem('flightAndCar', -1);
	window.location.href = "rentacar.html";
}

function next() {
	var x = document.getElementById("selectUser").selectedIndex;
	var y = document.getElementById("selectUser").options;
	var userId = y[x].id.substring(8);
	var jsonData = null;

	if (userId == 0) {
		jsonData = JSON.stringify({
			"flightReservationId": localStorage.getItem("flightReservationId"),
			"seatId": currentSeat.id,
			"firstname": document.getElementById("other_user_firstname").value,
			"lastname": document.getElementById("other_user_lastname").value,
			"passport": document.getElementById("other_user_passport").value
		});
	} else {
		jsonData = JSON.stringify({
			"flightReservationId": localStorage.getItem("flightReservationId"),
			"seatId": currentSeat.id,
			"userId": userId
		});
	}

	$.ajax({
		type: "POST",
		contentType: "application/json",
		data: jsonData,
		url: "/passengers/reserve",
		dataType: "json",
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: function() {
		},
		error: function() {
		},
		async: false
	});

	window.location.href = "passengers.html";
}