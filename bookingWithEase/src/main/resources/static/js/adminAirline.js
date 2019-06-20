$(document).ready(function() {


	fillAirlineData();

	$('#pageTitle').html('Booking With Ease - <i>' + JSON.parse(localStorage.currentUser).company.name) + "</i>";

	$('#editCompanyForm').on('submit', function(e) {
		e.preventDefault();
		var iden = this.id;

		var formData = {};
		formData["id"] = localStorage.getItem('companyId');
		var s_data = $('#' + this.id).serializeArray();

		for (var i = 0; i < s_data.length; i++) {
			var record = s_data[i];
			if (record.name === "companyId") {
				formData["id"] = record.value;
			} else {
				formData[record.name] = record.value;
			}
		}

		var jsonData = JSON.stringify(formData);
		$.ajax({
			type: 'post',
			url: "/airlines/edit",
			contentType: 'application/json',
			dataType: 'json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
			},
			data: jsonData,
			success: function(data) {
				console.log(data);

				var editedUser = JSON.parse(localStorage.currentUser);
				editedUser.company.name = data.name;
				editedUser.company.address = data.address;
				editedUser.company.latitude = data.latitude;
				editedUser.company.longitude = data.longitude;
				editedUser.company.description = data.description;
				localStorage.setItem("currentUser", JSON.stringify(editedUser));
				$('#pageTitle').html('Booking With Ease - <i>' + JSON.parse(localStorage.currentUser).company.name) + "</i>";
				window.location.href = "homePageAirline.html";
			},
			error: function(data) {
				console.log(data);
			}
		});
	});
});

$(document).on('click', '#logoutClicked', function(e) {
	e.preventDefault();
	window.location.href = "index.html";
})

$(document).on('submit', '#destRegForm', function(e) {
	e.preventDefault();
	var formData = getFormData("#destRegForm");
	formData["airlineId"] = localStorage.getItem('companyId');
	var jsonData = JSON.stringify(formData);
	console.log(jsonData);
	$.ajax({
		url: "/destination",
		type: "POST",
		contentType: "application/json",
		data: jsonData,
		dataType: 'json',
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: function(response) {

			window.location.href = "homePageAirline.html";
		},
		error: function(response) {
			console.log(response)
			console.log("Something went wrong! :(");
		}
	});

});


function findAirline() {

	var airlineId = JSON.parse(localStorage.currentUser).company.id;
	$.ajax({
		type: 'GET',
		url: "/airlines/" + airlineId,
		dataType: "json",
		beforeSend: function(xhr) {
			// Authorization header 
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: fillAirlineData,
		error: function(data) {
			console.log(data);
		}
	});
}


function fillAirlineData() {
	var data = JSON.parse(localStorage.currentUser).company;
	if ($('#companyName') != null) {
		$('#companyName').val(data.name);
		$('#companyAddress').val(data.address);
		$('#companyLatitude').val(data.latitude);
		$('#companyLongitude').val(data.longitude);
		$('#companyDesc').val(data.description);
	}

	localStorage.setItem('companyId', data.id);
}

//ispis


function findDestination() {
	$.ajax({
		type: 'GET',
		url: "/destination/company/" + localStorage.getItem("companyId"),
		dataType: "json",
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: fillTable,
		error: function(data) {
			console.log(data);
		}
	});
}

function fillTable(data) {
	var d_list = data == null ? []
		: (data instanceof Array ? data : [data]);
	var table = $('#destinationTable');
	$('#destinationTable').empty();

	//var cont = $('#help');
	//cont.empty();
	$('#destinationTable').append('<tr><br><br><th></th><th>Name</th><th>Address</th></tr>');
	//cont.append(form);

	$.each(d_list, function(index, destination) {

		var tr = $('<tr></tr>');
		var form = $('<td><form class="formsedit" id="form' + destination.id
			+ '"><input hidden name="ident" value=' + destination.id
			+ ' readonly></form></td><td><input name="name" form="form'
			+ destination.id + '" value="' + destination.name
			+ '"></td><td><input name="address" form="form' + destination.id
			+ '" value="' + destination.address

			+ '"></td><td><input type="submit" form="form' + destination.id
			+ '" id="bform' + destination.id
			+ '"></td><td><button class="delBtns" id="delBtn' + destination.id
			+ '">Delete</button></td>');

		tr.append(form);
		table.append(tr);
	}

	);

	$('.delBtns').on('click', function(e) {
		e.preventDefault();
		var iden = this.id.substring(6);
		console.log(iden);

		$.ajax({
			type: 'delete',
			url: "/destination/" + iden,
			beforeSend: function(xhr) {
				/* Authorization header */
				xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
			},
			success: function(response) {
				// alert("Vehicle deleted :)");
				window.location.href = "homePageAirline.html";
			},
			error: function(data) {
				alert(data);
			}
		});

	});


	$('.formsedit').on('submit', function(e) {
		e.preventDefault();
		var iden = this.id;
		// var formData = getFormData(iden);

		var formData = {};
		var s_data = $('#' + this.id).serializeArray();

		for (var i = 0; i < s_data.length; i++) {
			var record = s_data[i];
			console.log(record);
			if (record.name === "ident") {
				formData["id"] = record.value;
			} else {
				formData[record.name] = record.value;
			}
		}
		formData["airlineId"] = localStorage.getItem('companyId');
		console.log(formData);
		var jsonData = JSON.stringify(formData);
		$.ajax({
			type: 'post',
			url: "/destination/edit",
			contentType: 'application/json',
			dataType: 'json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
			},
			data: jsonData,
			success: findDestination,
			error: function(data) {
				alert(data);
			}
		});
	});


}

