alertify.set('notifier', 'position', 'top-right');
$(document).ready(
		function() {
			hotelId = localStorage.getItem("showRooms");
			if (hotelId !== null) {
				var checkIn = localStorage.getItem('latestHSearchCheckIn');
				var checkOut = localStorage.getItem('latestHSearchCheckOut');

				hotelId = localStorage.getItem("showRooms");

				var hotels = JSON.parse(localStorage.getItem("hotels"));

				var hotel = hotels[hotelId];
				$('#hotel_name_h').empty().append(
						'Hotel: ' + hotel.name + ', ' + hotel.address);

				$('#src-hotel-checkIn').val(checkIn);
				$('#src-hotel-checkOut').val(checkOut);
				$('#rooms_black_btn').css('background-color', 'gray');
				if (checkIn != "" && checkOut != "") {

					$('#rooms_black_btn').css('background-color', 'gray');
					searchRooms(getSearchRoomsData(), fillTableRooms);

				} else {
					getRooms(hotelId);
				}
			}
		});

$(document).on('click', '#room_search_btn', function(e) {
	e.preventDefault();

	if (!validateRoomSearchData()) {
		return;
	}

	searchRooms(getSearchRoomsData(), fillTableRooms);
	$('#info_black_btn').css('background-color', 'black');
	$('#rooms_black_btn').css('background-color', 'black');
	$('#qrr_black_btn').css('background-color', 'black');
});

function validateRoomSearchData() {
	var date1 = $('#src-hotel-checkIn').val();

	var date2 = $('#src-hotel-checkOut').val();

	var now = new Date();
	if (new Date(date1).getTime() < now.getTime()) {
		alertify.alert("Date not valid", "Check in date is past");
		return false;
	}

	if (date2 < date1) {
		alertify.alert("Date not valid",
				"Check out date must be greater than check in date");
		return false;
	}

	var floor = $('#src-hotel-floor').val();
	if (!!floor && !$.isNumeric(floor)) {
		alertify.alert("Invalid input", "Floor field must be a number");
		return false;
	}

	var cap = $('#src-hotel-capacity').val();
	if (!!cap && !$.isNumeric(cap)) {
		alertify.alert("Invalid input", "Capacity field must be a number");
		return false;
	}

	var minp = $('#src-hotel-min').val();
	if (!!minp && !$.isNumeric(minp)) {
		alertify.alert("Invalid input", "Min price field must be a number");
		return false;
	}

	var maxp = $('#src-hotel-max').val();
	if (!!maxp && !$.isNumeric(maxp)) {
		alertify.alert("Invalid input", "Max price field must be a number");
		return false;
	}

	return true;
}

$(document).on('click', '#qrr_search_btn', function(e) {
	e.preventDefault();
	searchQrr(getSearchRoomsData(), function(data) {

		var qrrsMap = arrayToObject(data, "id");
		localStorage.setItem('qrrsMap', JSON.stringify(qrrsMap));
		//
		// alert(JSON.stringify(qrrsMap));

		fillTableQrrs(data);

	});

	$('#info_black_btn').css('background-color', 'black');
	$('#rooms_black_btn').css('background-color', 'black');
	$('#qrr_black_btn').css('background-color', 'black');
});

$(document).on('click', '#info_black_btn', function(e) {
	e.preventDefault();

	$(this).css('background-color', 'gray');
	$('#rooms_black_btn').css('background-color', 'black');
	$('#qrr_black_btn').css('background-color', 'black');

});

$(document).on('click', '#rooms_black_btn', function(e) {
	e.preventDefault();

	$(this).css('background-color', 'gray');
	$('#info_black_btn').css('background-color', 'black');
	$('#qrr_black_btn').css('background-color', 'black');

	clearSerachRoomForm();

	getRooms(hotelId);

});

$(document).on('click', '#qrr_black_btn', function(e) {
	e.preventDefault();

	$(this).css('background-color', 'gray');
	$('#info_black_btn').css('background-color', 'black');
	$('#rooms_black_btn').css('background-color', 'black');

	clearSerachRoomForm();

	searchQrr(getSearchRoomsData(), function(data) {

		var qrrsMap = arrayToObject(data, "id");
		localStorage.setItem('qrrsMap', JSON.stringify(qrrsMap));
		// alert(JSON.stringify(qrrsMap));
		fillTableQrrs(data);
	});

});

$(document).on('change', '.so_check', function(e) {
	var rrData = JSON.parse(localStorage.getItem('rrData'));
	var oldPrice = Number(localStorage.getItem('currentPrice'));

	if (this.checked) {
		var newPrice = oldPrice + parseInt($(this).attr('price'));
		$('#p_price').empty().append(newPrice + '&#8364;');

	} else {
		var newPrice = oldPrice - parseInt($(this).attr('price'));
		$('#p_price').empty().append(newPrice + '&#8364;');
	}

	localStorage.setItem('currentPrice', newPrice);
});

