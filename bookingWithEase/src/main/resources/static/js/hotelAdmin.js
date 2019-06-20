var specialPriceCounter = 0;
alertify.set('notifier', 'position', 'top-right');

$(document).ready(function() {

	var currentUser = JSON.parse(localStorage.getItem('currentUser'));

	getHotel(currentUser);
});

$(document).on('click', '#addNewRoom', function(e) {
	e.preventDefault();
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));

	getRooms(currentUser.company.id);
});

$(document).on('click', '#special_offers_btn', function(e) {
	e.preventDefault();
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var hotelId = currentUser.company.id;

	getSpecialOffers(hotelId, fillSpecialOffers);

});

$(document).on('click', '#addSpecialOfferBtn', function(e) {
	e.preventDefault();

	addSpecialOffer(function() {
		var currentUser = JSON.parse(localStorage.getItem('currentUser'));
		var hotelId = currentUser.company.id;
		alertify.notify("Special offer added");
		getSpecialOffers(hotelId, fillSpecialOffers);
	});

});

$(document).on('click', '#cancelSpecialOfferBtn', function(e) {
	e.preventDefault();
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var hotelId = currentUser.company.id;

	openCity(event, 'allSpecialOffers');
	getSpecialOffers(hotelId, fillSpecialOffers);
});

$(document).on('click', '#edit_so_btn', function(e) {
	e.preventDefault();

	var formData = getFormData('#editSpecialOfferForm');
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var soId = localStorage.getItem('updateSoId');
	formData["id"] = soId;
	formData["hotelId"] = currentUser.company.id;
	var jsonData = JSON.stringify(formData);

	updateSpecialOffer(currentUser.company.id, soId, jsonData, function(data) {
		alertify.notify("Saved :)");
		openCity(event, 'allSpecialOffers');
		fillSpecialOffers(data);

	});

});

$(document).on('click', '#pricelist_btn', function(e) {
	e.preventDefault();
	var formData = getFormData('#pricelist_form');
	// alert("pricelist " + JSON.stringify(formData));
	updatePricelist(JSON.stringify(formData), function(data) {
		alertify.notify("Saved :)");
		fillPricelist(data);

	});

});

$(document).on('click', '#openQrrBtn', function(e) {
	e.preventDefault();
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var hotelId = currentUser.company.id;

	getSpecialOffers(hotelId, addSpecialOffersToQrr);

});

$(document).on('click', '#allQrrBtn', function(e) {
	e.preventDefault();
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var hotelId = currentUser.company.id;

	getQuickRoomReservations(hotelId, fillQrrTable);

});

$(document).on('click', '#editQuickRoomReservationBtn', function(e) {
	e.preventDefault();
	var formData = getFormData('#editQuickRoomReservationForm');
//	alertify.alert(JSON.stringify(formData));
	var newFormData = {};
	var specialOffers = [];
	var rooms = [];
	rooms.push(formData["roomNumber"]);
	
	//alert(JSON.stringify(formData));
	for ( var el in formData) {
		if (el.startsWith('qrr_so_')) {
			specialOffers.push(formData[el]);

		} else if(el !== "roomNumber") {
			newFormData[el] = formData[el];
		}
	}

	newFormData["rooms"] = rooms;
	newFormData["specialOffers"] = specialOffers;
	//alert(JSON.stringify(newFormData));
	
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var hotelId = currentUser.company.id;

	updateQuickRoomReservations(hotelId, newFormData, function(data) {
		alertify.notify("Quick Room Reservation Updated!");
	});
	
});

$(document).on(
		'click',
		'#addQuickRoomReservationBtn',
		function(e) {
			e.preventDefault();
			var formData = getFormData('#addQuickRoomReservationForm');

			var newFormData = {};
			var specialOffers = [];
			var rooms = [];

			for ( var el in formData) {
				if (el.startsWith('qrr_so_')) {
					specialOffers.push(formData[el]);

				} else if (el.startsWith('qrr_room_')) {
					rooms.push(formData[el]);

				} else if (!el.startsWith('start_date_')
						&& !el.startsWith('end_date')) {
					newFormData[el] = formData[el];
				}
			}

			newFormData["rooms"] = rooms;
			newFormData["specialOffers"] = specialOffers;

			var currentUser = JSON.parse(localStorage.getItem('currentUser'));
			var hotelId = currentUser.company.id;

			addQuickRoomReservations(hotelId, newFormData, function(data) {
				alertify.notify("Quick Room Reservation Saved!");
			});

		});