//dodavanje letova

$(document).on('submit', '#addFlightForm', function(e) {

	e.preventDefault();

	var formData = getFormData("#addFlightForm");
	//formData["startDestinationId"] = $("#startDestinationDropdown option:selected").val();
	//formData["endDestinationId"] = $("#endDestinationDropdown option:selected").val();
	formData["airlineId"] = localStorage.getItem('companyId');

	var jsonData = JSON.stringify(formData);
	console.log(formData);
	$.ajax({
		url: "/flights",
		type: "POST",
		contentType: "application/json",
		data: jsonData,
		dataType: 'json',
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: function(response) {
			alert("Flight saved :)");
			window.location.href = "homePageAirline.html";
		},
		error: function(response) {
			alert("Something went wrong! :(");
		}
	});

});

function findFlights() {
	$.ajax({
		type: 'GET',
		url: "/flights/airline/" + localStorage.getItem('companyId'),
		dataType: "json",
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: fillTable1,
		error: function(data) {
			alert(data);
		}
	});
}

function fillTable1(data) {

	var f_list = data == null ? []
		: (data instanceof Array ? data : [data]);
	var table = $('#tab-flights');
	$('#tab-flights').empty();
	$('#tab-flights')
		.append(
			'<tr><th>Flight number</th><th>Start destination </th><th>End destination </th><th>Date flight </th><th>Date landing </th><th>Length travel</th><th>Flight duration</th><th> Price ticket</th></tr>');

	$.each(f_list, function(index, flight) {

		var tr = $('<tr></tr>');
		var form = $('<td><form class="formseditf" id="form' + flight.id
			+ '"><input name="ident" value=' + flight.number
			+ ' readonly><input hidden name="idenf" value=' + flight.id + ' readonly>'
			+ '</form></td><td><select id="startDestinationDropdownf' + flight.id
			+ '"name ="startDestinationId"></select></td>'
			+ '<td><select id="endDestinationDropdownf' + flight.id
			+ '"name ="endDestinationId"></select></td>'
			+ '<td><input name="dateFligh" form="form' + flight.id
			+ '" value="' + flight.dateFligh.substring(0, 10)
			+ '"></td><td><input name="dateLand" form="form'
			+ flight.id + '" value="' + flight.dateLand.substring(0, 10)
			+ '"></td><td><input name="lengthTravel" form="form' + flight.id
			+ '" value="' + flight.lengthTravel
			+ '"></td><td><input name="timeTravel" form="form' + flight.id
			+ '" value="' + flight.timeTravel
			+ '"></td><td><input name="priceTicket" form="form' + flight.id
			+ '" value="' + flight.priceTicket
			+ '"></td><td><input type="submit" form="form' + flight.id
			+ '" id="bform' + flight.id
			+ '"></td><td><button class="delBtns1" id="delBtn' + flight.id
			+ '">Delete</button></td>');
		tr.append(form);
		table.append(tr);
		findDestinationByFlightId2(flight);
	});



	$('.delBtns1').on('click', function(e) {
		e.preventDefault();
		var iden = this.id.substring(6);

		$.ajax({
			type: 'delete',
			url: "/flights/" + iden,
			beforeSend: function(xhr) {
				/* Authorization header */
				xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
			},
			success: function(response) {
				// alert("Flight deleted :)");
				window.location.href = "homePageAirline.html";
			},
			error: function(data) {
				alert(data);
			}
		});

	});

	$('.formseditf').on('submit', function(e) {

		e.preventDefault();
		var iden = this.id;


		var formData = {};
		var s_data = $('#' + this.id).serializeArray();

		for (var i = 0; i < s_data.length; i++) {
			var record = s_data[i];
			if (record.name === "idenf") {
				formData["id"] = record.value;
			} else {
				formData[record.name] = record.value;
			}
		}
		var onlyId = iden.split("m")[1];
		formData["startDestinationId"] = $("#startDestinationDropdownf" + onlyId + " option:selected").val();
		formData["startDestination"] = $("#startDestinationDropdownf" + onlyId + " option:selected").text();
		formData["endDestinationId"] = $("#endDestinationDropdownf" + onlyId + " option:selected").val();
		formData["endDestination"] = $("#endDestinationDropdownf" + onlyId + " option:selected").text();
		console.log(formData);
		var jsonData = JSON.stringify(formData);
		$.ajax({
			type: 'post',
			url: "/flights/edit",
			contentType: 'application/json',
			dataType: 'json',

			data: jsonData,
			success: findFlights,
			error: function(data) {
				alert("Error while editing flight");
			}
		});
	});

}