$(document).on(
		'click',
		'#so_confirm_btn',
		function(e) {
			e.preventDefault();
			var soData = getFormData("#so_form");

			var so_list = [];
			$.each(soData, function(index, so) {
				so_list.push(so);
			});
			var rrData = JSON.parse(localStorage.getItem('rrData'));
			rrData['specialOffers'] = so_list;
			closeForm();
			var message = "Check in: " + rrData['checkIn'] + "</br></br>";
			message += "Check out: " + rrData['checkOut'] + "</br></br>";
			message += "Total price: " + localStorage.getItem('currentPrice')
					+ "</br></br>"

			rrData["totalPrice"] = localStorage.getItem('currentPrice');
			alertify.confirm('Booking confirmation', message, function() {
				bookRoom(JSON.stringify(rrData));
			}, function() {
				alertify.notify("Booking canceled");
			});

		});

function getRooms(hotelId) {
// alert("get rooms from showRooms.js called");
	$.ajax({
		url : "/hotels/" + hotelId + "/rooms",
		type : "GET",
		dataType : 'json',
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillTableRooms,
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function getSearchRoomsData() {
	var formData = getFormData('#room_search_form');
	if (formData["floorNumber"] === "")
		formData["floorNumber"] = -11;
	formData["hotelId"] = localStorage.getItem('showRooms');

	localStorage.setItem('latestHSearchCheckIn', formData['checkIn']);
	localStorage.setItem('latestHSearchCheckOut', formData['checkOut']);
	// alert(JSON.stringify(formData));
	return formData;
}

function searchRooms(formData, callback) {
	var checkIn = formData['checkIn'];
	var checkOut = formData['checkOut'];

	if (!check_dates(new Date(checkIn), new Date(checkOut), true)) {
		alertify.notify("Invalid dates!");
		return;
	}

	var jsonData = JSON.stringify(formData);
	// alert(jsonData);
	$.ajax({
		url : "/hotels/rooms",
		type : "POST",
		dataType : 'json',
		contentType : "application/json",
		data : jsonData,
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : callback,
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function searchQrr(formData, callback) {
	var checkIn = formData['checkIn'];
	var checkOut = formData['checkOut'];

	if (!check_dates(new Date(checkIn), new Date(checkOut), true)) {
		alertify.notify("Invalid dates!");
		return;
	}

	var jsonData = JSON.stringify(formData);
	// alert(jsonData);
	$.ajax({
		url : "/hotels/" + hotelId + "/quickRoomReservations/search",
		type : "POST",
		dataType : 'json',
		contentType : "application/json",
		data : jsonData,
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : callback,
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function fillTableQrrs(data) {

	var qrrs_list = data == null ? [] : (data instanceof Array ? data
			: [ data ]);

	var qrrsDiv = $('#vehsDiv');
	qrrsDiv.empty();
	var counter = 0;

	$
			.each(
					qrrs_list,
					function(index, qrr) {
						
						localStorage.setItem("version_" + qrr.id, qrr.version);
				
						var qrrDiv = $('<div class="company-div" id="qrrDiv_'
								+ counter
								+ '" style="bottom:'
								+ (60 - counter * 40)
								+ '%; top:'
								+ (3 + counter * 40)
								+ '%;"'
								+ '>'
								+ '<img src="../images/hotel_room.jpg" height = 90% width= 18%>'
								+ '<h3>Room number: ' + qrr.room.roomNumber
								+ '</h3>');

						qrrDiv
								.append('<p style="position: absolute;top:30%;left:22%;"> Capacity: '
										+ qrr.room.roomCapacity + '</p></div>');
						qrrDiv
								.append('<p style="position: absolute;top:42%;left:22%;"> Check in: '
										+ qrr.checkInDate.substring(0, 10)
										+ '</p></div>');
						qrrDiv
								.append('<p style="position: absolute;top:54%;left:22%;"> Check out: '
										+ qrr.checkOutDate.substring(0, 10)
										+ '</p></div>');

						qrrDiv.append('<p class="totalPrice">' + qrr.finalPrice
								+ '&#8364;</p>');

						qrrDiv.append('<p class="discount"><b>-' + qrr.discount
								+ '%</b></p>');

						qrrDiv.append('<p class="oldPrice">' + qrr.totalPrice
								+ '&#8364;</p>');

						qrrDiv
								.append('<button class="show_details_btn book_qrr_btn" id="book_qrr_'
										+ qrr.id + '">Book now</button>');
						counter++;
						qrrsDiv.append(qrrDiv);

					});

	$('.book_qrr_btn').on('click', function(e) {
		e.preventDefault();
		var qrrId = this.id.substring(9);

		alertify.confirm('Booking confirmation', 'Are you sure?', function() {
			bookQrr(qrrId);
		}, function() {
			alertify.notify("Booking canceled");
		});

	});

}

function fillTableRooms(data) {

	var veh_list = data == null ? []
			: (data instanceof Array ? data : [ data ]);

	var roomsDiv = $('#vehsDiv');
	roomsDiv.empty();
	var counter = 0;

	$
			.each(
					veh_list,
					function(index, veh) {
			
						var vehDiv = $('<div class="company-div" id="vehDiv_'
								+ counter
								+ '" style="bottom:'
								+ (60 - counter * 40)
								+ '%; top:'
								+ (3 + counter * 40)
								+ '%;"'
								+ '>'
								+ '<img src="../images/hotel_room.jpg" height = 90% width= 18%>'
								+ '<h3>Room number: ' + veh.roomNumber
								+ '</h3>');

						vehDiv.append('<p> Floor: ' + veh.floorNumber + '</p>');
						vehDiv
								.append('<p style="position: absolute;top:45%;left:22%;"> Capacity: '
										+ veh.capacity + '</p></div>');

						vehDiv
								.append('<p style="position: absolute;top:65%;left:22%;">Price per night: '
										+ veh.pricePerNight + ' &#8364;</p>');
						vehDiv
								.append('<p style="position: absolute;top:2%;left:80%;">Guest ratings: '
										+ (veh.rating == 0 ? "no ratings"
												: veh.rating) + ' / 5 </p>');

						if (veh.totalPrice != undefined && veh.totalPrice != 0) {
							localStorage.setItem('totalPrice' + veh.id,
									veh.totalPrice);
							vehDiv.append('<p class="totalPrice">'
									+ veh.totalPrice + '&#8364;</p>');
						}

						vehDiv
								.append('<button class="show_details_btn book_room_btn" id="book_room_'
										+ veh.id + '">Book now</button>');

						var slideShowContainer = $('<div class="slideshow-container"></div>');
						$
								.each(
										veh.prices,
										function(index, price) {

											slideShowContainer
													.append('<div class="mySlides fade mySlides'
															+ veh.id
															+ '"><p>'
															+ price.startDate
																	.substring(
																			0,
																			10)
															+ ' &nbsp;-&nbsp; '
															+ price.endDate
																	.substring(
																			0,
																			10)
															+ '&nbsp;&nbsp;<b>'
															+ price.price
															+ '&#8364;</b></p></div>');
										});

						slideShowContainer
								.append('<a class="prev" onclick="plusSlides(-1,'
										+ veh.id
										+ ')" style="font-size:15px;">&#10094;</a>'
										+ '<a class="next" onclick="plusSlides(1,'
										+ veh.id
										+ ')" style="font-size:15px;">&#10095;</a>');

						if (veh.prices.length > 0) {
							vehDiv.append(slideShowContainer);
						}
						counter++;
						roomsDiv.append(vehDiv);

					});
	$.each(veh_list, function(index, veh) {
		if (veh.prices.length > 0)
			showSlides(1, veh.id);
	});

	$('.book_room_btn').on('click', function(e) {
		e.preventDefault();
		var roomId = this.id.substring(10);
		var rrData = collectRoomReservationData(roomId);

		if (!validateRRData(rrData)) {
			return;
		}

		localStorage.setItem('rrData', JSON.stringify(rrData));

		openForm();

	});
}

function bookRoom(rrData) {

	alertify.set('notifier', 'position', 'top-right');

	var hotelId = localStorage.getItem('showRooms');

	var jsonData = rrData;

	$.ajax({
		type : 'POST',
		url : '/hotels/' + hotelId + '/roomReservations',
		contentType : 'application/json',
		dataType : 'json',
		data : jsonData,
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(data) {
			alertify.notify('Room booked!');
		
			searchRooms(getSearchRoomsData(), fillTableRooms);
			var flightAndHotel = localStorage.getItem('flightAndHotel');
			if (flightAndHotel != null) {
				localStorage.setItem('flightAndHotel', data.id);
				window.location.replace("passengers.html");
			}
		},
		statusCode : {
			403 : function() {

				alertify.error('You must first log in!');
			}
		},
		statusCode : {
			410 : function() {

				alertify.alert('Exception','Room was deleted!');
				searchRooms(getSearchRoomsData(), fillTableRooms);
			}, error : function(xhr, status, error) {
			try {
				var err = JSON.parse(xhr.responseText);
				alertify.alert("Exception", err.apierror.message);
			} catch (err) {
				alertify.alert("Exception", xhr.responseText + " " + status + " " + error);

				searchRooms(getSearchRoomsData(), fillTableRooms);
			}}}
	});
}

function bookQrr(qrrId) {

	alertify.set('notifier', 'position', 'top-right');
	var user;
	if (localStorage.getItem("currentUser") == null) {
		alertify.error('You must first log in!');
		return;
	}
	
	var sendData = {};
	sendData["id"] = (JSON.parse(localStorage.getItem("currentUser"))).id;
	sendData["version"] = localStorage.getItem("version_" + qrrId);
	
	$.ajax({
		type : 'PUT',
		url : '/hotels/' + hotelId + '/quickRoomReservations/' + qrrId,
		contentType : 'application/json',
		data : JSON
				.stringify(sendData),
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function() {

			var qrrs = JSON.parse(localStorage.getItem('qrrsMap'));
			
			delete qrrs[qrrId];
			
			localStorage.setItem('qrrsMap', JSON.stringify(qrrs));

			var array = Object.values(qrrs);
			fillTableQrrs(array);

			alertify.notify('Booked!');
		},
		statusCode : {
			403 : function() {

				alertify.error('You must first log in!');
			}
		},
		statusCode : {
			400 : function() {

				alertify.alert('Booking Failed', 'Bad data was sent!');
			}
		},
		error : function(xhr, status, error) {
			try {
				var err = JSON.parse(xhr.responseText);
				alertify.alert("Exception", err.apierror.message);
			} catch (err) {
				alertify.alert("Exception", xhr.responseText + " " + status + " " + error);
			}
			searchQrr(getSearchRoomsData(), function(data) {

				var qrrsMap = arrayToObject(data, "id");
				localStorage.setItem('qrrsMap', JSON.stringify(qrrsMap));
				// alert(JSON.stringify(qrrsMap));
				fillTableQrrs(data);
			});

		}
	});
}

function collectRoomReservationData(roomId) {

	var rrData = {};
	rrData['roomId'] = roomId;
	rrData['hotelId'] = localStorage.getItem('showRooms');
	rrData['checkIn'] = localStorage.getItem('latestHSearchCheckIn');
	rrData['checkOut'] = localStorage.getItem('latestHSearchCheckOut');
	if (JSON.parse(localStorage.getItem('currentUser')) != null)
		rrData['userId'] = JSON.parse(localStorage.getItem('currentUser')).id;

	rrData['totalPrice'] = localStorage.getItem('currentPrice');

	return rrData;

}

function validateRRData(rrData) {
	alertify.set('notifier', 'position', 'top-right');
	if (rrData['userId'] === undefined || rrData['userId'] === null) {
		alertify.error('You must first log in!');
		return false;
	} else if (rrData['checkIn'] === "" || rrData['checkOut'] === "") {
		alertify.error('You must choose check in and check out dates!');
		return false;
	} else if (rrData['checkIn'] >= rrData['checkOut']) {
		// TODO fix bug
		alertify.error('Invalid dates!');
		return false;
	}

	return true;
}

// ******************** slides prices *****************
var slideIndex = 1;

// Next/previous controls
function plusSlides(n, id) {
	showSlides(slideIndex += n, id);
}

// Thumbnail image controls
function currentSlide(n, id) {
	showSlides(slideIndex = n, id);
}

function showSlides(n, id) {
	var i;
	var slides = document.getElementsByClassName("mySlides" + id);
	if (n > slides.length) {
		slideIndex = 1
	}
	if (n < 1) {
		slideIndex = slides.length;
	}
	for (i = 0; i < slides.length; i++) {
		slides[i].style.display = "none";
	}

	slides[slideIndex - 1].style.display = "block";

}

// ************ popup form ****************

function fillPopupForm(data) {
	// alert(JSON.stringify(data));

	var specialOffers = data == null ? [] : (data instanceof Array ? data
			: [ data ]);

	var formContainer = $('#check_container');
	formContainer.empty();
	var form = $('<form id="so_form"></form>');
	var rrData = JSON.parse(localStorage.getItem('rrData'));
	var price = localStorage.getItem('totalPrice' + rrData['roomId']);
	$('#p_price').empty().append(price + '&#8364;');
	localStorage.setItem('currentPrice', price);
	$.each(specialOffers, function(index, so) {
		form.append('<input type="checkbox" class="so_check" name="' + so.name
				+ '" value="' + so.id + '"price=' + so.price + '" id="' + so.id
				+ '"><label for="' + so.id + '">' + so.name + '&nbsp;&nbsp;+'
				+ so.price + '&#8364</label>');
	});

	formContainer.append(form);

}

function openForm() {

	var hotelId = localStorage.getItem('showRooms');
	getSpecialOffers(hotelId, fillPopupForm);

	document.getElementById("myForm").style.display = "block";
}

function closeForm() {
	document.getElementById("myForm").style.display = "none";
}

function clearSerachRoomForm() {
	$('#src-hotel-checkIn').val("");
	$('#src-hotel-checkOut').val("");
	$('#src-hotel-floor').val("");
	$('#src-hotel-capacity').val("");
	$('#src-hotel-max').val("");
	$('#src-hotel-min').val("");
}