$(document).on('click', '#cancelQuickRoomReservationBtn', function(e) {
	e.preventDefault();
	openCity(event, '');
});

$(document).on('click', '#cancelEditQuickRoomReservationBtn', function(e) {
	e.preventDefault();
	openCity(event, '');
});

$(document).on('change', '.qrrDate', function(e) {

	var newCheckIn = $('#qrr-checkIn').val();
	var newCheckOut = $('#qrr-checkOut').val();
	if (newCheckIn === "" || newCheckOut === "") {
		return;
	}

	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var hotelId = currentUser.company.id;

	var formData = {};
	formData["floorNumber"] = -11;
	formData["hotelId"] = hotelId;
	formData["checkIn"] = newCheckIn;
	formData["checkOut"] = newCheckOut;

	searchRooms(formData, addRoomsToQrr);

});

function addSpecialOffersToQrr(data) {
	var specialOffers = data == null ? [] : (data instanceof Array ? data
			: [ data ]);
	$('#qrr-specialOffers').empty();
	// $('#qrr-specialOffers-edit').empty();
	var counter = 0;
	$
			.each(
					specialOffers,
					function(index, so) {

						counter++;
						var newItem = $('<div align="left"></div');
						newItem
								.append('<input type="checkbox" class= "qrr_so_checkbox" name="qrr_so_checkbox'
										+ so.id
										+ '" value="'
										+ so.id
										+ '" />'
										+ so.name);
						$('#qrr-specialOffers').append(newItem);
						// $('#qrr-specialOffers-edit').append(newItem);
					});

	if (counter == 0) {
		$('#qrr-specialOffers').append("none available");
		// $('#qrr-specialOffers-edit').append("none available");
	}
}

function addRoomsToQrr(data) {
	// alert(JSON.stringify(data));
	var rooms = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#qrr-rooms').empty();
	var counter = 0;
	$
			.each(
					rooms,
					function(index, room) {

						counter++;
						var newItem = $('<div align="left"></div');
						newItem
								.append('<input type="checkbox" class= "qrr_room_checkbox" name="qrr_room_checkbox'
										+ room.id
										+ '" value="'
										+ room.id
										+ '" />'
										+ room.roomNumber
										+ " "
										+ room.capacity + " beds");
						$('#qrr-rooms').append(newItem);

					});

	if (counter == 0)
		$('#qrr-rooms').append("none available");
}