function findDestinationByFlightId() {
	$.ajax({
		type: 'GET',
		url: "/destination/company/" + localStorage.getItem('companyId'),
		dataType: "json",
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: fillDestinationSelect,
		error: function(data) {
			alert(data);
		}
	});
}

function findDestinationByFlightId2(flight) {
	$.ajax({
		type: 'GET',
		url: "/destination/company/" + localStorage.getItem('companyId'),
		dataType: "json",
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: function(data) {
			fillDestinationSelect2(data, flight)
		},
		error: function(data) {
			alert(data);
		}
	});
}

function fillDestinationSelect(data) {
	console.log(data);
	var s = '<option value="-1">Please Select Start destination</option>';
	for (var i = 0; i < data.length; i++) {
		s += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
	}
	$("#startDestinationDropdown").html(s);

	$('#startDestinationDropdown').on('change', function(e) {
		e.preventDefault();
		var iden = $(this).children("option:selected").val();

		var endDestinationId = $("#endDestinationDropdown option:selected").val();

		if (endDestinationId != -1) {
			if (endDestinationId == iden) {
				alert('Start destination is same as end destination!');
				$(this).children("option:selected").change();
			}
		}

	});

	s = '<option value="-1">Please Select End destination</option>';
	for (var i = 0; i < data.length; i++) {
		s += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
	}
	$("#endDestinationDropdown").html(s);

	$('#endDestinationDropdown').on('change', function(e) {
		e.preventDefault();
		var iden = $(this).children("option:selected").val();

		var startestinationId = $("#startDestinationDropdown option:selected").val();

		if (startestinationId != -1) {
			if (startestinationId == iden) {
				$(this).children("option:selected").val(-1).change();
				alert('End destination is same as start destination!');
			}
		}
	});
}

function fillDestinationSelect2(data, flight) {

	var s = '';
	for (var i = 0; i < data.length; i++) {
		if (data[i].address + ' (' + data[i].name + ')' == flight.startD)
			s += '<option selected value="' + data[i].id + '">' + data[i].address + ' (' + data[i].name + ')' + '</option>';
		else
			s += '<option value="' + data[i].id + '">' + data[i].address + ' (' + data[i].name + ')' + '</option>';
	}
	$("#startDestinationDropdownf" + flight.id).html(s);



	s = '';
	for (var i = 0; i < data.length; i++) {
		if (data[i].address + ' (' + data[i].name + ')' == flight.finalD)
			s += '<option selected value="' + data[i].id + '">' + data[i].address + ' (' + data[i].name + ')' + '</option>';
		else
			s += '<option value="' + data[i].id + '">' + data[i].address + ' (' + data[i].name + ')' + '</option>';
	}
	$("#endDestinationDropdownf" + flight.id).html(s);

}


