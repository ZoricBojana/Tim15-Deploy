$(document).ready(function() {
	getUsers();
	alertify.set('notifier', 'position', 'top-right');

});

$(document).on('click', '.cancelRR', function(e) {
	var rrId = $(this).attr('id').substring(8);
	var userId = JSON.parse(localStorage.getItem('currentUser')).id;

	alertify.confirm('Cancel reservation', 'Are you sure?', function() {
		cancelRoomReservation(userId, rrId, function() {
			alertify.notify('Reservation canceled');
			getMyRoomReservations(fillRoomReservations);
		});
	}, function() {
		alertify.notify("Canceled");
	});

});

function getUsers() {
	$.ajax({
		url : "/users",
		type : "GET",
		dataType : 'json',
		success : fillUsersTable,
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function cancelRoomReservation(userId, rrId, callback) {

	$
			.ajax({
				url : "users/" + userId + "/roomReservations/" + rrId,
				type : "DELETE",
				contentType : 'application/json',
				dataType : 'json',
				success : callback,
				beforeSend : function(xhr) {
					/* Authorization header */
					xhr.setRequestHeader("Authorization", "Bearer "
							+ getJwtToken());
				},
				statusCode : {
					422 : function() {
						alertify
								.error("The deadline for canceling the reservation has expired");
					}
				}
			});
}

function getMyRoomReservations(callback) {

	$.ajax({
		url : "/users/" + JSON.parse(localStorage.getItem('currentUser')).id
				+ "/roomReservations",
		type : "GET",
		dataType : 'json',
		success : callback,
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function fillRoomReservations(data) {

	var reservation_list = data == null ? [] : (data instanceof Array ? data
			: [ data ]);

	reservation_list.sort((a, b) => (a.reservationDate > b.reservationDate ? -1 : 1));
	
	var rrDiv = $('#roomReservationsDiv');
	rrDiv.empty();
	var counter = 0;

	$.each(reservation_list, function(index, reservation) {
		// alert(JSON.stringify(reservation));
		var reservationDiv = $('<div class="rrDiv" id="rrDiv_' + counter
				+ '" style="bottom:' + (55 - counter * 45) + '%; top:'
				+ (3 + counter * 45) + '%;"' + '></div>');
		reservationDiv.append('Hotel: ' + reservation.room.hotelName + ', '
				+ reservation.room.hotelAddress + ' </br>');
		reservationDiv.append('Room number: ' + reservation.room.roomNumber
				+ ' </br>');
		reservationDiv.append('Room capacity: ' + reservation.room.roomCapacity
				+ ' </br>');
		reservationDiv.append('Reservation date: '
				+ reservation.reservationDate.substring(0, 10) + ' </br>');
		reservationDiv.append('Check in: '
				+ reservation.checkInDate.substring(0, 10) + ' </br>');
		reservationDiv.append('Check out: '
				+ reservation.checkOutDate.substring(0, 10) + ' </br></br>');
		reservationDiv.append('Price: ' + reservation.totalPrice
				+ '&#8364;</br>');

		var today = new Date();
		today.setDate(today.getDate() + 2);
		var checkIn = new Date(reservation.checkInDate);

		var checkOut = new Date(reservation.checkOutDate);
		if (today.getTime() <= checkIn.getTime()){
			reservationDiv.append('<button class="cancelRR" id="cancelRR'
					+ reservation.id + '">Cancel reservation</button>');}
		if (today.getTime() > checkOut.getTime()){
			if(reservation.rate == null){
				reservationDiv.append('<div align="right" class="rateRDiv" id="rateRDiv' + reservation.id + '" style="display:inline-block;float:right;"></div>');
			}else {
				reservationDiv.append('<h2 style="float:right;">Rate: ' + reservation.rate.rate + '</h2>');
			}
		}

		counter++;
		rrDiv.append(reservationDiv);
	});
	
	$('.rateRDiv').rate();
	
	$(".rateRDiv").on("change", function(ev, data){
			var iden = $(this).attr('id').substring(8);
		    //console.log(data.from, data.to);
			var rate = data.to;
			var sendData = {};
			sendData["reservationId"] = iden;
			sendData["rate"] = rate;
			
			var jsonData = JSON.stringify(sendData);
			
			$.ajax({
				url : "/room/rate",
				type : "POST",
				contentType: 'application/json',
				data: jsonData,
				dataType : 'json',
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
				},
				success : function(data){
					getMyRoomReservations(fillRoomReservations);
				},
				error : function(response) {
					alert("Something went wrong");
				}
			});
			
	});


}

function getMyVehicleReservations() {

	$.ajax({
		url : "/vehicleReservations/"
				+ JSON.parse(localStorage.getItem('currentUser')).id,
		type : "GET",
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillVehicleReservations,
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function fillVehicleReservations(data) {

	var reservation_list = data == null ? [] : (data instanceof Array ? data
			: [ data ]);

	var vrDiv = $('#vehicleReservationsDiv');
	vrDiv.empty();
	var counter = 0;

	$.each(reservation_list, function(index, reservation) {
		var reservationDiv = $('<div class="vrDiv" id="vrDiv_' + counter
				+ '" style="bottom:' + (55 - counter * 45) + '%; top:'
				+ (3 + counter * 45) + '%;"' + '></div>');
		reservationDiv.append('Rent-a-car: '
				+ reservation.vehicle.vehicleRacName + ', '
				+ reservation.vehicle.vehicleRacAddress + ' </br>');
		reservationDiv.append('Vehicle type: '
				+ reservation.vehicle.vehicleType + ' </br>');
		//reservationDiv.append('Vehicle capacity: '
			//	+ reservation.vehicle.passengerNumber + ' </br>');
		// reservationDiv.append('Reservation date: '
		// + reservation.reservationDate.substring(0, 10) + ' </br>');
		reservationDiv.append('Check in: '
				+ reservation.checkInDate.substring(0, 10) + ' </br>');
		reservationDiv.append('Check out: '
				+ reservation.checkOutDate.substring(0, 10) + ' </br></br>');
		reservationDiv.append('Price: ' + reservation.totalPrice
				+ '&#8364;</br>');

		var today = new Date();
		today.setDate(today.getDate() + 2);
		var checkIn = new Date(reservation.checkInDate);
		var checkOut = new Date(reservation.checkOutDate);
		if (today.getTime() <= checkIn.getTime()) {
			reservationDiv.append('<button class="cancelVR" id="cancelVR'
					+ reservation.id + '">Cancel reservation</button>');
		}

		if (today.getTime() > checkOut.getTime()){
			if(reservation.rate == null){
				reservationDiv.append('<div align="right" class="rateDiv" id="rateDiv' + reservation.id + '" style="display:inline-block;float:right;"></div>');
			}else {
				reservationDiv.append('<h2 style="float:right;">Rate: ' + reservation.rate.rate + '</h2>');
			}
		}

		counter++;
		vrDiv.append(reservationDiv);
	});


	
$('.rateDiv').rate();
	
$(".rateDiv").on("change", function(ev, data){
		var iden = $(this).attr('id').substring(7);
	    //console.log(data.from, data.to);
		var rate = data.to;
		var sendData = {};
		sendData["reservationId"] = iden;
		sendData["rate"] = rate;
		
		var jsonData = JSON.stringify(sendData);
		
		$.ajax({
			url : "/vehicle/rate",
			type : "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType : 'json',
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
			},
			success : function(data){
				getMyVehicleReservations();
			},
			error : function(response) {
				alert("Something went wrong");
			}
		});
		
});
	
	$(document).on('click', '.cancelVR', function(e) {
		var vrId = $(this).attr('id').substring(8);
		var userId = JSON.parse(localStorage.getItem('currentUser')).id;

		alertify.confirm('Cancel reservation', 'Are you sure?', function() {
			cancelVehicleReservation(vrId);
			alertify.notify("Reservation canceled");
		}, function() {
			alertify.notify("Canceled");
		});

	});

}

function cancelVehicleReservation(vrId) {
	$
			.ajax({
				url : "vehicleReservations/" + vrId,
				type : "DELETE",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Bearer "
							+ getJwtToken());
				},
				success : getMyVehicleReservations,
				statusCode : {
					422 : function() {
						alertify
								.error("The deadline for canceling the reservation has expired");
					}
				}
			});
}

function getMyFlightReservations() {

	$.ajax({
		url : "/flightReservation/" + JSON.parse(localStorage.getItem('currentUser')).id,
		type : "GET",
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillFlightReservations,
		error : function(jqXHR, textStatus, errorThrown) {
			alert(errorThrown);
		}
	});
}

function fillFlightReservations(data) {

	var reservation_list = data == null ? [] : (data instanceof Array ? data
			: [ data ]);

	var frDiv = $('#flightReservationsDiv');
	frDiv.empty();
	var counter = 0;

	$.each(reservation_list, function(index, reservation) {
		var reservationDiv = $('<div class="frDiv" id="frDiv_' + counter
				+ '" style="bottom:' + (55 - counter * 45) + '%; top:'
				+ (3 + counter * 45) + '%;"' + '></div>');
		reservationDiv.append('Airline: ' + reservation.flight.flightAirlineName + ', '
				+ reservation.flight.flightAirlineAddress + ' </br>');
		//reservationDiv.append('Reservation date: '
		//		+ reservation.reservationDate.substring(0, 10) + ' </br>');
		reservationDiv.append('Check in: '
				+ reservation.checkInDate.substring(0, 10) + ' </br>');
		reservationDiv.append('Check out: '
				+ reservation.checkOutDate.substring(0, 10) + ' </br></br>');
		reservationDiv.append('Price: ' + reservation.totalPrice
				+ '&#8364;</br>');

		var today = new Date();
		today.setDate(today.getDate() + 2);
		var checkIn = new Date(reservation.checkInDate);
		var checkOut = new Date(reservation.checkOutDate);
		if (today.getTime() <= checkIn.getTime()){
			reservationDiv.append('<button class="cancelFR" id="cancelFR'
					+ reservation.id + '">Cancel reservation</button>');
		}
		if (today.getTime() > checkOut.getTime()){
			if(reservation.rate == null){
				reservationDiv.append('<div align="right" class="rateFDiv" id="rateFDiv' + reservation.id + '" style="display:inline-block;float:right;"></div>');
			}else {
				reservationDiv.append('<h2 style="float:right;">Rate: ' + reservation.rate.rate + '</h2>');
			}
		}

		counter++;
		frDiv.append(reservationDiv);
	});
	
$('.rateFDiv').rate();
	
	$(".rateFDiv").on("change", function(ev, data){
			var iden = $(this).attr('id').substring(8);
		    //console.log(data.from, data.to);
			var rate = data.to;
			var sendData = {};
			sendData["reservationId"] = iden;
			sendData["rate"] = rate;
			
			var jsonData = JSON.stringify(sendData);
			
			$.ajax({
				url : "/flight/rate",
				type : "POST",
				contentType: 'application/json',
				data: jsonData,
				dataType : 'json',
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
				},
				success : function(data){
					getMyFlightReservations();
				},
				error : function(response) {
					alert("Something went wrong");
				}
			});
			
	});

	
	$(document).on('', '.cancelFR', function(e) {
		var frId = $(this).attr('id').substring(8);
		var userId = JSON.parse(localStorage.getItem('currentUser')).id;

		alertify.confirm('Cancel reservation', 'Are you sure?', function() {
			cancelFlightReservation(frId);
			alertify.notify("Reservation canceled");
		}, function() {
			alertify.notify("Canceled");
		});

	});

}


function cancelFlightReservation(vrId) {
	/*$
			.ajax({
				url : "vehicleReservations/" + vrId,
				type : "DELETE",
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
				},
				success : getMyVehicleReservations,
				statusCode : {
					422 : function() {
						alertify
								.error("The deadline for canceling the reservation has expired");
					}
				}
			});*/
}

function fillUsersTable(data) {
	var users = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#usersTable').empty();
	$('#usersTable')
			.append(
					'<tr><th>Username</th><th>First name</th><th>Last name</th><th>Email</th><th>User type</th></tr>');
	$.each(users, function(index, user) {

		var tr = $('<tr></tr>');
		tr.append('<td>' + user.username + '</td>' + '<td>' + user.firstName
				+ '</td>' + '<td>' + user.lastName + '</td>' + '<td>'
				+ user.email + '</td>');

		if (user.type == undefined) {
			tr.append('<td>' + 'registered user' + '</td>');
		} else if (user.type !== null) {
			tr.append('<td>' + user.type.toLowerCase() + ' admin' + '</td>');
		} else {
			tr.append('<td>' + ' registered user' + '</td>');
		}

		$('#usersTable').append(tr);

	});

}

function getMyProfileData() {
	$("#myprofilediv").empty();

	$.ajax({
		type : 'GET',
		url : "/users/myprofile",
		dataType : "json",
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillProfileData,
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function fillProfileData(data) {
	$("#myprofilediv").empty();
	var cont = $('#myprofilediv');
	cont
			.append('<img style="position:absolute; top:10%; left:7%;" src="../images/user-profile.png" height = 40% width= 25%>');
	var form = $('<form id="formChangeProfile"></form>');
	var tab = $('<table id="profileTable" align="center" style="position:absolute; top:10%; left:42%;"></table>');

	tab.append('<tr><td>Username:</td><td><input name="username" value='
			+ data.username + ' readonly></td></tr>');
	tab
			.append('<tr><td>First Name:</td><td><input name="name" class="changefield" value='
					+ data.firstName + ' readonly></td></tr>');
	tab
			.append('<tr><td>Last Name:</td><td><input name="lastName" class="changefield" value='
					+ data.lastName + ' readonly></td></tr>');
	tab.append('<tr><td>Email:</td><td><input name="email" value=' + data.email
			+ ' readonly></td></tr>');
	tab
			.append('<tr><td>Phone number:</td><td><input name="phoneNumber" class="changefield" value='
					+ (data.telephoneNumber == null ? "/"
							: data.telephoneNumber) + ' readonly></td></tr>');
	tab
			.append('<tr><td>City:</td><td><input name="city" class="changefield" value='
					+ (data.city == null ? "/" : data.city)
					+ ' readonly></td></tr>');
	tab
			.append('<tr><td>&nbsp;</td></tr><tr><td></td><td align="right" ><button type="button" id="editProfile">Edit Profile</button></td></tr>');
	form.append(tab);
	cont.append(form);
	cont
			.append('<button id="changepswbtn" style="position:absolute; top:86%; left:71%;">Change password</button>')

	$('#editProfile').click(function(e) {
		e.preventDefault();
		$("#editProfile").attr("disabled", true);
		$('.changefield').removeAttr('readonly');
		;
		editBtn();

	});

	$('#changepswbtn').click(function(e) {
		openCity(event, 'changepswdiv');
	});

	function editBtn() {
		form
				.append('<input  style="position:absolute; top: 74%; left: 75%;" type="submit" id="doEdit" value="Save changes">');

		$(document).on(
				'submit',
				'#formChangeProfile',
				function(e) {

					e.preventDefault();
					var formData = getFormData("#formChangeProfile");
					var jsonData = JSON.stringify(formData);
					$.ajax({
						type : 'PUT',
						url : "/users",
						dataType : "json",
						contentType : "application/json",
						data : jsonData,
						beforeSend : function(xhr) {
							/* Authorization header */
							xhr.setRequestHeader("Authorization", "Bearer "
									+ getJwtToken());
						},
						success : function(data) {
							getMyProfileData(data);
							alertify.notify("Changes saved!")
						},
						error : function(xhr, status, error) {
							alertify.alert("Error", xhr.responseText);
						}

					});
				});
	}
}

$(document)
		.on(
				'submit',
				'#changepswform',
				function(e) {

					e.preventDefault();
					var formData = getFormData("#changepswform");
					var jsonData = JSON.stringify(formData);
					$
							.ajax({
								type : 'POST',
								url : "/auth/change-password",
								dataType : "json",
								contentType : "application/json",
								data : jsonData,
								beforeSend : function(xhr) {
									/* Authorization header */
									xhr.setRequestHeader("Authorization",
											"Bearer " + getJwtToken());
								},
								success : function() {
									alertify.alert("Success",
											"Password is changed.");
									if (JSON.parse(localStorage
											.getItem('currentUser')).passwordChanged == false) {
										adminPswChanged();
									}
									$('#oldpsw').val("");
									$('#newpsw').val("");
								},
								statusCode : {
									403 : function() {
										alertify.alert('Error',
												'Old password is not correct!');
									}
								}
							});
				});

function adminPswChanged() {
	// ovde ide da prebaci na admin homepage
	// izmeni currentUser da je pswchanged true
	// izmeni u localstorage homepage
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	currentUser.passwordChanged = true;
	localStorage.setItem('currentUser', JSON.stringify(currentUser));

	if (currentUser.authorities[0].authority === "ROLE_ADMINRAC") {

		localStorage.setItem("userHomepage", "homePageRAC.html");

	} else if (currentUser.authorities[0].authority === "ROLE_ADMINHOTEL") {

		localStorage.setItem("userHomepage", "homePageHotel.html");

	} else if (data.authorities[0].authority === "ROLE_ADMINAIRLINE") {

		localStorage.setItem("userHomepage", "homePageAirline.html");
	}

	window.location = localStorage.getItem('userHomepage');

}

function showPswchangeFunction() {
	var x = document.getElementById("newpsw");
	var y = document.getElementById("oldpsw");
	if (x.type === "password") {
		x.type = "text";
		y.type = "text";
	} else {
		x.type = "password";
		y.type = "password";
	}
}