function updatePricelist(jsonData, callback) {
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var hotelId = currentUser.company.id;

	$.ajax({
		type : 'put',
		url : "/hotels/" + hotelId + "/serviceTypePrices",
		contentType : 'application/json',
		dataType : 'json',
		data : jsonData,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : callback,
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function addSpecialOffer(callback) {
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var hotelId = currentUser.company.id;

	var formData = getFormData('#addSpecialOfferForm');
	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/hotels/" + hotelId + "/specialOffers",
		type : "POST",
		dataType : 'json',
		contentType : 'application/json',
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

function getSpecialOffers(hotelId, callback) {

	$.ajax({
		url : "/hotels/" + hotelId + "/specialOffers",
		type : "GET",
		async : false,
		dataType : 'json',
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

function deleteSpecialOffer(hotelId, soId, callback) {
	$.ajax({
		url : "/hotels/" + hotelId + "/specialOffers/" + soId,
		type : "DELETE",
		dataType : 'json',
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

function updateSpecialOffer(hotelId, soId, jsonData, callback) {
	$.ajax({
		type : 'put',
		url : "/hotels/" + hotelId + "/specialOffers/" + soId,
		contentType : 'application/json',
		dataType : 'json',
		data : jsonData,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : callback,
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function getQuickRoomReservations(hotelId, callback) {
	$
			.ajax({
				url : "/hotels/" + hotelId + "/quickRoomReservations/notBooked",
				type : "GET",
				dataType : 'json',
				beforeSend : function(xhr) {
					/* Authorization header */
					xhr.setRequestHeader("Authorization", "Bearer "
							+ getJwtToken());
				},
				success : callback,
				error : function(xhr, status, error) {
					alertify.alert("Error", xhr.responseText);
				}

			});
}

function addQuickRoomReservations(hotelId, formData, callback) {

	var jsonData = JSON.stringify(formData);
	if(formData["discount"] < 0 || formData["discount"] > 100){
		alertify.notify("Discount is not valid");
		return;
	}
	$.ajax({
		url : "/hotels/" + hotelId + "/quickRoomReservations",
		type : "POST",
		dataType : 'json',
		contentType : 'application/json',
		data : jsonData,
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : callback,
		statusCode : {
			400 : function() {

				alertify.alert('Error', 'Please insert all valid data');
			}
		},
		statusCode : {
			409 : function() {

				alertify.alert('Error', 'Unable to save all quick room reservations');
			}
		},
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function updateQuickRoomReservations(hotelId, formData, callback) {

	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/hotels/" + hotelId + "/quickRoomReservations/" + formData['id'] + "/update",
		type : "PUT",
		dataType : 'json',
		contentType : 'application/json',
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

function deleteQuickRoomReservation(hotelId, qrrId, callback) {
	$.ajax({
		url : "/hotels/" + hotelId + "/quickRoomReservations/" + qrrId,
		type : "DELETE",
		dataType : 'json',
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : callback,
		error : function(xhr, status, error) {
			try{
				var err = JSON.parse(xhr.responseText);
				alertify.alert("Exception", err.apierror.message);
			}catch(err)
			{
				alertify.alert("Exception", xhr.responseText);
			}
		}

	});
}

function getHotel(user) {
	var hotelId = user.company.id;

	$.ajax({
		url : "/hotels/" + hotelId,
		type : "GET",
		dataType : 'json',
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillHotelInfo,
		error : function(xhr, status, error) {
			alertify.alert("Error", xhr.responseText);
		}

	});
}

function fillHotelInfo(data) {
	$('#hotel_name').empty().append(data.name);
	$('#hotel_address').empty().append(data.address);
	$('#hotel_latitude').empty().append(data.latitude);
	$('#hotel_longitude').empty().append(data.longitude);
	$('#hotel_description').empty().append(data.description);
	$('#hotel_stars').empty().append(data.stars);
	$('#hotel_rating').empty().append(data.rating);

	$('#edit_hotel_name').val(data.name);
	$('#edit_hotel_address').val(data.address);
	$('#edit_hotel_latitude').val(data.latitude);
	$('#edit_hotel_longitude').val(data.longitude);
	$('#edit_hotel_description').val(data.description);
	$('#edit_hotel_stars').val(data.stars);

	// alert(JSON.stringify(data));

	fillPricelist(data);

	$('#edit_hotel_form').on(
			'submit',
			function(e) {

				e.preventDefault();
				var iden = this.id;

				var formData = {};
				var s_data = $('#' + this.id).serializeArray();

				for (var i = 0; i < s_data.length; i++) {
					var record = s_data[i];
					if (record.name === "ident") {
						formData["id"] = record.value;
					} else {
						formData[record.name] = record.value;
					}
				}

				var currentUser = JSON.parse(localStorage
						.getItem('currentUser'));

				formData['id'] = currentUser.company.id;

				if (formData["name"] === "") {
					alertify.alert("Please enter a hotel name");
					return;
				}
				
				if(formData["stars"] < 1 || formData["stars"] > 5)
				{
					alertify.alert("Please enter a valid number for stars");
					return;
				}

				var jsonData = JSON.stringify(formData);
				$.ajax({
					type : 'put',
					url : "/hotels/" + this.id,
					contentType : 'application/json',
					dataType : 'json',
					data : jsonData,
					beforeSend : function(xhr) {
						/* Authorization header */
						xhr.setRequestHeader("Authorization", "Bearer "
								+ getJwtToken());
					},
					success : getHotels,
					error : function(xhr, status, error) {
						alertify.alert("Error", xhr.responseText);
					}

				});

				alertify.notify("Saved");
				getHotel(JSON.parse(localStorage.getItem('currentUser')));
			});

}

function fillPricelist(data) {

	$('#hstpId').val(data.serviceTypePrices.id);
	$('#bedAndBreakfastPrice').val(data.serviceTypePrices.bedAndBreakfastPrice);
	$('#halfBoardPrice').val(data.serviceTypePrices.halfBoardPrice);
	$('#fullBoardPrice').val(data.serviceTypePrices.fullBoardPrice);
	$('#allInclusivePrice').val(data.serviceTypePrices.allInclusivePrice);

	if (data.serviceTypePrices.bedAndBreakfastEnabled)
		$('#bedAndBreakfastEnabled').prop('checked', true);
	else
		$('#bedAndBreakfastPrice').prop('readonly', true);

	if (data.serviceTypePrices.halfBoardEnabled == true)
		$('#halfBoardEnabled').prop('checked', true);
	else
		$('#halfBoardPrice').prop('readonly', true);

	if (data.serviceTypePrices.fullBoardEnabled)
		$('#fullBoardEnabled').prop('checked', true);
	else
		$('#fullBoardPrice').prop('readonly', true);

	if (data.serviceTypePrices.allInclusiveEnabled)
		$('#allInclusiveEnabled').prop('checked', true);
	else
		$('#allInclusivePrice').prop('readonly', true);

	$("#bedAndBreakfastEnabled").change(function() {
		if (this.checked) {
			$('#bedAndBreakfastPrice').prop('readonly', false);
		} else {
			$('#bedAndBreakfastPrice').prop('readonly', true);
		}
	});

	$("#halfBoardEnabled").change(function() {
		if (this.checked) {
			$('#halfBoardPrice').prop('readonly', false);
		} else {
			$('#halfBoardPrice').prop('readonly', true);
		}
	});

	$("#fullBoardEnabled").change(function() {
		if (this.checked) {
			$('#fullBoardPrice').prop('readonly', false);
		} else {
			$('#fullBoardPrice').prop('readonly', true);
		}
	});

	$("#allInclusiveEnabled").change(function() {
		if (this.checked) {
			$('#allInclusivePrice').prop('readonly', false);
		} else {
			$('#allInclusivePrice').prop('readonly', true);
		}
	});
}

function fillSpecialOffers(data) {
	var specialOffers = data == null ? [] : (data instanceof Array ? data
			: [ data ]);

	$('#specialOffersTable').empty();
	$('#specialOffersTable')
			.append(
					'<tr><th>Id</th><th>Name</th><th>Description</th><th>Additional payment</th><th>&nbsp;</th><th>&nbsp;</th></tr>');

	$.each(specialOffers, function(index, specialOffer) {
		localStorage.setItem("specialOffer_" + specialOffer.id, JSON
				.stringify(specialOffer));

		var tr = $('<tr></tr>');
		var soTr = $('<td>' + specialOffer.id + '</td>' + '<td id="tso_'
				+ specialOffer.id + '">' + specialOffer.name + '</td>' + '<td>'
				+ specialOffer.description + '</td>' + '<td>'
				+ specialOffer.price + '</td>'
				+ '<td><button class="edit_so_btn" id="edit_so_'
				+ specialOffer.id + '">Edit</button></td>'
				+ '</td><td><button class="delete_so_btn" id="delete_so_'
				+ specialOffer.id + '">Delete</button></td>');

		tr.append(soTr);
		$('#specialOffersTable').append(tr);
	});

	$('.delete_so_btn').on('click', function(e) {
		e.preventDefault();

		var currentUser = JSON.parse(localStorage.getItem('currentUser'));
		var soId = this.id.substring(10);

		var hotelId = currentUser.company.id;
		alertify.confirm('Delete special offer', 'Are you sure?', function() {
			deleteSpecialOffer(hotelId, soId, function(data) {

				alertify.notify("Special offer deleted!");
				fillSpecialOffers(data);

			});
		}, function() {
			alertify.notify("Canceled");
		});

	});

	$('.edit_so_btn').on('click', function(e) {
		e.preventDefault();

		var currentUser = JSON.parse(localStorage.getItem('currentUser'));
		var soId = this.id.substring(8);
		var hotelId = currentUser.company.id;
		openCity(event, 'editSpecialOffer');

		var so = JSON.parse(localStorage.getItem('specialOffer_' + soId));
		localStorage.setItem("updateSoId", soId);

		$('#so_name').val(so.name);
		$('#so_description').val(so.description);
		$('#so_price').val(so.price);

	});

}

function fillQrrTable(data) {
	var qrrs = data == null ? [] : (data instanceof Array ? data : [ data ]);

	var qrrs_dict = arrayToObject(qrrs, "id");
	localStorage.setItem("qrrs", JSON.stringify(qrrs_dict));

	$('#allQrrTable').empty();
	$('#allQrrTable')
			.append(
					'<tr><th>Id</th><th>Room number</th><th>Check in</th><th>Check out</th><th>Price</th><th>Discount</th><th>Final price</th><th>&nbsp;</th><th>&nbsp;</th></tr>');

	$.each(qrrs, function(index, qrr) {
		// localStorage.setItem("specialOffer_" + specialOffer.id, JSON
		// .stringify(specialOffer));

		var tr = $('<tr></tr>');

		var qrrTr = $('<td>' + qrr.id + '</td>' + '<td id="qrr_' + qrr.id
				+ '">' + qrr.room.roomNumber + '</td>' + '<td>'
				+ qrr.checkInDate.substring(0, 10) + '</td>' + '<td>'
				+ qrr.checkOutDate.substring(0, 10) + '</td><td>'
				+ qrr.totalPrice + '&#8364;</td><td>' + qrr.discount + '%<td>'
				+ qrr.finalPrice + '&#8364;</td>'
				+ '<td><button class="edit_qrr_btn" id="edit_qrr_' + qrr.id
				+ '">Edit</button></td>'
				+ '</td><td><button class="delete_qrr_btn" id="delete_qrr_'
				+ qrr.id + '">Delete</button></td>');

		tr.append(qrrTr);

		$('#allQrrTable').append(tr);
	});

	$('.delete_qrr_btn')
			.on(
					'click',
					function(e) {
						e.preventDefault();

						var currentUser = JSON.parse(localStorage
								.getItem('currentUser'));
						var qrrId = this.id.substring(11);

						var hotelId = currentUser.company.id;
						alertify
								.confirm(
										'Delete quick room reservation',
										'Are you sure?',
										function() {
											deleteQuickRoomReservation(
													hotelId,
													qrrId,
													function(data) {

														alertify
																.notify("Quick room reservation deleted!");
														fillQrrTable(data);

													});
										}, function() {
											alertify.notify("Canceled");
										});

					});

	$('.edit_qrr_btn').on('click', function(e) {
		e.preventDefault();

		var currentUser = JSON.parse(localStorage.getItem('currentUser'));
		var qrrId = this.id.substring(9);

		var hotelId = currentUser.company.id;

		getSpecialOffers(hotelId, addSpecialOffersToEditQrr);
		fillEditQrrForm(qrrId);
		openCity(event, 'editQuickRoomReservation');

	});
}

function roomSpecialPrice() {
	var priceDiv = $('#add_room_table');
	specialPriceCounter++;

	priceDiv
			.append('<tr class="delete_tr"><td>&nbsp;</td></tr>'
					+ '<tr class="delete_tr"><td align="left">Price:</td><td><input type="text" name="room_sp_'
					+ specialPriceCounter
					+ '"id="room_sp_'
					+ specialPriceCounter
					+ '"/></td></tr>'
					+ '<tr tr class="delete_tr"><td>Start date:</td><td><input type="date" name="start_date_'
					+ specialPriceCounter
					+ '" id="start_date_'
					+ specialPriceCounter
					+ '" ></td></tr>'
					+ '<tr class="delete_tr"><td>End date:</td><td><input type="date"  name="end_date_'
					+ specialPriceCounter + '"id="end_date_'
					+ specialPriceCounter + '"></td></tr>'

			);

}

function addSpecialOffersToEditQrr(data) {
	var specialOffers = data == null ? [] : (data instanceof Array ? data
			: [ data ]);

	$('#qrr-specialOffers-edit').empty();
	var counter = 0;
	$
			.each(
					specialOffers,
					function(index, so) {

						counter++;
						var newItem = $('<div align="left"></div');
						newItem
								.append('<input type="checkbox" class= "qrr_so_checkbox" name="qrr_so_checkbox'
										+ so.id
										+ '" value="'
										+ so.id
										+ '" id="qrr_so_checkbox_'
										+ so.id
										+ '" />' + so.name);

						$('#qrr-specialOffers-edit').append(newItem);
					});

	if (counter == 0) {
		$('#qrr-specialOffers-edit').append("none available");
	}
}

function fillEditQrrForm(qrrId) {
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
		for ( var v in sos) {

			if ($(check).val() == sos[v].id) {
				$('#qrr_so_checkbox_' + sos[v].id).prop('checked', true);
			}

		}

	});

}

$(document).on('submit', '#reportDatesForm', function(e) {

	e.preventDefault();

	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var hotelId = currentUser.company.id;
	
	var formData = getFormData("#reportDatesForm");
	var jsonData = JSON.stringify(formData);
	
	var start = formData["start"];
	var end = formData["end"];
	
	if(!check_dates(new Date(start),new Date(end), false))
	{
		alertify.notify("Invalid dates!");
		return;
	}
	
	$.ajax({
		url : "/hotels/" + hotelId + "/income",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(data){
			$("#totalIncomeH2").html("Total income: " + data + " &euro;");
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("Something went wrong! :( " + errorThrown);
		}
	});
	
	$.ajax({
		url : "/hotels/" + hotelId + "/avgRatePeriod",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(data){
			if(data != -1){
				$("#avgRateH2").html("Average company rate: " + data);
			}else
			{
				$("#avgRateH2").html("Average company rate:  no rates");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("Something went wrong! :( " + errorThrown);
		}
	});

});