function findQuickReservations() {
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var airlineId = currentUser.company.id;

	$.ajax({
		url: "/quickFlightReservation/" + airlineId + "/quickReservations",
		type: "GET",
		dataType: 'json',
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: fillQuickFlightReservations,
		error: function(response) {
			alert("Something went wrong while getting quick flight reservations! :(");
		}
	});
}

function fillQuickFlightReservations(data) {
	var qfrs = data == null ? [] : (data instanceof Array ? data : [data]);

	var qfrs_dict = arrayToObject(qfrs, "id");
	localStorage.setItem("qfrs", JSON.stringify(qfrs_dict));

	$('#allQfrTable').empty();
	$('#allQfrTable').append('<tr><th>Id</th><th>Flight number</th><th>Seat id</th><th>Date</th><th>Price</th><th>Discount</th><th>Final price</th><th>&nbsp;</th><th>&nbsp;</th></tr>');

	$.each(qfrs, function(index, qfr) {

		var tr = $('<tr></tr>');

		var qfrTr = $('<td>' + qfr.qfrId + '</td>' + '<td id="qfr_' + qfr.qfrId
			+ '">' + qfr.flightNumber + '</td>' + '<td>'
			+ qfr.seatId + '</td>' + '<td>'
			+ qfr.dateFligh.substring(0, 10) + '</td><td>'
			+ qfr.originalPrice + '&#8364;</td><td>' + qfr.discount + '%<td>'
			+ qfr.currentPrice + '&#8364;</td>'
			+ '<td><button class="edit_qfr_btn" id="edit_qfr_' + qfr.qfrId
			+ '">Edit</button></td>'
			+ '</td><td><button class="delete_qfr_btn" id="delete_qfr_'
			+ qfr.qfrId + '">Delete</button></td>');

		tr.append(qfrTr);

		$('#allQfrTable').append(tr);
	});

	$('.delete_qfr_btn').on('click', function(e) {
		e.preventDefault();

		var currentUser = JSON.parse(localStorage.getItem('currentUser'));
		var qfrId = this.id.substring(11);
		var airlineId = currentUser.company.id;

		alertify.confirm('Delete quick flight reservation', 'Are you sure?',
			function() {
				deleteQuickFlightReservation(airlineId, qfrId,
					function(data) {
						alertify.notify("Quick flight reservation deleted!");
						fillQuickFlightReservations(data);
					}
				);
			},
			function() {
				alertify.notify("Canceled");
			}
		);
	});

	$('.edit_qfr_btn').on('click', function(e) {
		e.preventDefault();

		var currentUser = JSON.parse(localStorage.getItem('currentUser'));
		var qfrId = this.id.substring(9);
		var airlineId = currentUser.company.id;

		//getSpecialOffers(hotelId, addSpecialOffersToEditQrr);
		fillEditQfrForm(qfrId);
		openCity(event, 'editQuickFlightReservation');

	});
}

function deleteQuickFlightReservation(airlineId, qfrId, callback) {
	$.ajax({
		url: "/quickFlightReservation/" + airlineId + "/quickReservations/" + qfrId,
		type: "DELETE",
		dataType: 'json',
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: callback,
		error: function(response) {
			alert("Something went wrong while deleting qfr!");
		}
	});
}

function fillEditQrrForm(qfrId) {
	var qrrInfo = JSON.parse(localStorage.getItem('qrrs'))[qrrId];
	// alert(JSON.stringify(qrrInfo));
	$('#qrr-id-edit').val(qrrInfo.id);
	$('#qrr-roomNumber-edit').val(qrrInfo.room.roomNumber);
	$('#qrr-checkIn-edit').val(qrrInfo.checkInDate.substring(0, 10));
	$('#qrr-checkOut-edit').val(qrrInfo.checkOutDate.substring(0, 10));
	$('#qrr-discount-edit').val(qrrInfo.discount);

	var checks = $('.qrr_so_checkbox');

	$.each(checks, function(index, check) {

		var sos = qrrInfo.specialOffers;
		for (var v in sos) {

			if ($(check).val() == sos[v].id) {
				$('#qrr_so_checkbox_' + sos[v].id).prop('checked', true);
			}
		}
	});
}

function fillEditQfrForm(qfrId) {
	var qfrInfo = JSON.parse(localStorage.getItem('qfrs'))[qfrId];

	$('#qfr-id-edit').val(qfrInfo.id);
	$('#qfr-seatId-edit').val(qfrInfo.seatId);
	$('#qfr-discount-edit').val(qfrInfo.discount);
}

function findFlightsSelect(isSeat) {
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var id = currentUser.company.id;

	$.ajax({
		url: "/flights/airline/" + id,
		type: "GET",
		dataType: 'json',
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: function(data) {
			fillFlightsSelect(data, isSeat);
		},
		error: function(response) {
			alert("Something went wrong while deleting qfr!");
		}
	});
}

function fillFlightsSelect(data, isSeat) {
	var flights = data == null ? [] : (data instanceof Array ? data : [data]);
	var select;

	if (isSeat) {
		$('#seat-flight-select').empty();
		select = document.getElementById("seat-flight-select");
	} else {
		$('#qfrAdd-flight-select').empty();
		select = document.getElementById("qfrAdd-flight-select");
	}

	$.each(flights, function(index, flight) {
		var el = document.createElement("option");
		el.textContent = flight.number + "(" + flight.id + ")";
		el.value = flight.id;
		el.id = flight.id;
		select.appendChild(el);
	});

	if (flights == null) {
		alert("No flights");
		return;
	}

	if (isSeat) {
		findSeats();
	} else {
		fillSeats();
	}
}

$(document).on('change', '#qfrAdd-flight-select', function(e) {

	fillSeats();
});

function fillSeats() {
	var flightId = $('#qfrAdd-flight-select').val();

	$.ajax({
		url: "/seats/available/" + flightId,
		type: "GET",
		dataType: 'json',
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: addSeatToQfr,
		error: function(response) {
			alert("Something went wrong! :(");
		}
	});
}

function addSeatToQfr(data) {
	var seats = data == null ? [] : (data instanceof Array ? data : [data]);

	$('#qfr-seats').empty();
	var counter = 0;

	$.each(seats, function(index, seat) {
		counter++;

		var newItem = $('<div align="left"></div');
		newItem.append('<input type="checkbox" class= "qfr_seat_checkbox" name="qfr_seat_checkbox' + seat.id +
			'" value="' + seat.id + '" />' + seat.seatNumber + " " + seat.type + " class");

		$('#qfr-seats').append(newItem);
	});

	if (counter == 0)
		$('#qfr-seats').append("none available");
}

$(document).on('click', '#addQuickFlightReservationBtn', function(e) {
	e.preventDefault();
	var formData = getFormData('#addQuickFlightReservationForm');

	var newFormData = {};
	var seats = [];

	for (var el in formData) {
		if (el.startsWith('qfr_seat_')) {
			seats.push(formData[el]);
		} else {
			newFormData[el] = formData[el];
		}
	}

	newFormData["seats"] = seats;

	addQuickFlightReservations(newFormData, function(data) {
		alertify.notify("Quick Flight Reservation Saved!");
		findFlightsSelect(false);
	});
});

function addQuickFlightReservations(formData, callback) {

	var jsonData = JSON.stringify(formData);

	$.ajax({
		url: "/quickFlightReservation/quickReservations",
		type: "POST",
		dataType: 'json',
		contentType: 'application/json',
		data: jsonData,
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: callback,
		error: function(response) {
			alert("Something went wrong while adding qrr! :(");
		},
		async: false
	});
}

$(document).on('click', '#cancelQuickFlightReservationBtn', function(e) {
	e.preventDefault();
	openCity(event, '');
});

function addSeat() {
	var flightId = $('#seat-flight-select').val();
	var seatType = $('#seatType-select').val();

	var jsonData = JSON.stringify({
		"flightId": flightId,
		"typeClass": seatType
	});

	$.ajax({
		type: 'POST',
		contentType: "application/json",
		url: "/seats/add",
		dataType: "json",
		data: jsonData,
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: drawSeats,
		error: function(data) {
			alert(data);
		}
	});
}

$(document).on('change', '#seat-flight-select', function(e) {

	findSeats();
});

function findSeats() {
	var flightId = $('#seat-flight-select').val();

	$.ajax({
		type: 'GET',
		url: "/seats/flight/" + flightId,
		dataType: "json",
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: drawSeats,
		error: function(data) {
			alert(data);
		}
	});
}

function drawSeats(data) {
	localStorage.removeItem('selectedSeats');

	var seats_list = data == null ? [] : (data instanceof Array ? data : [data]);

	var seatsTableLeft = $('#seatsTableLeft');
	seatsTableLeft.empty();

	var seatTableLeft = "";

	var counter = 0;
	var seatSpliter = 4.0;

	$.each(seats_list, function(index, seat) {
		if (counter % seatSpliter == 0) {
			seatTableLeft += '<tr>';
		}

		seatTableLeft += '<td><button id="seat_' + seat.id + '" class="av_' + seat.available + '_num_' + seat.seatNumber + '_type_' + seat.type + '">' + seat.seatNumber + '</button></td>';

		if (counter % seatSpliter == 3) {
			seatTableLeft += '</tr>';
		}

		counter++;
	});

	seatsTableLeft.append(seatTableLeft);

	$("button[class^='av_true']").on('click', function(e) {
		e.preventDefault();
		var selected_seats_list = localStorage.getItem("selectedSeats") != null ? JSON.parse(localStorage.getItem("selectedSeats")) : [];
		var idSeat = this.id.substring(5);
		var class_name = this.className;

		if (class_name.includes("_selected")) {
			for (var i = 0; i < selected_seats_list.length; i++) {
				if (selected_seats_list[i] === idSeat) {
					selected_seats_list.splice(i, 1);

					var lastIndex = class_name.lastIndexOf("_selected");
					this.className = class_name.substring(0, lastIndex);
					break;
				}
			}
		} else {
			selected_seats_list.push(idSeat);

			this.className = class_name + "_selected";
		}

		localStorage.setItem('selectedSeats', JSON.stringify(selected_seats_list));
	});
}

function editSeats() {
	var jsonData = JSON.stringify({
		"typeClass": $('#seatType-edit-select').val(),
		"selectedSeats": JSON.parse(localStorage.getItem('selectedSeats'))
	});

	$.ajax({
		type: 'POST',
		contentType: "application/json",
		url: "/seats/edit",
		dataType: "json",
		data: jsonData,
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: drawSeats,
		error: function(data) {
			alert(data);
		}
	});
}

function deleteSeats() {
	var jsonData = JSON.stringify({
		"selectedSeats": JSON.parse(localStorage.getItem('selectedSeats'))
	});

	$.ajax({
		type: 'POST',
		contentType: "application/json",
		url: "/seats/delete",
		dataType: "json",
		data: jsonData,
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: drawSeats,
		error: function(data) {
			alert(data);
		}
	});
}


function reports() {
	var airline_id = localStorage.getItem('companyId');

	$.ajax({
		url: "/companies/rate/" + airline_id,
		type: "GET",
		dataType: 'json',
		contentType: "application/json",
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: function(response) {
			$('#average_airline_rate').empty();
			$('#average_airline_rate').append('Average airline rate: ' + response);
		},
		error: function(response) {
			console.log("Something went wrong! :(");
		}
	});

	$.ajax({
		url: "/airlines/flights/rate/" + airline_id,
		type: "GET",
		contentType: "application/json",
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: function(data) {
			fillFlightRate(data);
		},
		error: function(response) {
			console.log("Something went wrong! :(");
		}
	});
	
	//chart();
	showRepDiv()
}

function fillFlightRate(data) {
	var f_r_list = data == null ? [] : (data instanceof Array ? data : [data]);

	var table = $('#average_flight_rate_table');
	$('#average_flight_rate_table').empty();

	$('#average_flight_rate_table').append('<tr><th>Flight number</th><th>Average rate</th></tr>');

	$.each(f_r_list, function(index, flightRate) {

		var tr = $('<tr></tr>');
		var td = $('<td>' + flightRate.flightNumber + '</td><td>' + flightRate.averageRate + '</td>');

		tr.append(td);
		table.append(tr);
	});
}

$(document).on('submit', '#reportDatesForm', function(e) {
	e.preventDefault();
	var formData = getFormData("#reportDatesForm");
	formData["airlineId"] = localStorage.getItem('companyId');

	if (formData["start"] == "" || formData["end"] == "") {
		alert("Set start and end date");
		return;
	}

	var jsonData = JSON.stringify(formData);
	console.log(jsonData);
	$.ajax({
		url: "/airlines/income",
		type: "POST",
		contentType: "application/json",
		data: jsonData,
		dataType: 'json',
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: function(response) {
			document.getElementById('total_income_value').innerHTML = response;
		},
		error: function(response) {
			console.log(response)
			console.log("Something went wrong! :(");
		}
	});
});

$(document).on('change', '#report-chart-select', function(e) {

	showRepDiv();
});

function showRepDiv(){
	var select = $('#report-chart-select').val();
	if(select == "reportDay"){
		$("#show-month-div").show();
		$("#show-year-div").hide();
	}else{
		$("#show-year-div").show();
		$("#show-month-div").hide();
	}
}

$(document).on('change', '#year-chart-id', function(e) {

	chart();
});

$(document).on('change', '#month-chart-id', function(e) {

	chart();
});

function chart(){
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var airlineId = currentUser.company.id;
	
	var typeSearch= $('#report-chart-select').val();	
	var month = typeSearch == "reportDay"? $('#month-chart-id').val().substring(5) : 0;
	var year = typeSearch == "reportDay"? $('#month-chart-id').val().substring(0, 4) : $('#year-chart-id').val();

	var jsonData = JSON.stringify({
		"airlineId": airlineId,
		"typeSearch": typeSearch,
		"year": year,
		"month": month,
	});

	$.ajax({
		type: 'POST',
		contentType: "application/json",
		url: "/flightReservation/report",
		dataType: "json",
		data: jsonData,
		beforeSend: function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success: drawChart,
		error: function(data) {
			alert(data);
		}
	});
}

function drawChart(data1) {
	var report_list = data1 == null ? [] : (data1 instanceof Array ? data1 : [data1]);
	
	let myChart = document.getElementById('myChart').getContext('2d');
	
	// Global Options
	Chart.defaults.global.defaultFontFamily = 'Lato';
	Chart.defaults.global.defaultFontSize = 12;
	Chart.defaults.global.defaultFontColor = '#777';

	let massPopChart = new Chart(myChart, {
		type: 'bar', // bar, horizontalBar, pie, line, doughnut, radar, polarArea
		data: {
			labels: report_list[0].type,//['Boston', 'Worcester', 'Springfield', 'Lowell', 'Cambridge', 'New Bedford', 'Boston', 'Worcester', 'Springfield', 'Lowell', 'Cambridge', 'New Bedford'],
			datasets: [{
				label: 'Sales count',
				data: report_list[0].saleCount,/*[
					617594,
					181045,
					153060,
					106519,
					105162,
					181045,
					153060,
					106519,
					105162,
					95072
				],*/
				//backgroundColor:'green',
				/*backgroundColor: [
					'rgba(255, 99, 132, 0.6)',
					'rgba(54, 162, 235, 0.6)',
					'rgba(255, 206, 86, 0.6)',
					'rgba(75, 192, 192, 0.6)',
					'rgba(153, 102, 255, 0.6)',
					'rgba(255, 159, 64, 0.6)',
					'rgba(255, 99, 132, 0.6)'
				],*/
				borderWidth: 1,
				borderColor: '#777',
				hoverBorderWidth: 3,
				hoverBorderColor: '#000'
			}]
		},
		options: {
			title: {
				display: true,
				text: 'Sales report',
				fontSize: 25
			},
			layout: {
				padding: {
					left: 10,
					right: 0,
					bottom: 0,
					top: 0
				}
			},
			tooltips: {
				enabled: true
			}
		}
	});
}