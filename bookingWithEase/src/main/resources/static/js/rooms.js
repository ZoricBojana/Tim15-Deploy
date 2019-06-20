alertify.set('notifier', 'position', 'top-right');
$(document).on('click', '#addNewRoom', function(e) {
	e.preventDefault();
	roomRegistration();
});

$(document).on('click', '#cancelNewRoom', function(e) {
	e.preventDefault();
	window.location.href = "hotels.html";
});

function roomRegistration() {
	var formData = getFormData("#addRoomForm");
	var currentUser = JSON.parse(localStorage.getItem('currentUser'));

	formData['hotelId'] = currentUser.company.id;

	var newFormData = {};
	var prices = [];
	for ( var el in formData) {
		if (el.startsWith('room_sp')) {
			var price = {};
			price["price"] = formData[el];
			price["startDate"] = formData["start_date_" + el.substring(8)];
			price["endDate"] = formData["end_date_" + el.substring(8)];
			prices.push(price);
		} else if (!el.startsWith('start_date_') && !el.startsWith('end_date')) {
			newFormData[el] = formData[el];
		}
	}

	newFormData["prices"] = prices;

	var validData = Boolean(validateNewRoomData(formData));
	if (validData) {
		var jsonData = JSON.stringify(newFormData);
		console.log(formData);
		$.ajax({
			url : "/hotels/" + formData['hotelId'] + "/rooms",
			type : "POST",
			contentType : "application/json",
			data : jsonData,
			dataType : 'json',
			beforeSend : function(xhr) {
				xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
			},
			success : function(data) {
				alertify.notify("Room added :)");
	
			},statusCode : {
				400 : function() {

					alertify.alert('Please enter all valid data!');
				}
			},
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
	} else {

	}
}

function deleteRoom(hotelId, roomId, callback) {

	$.ajax({
		type : 'delete',
		url : "/hotels/" + hotelId + "/rooms/" + roomId,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : callback,
		error : function(xhr, status, error ) {
			var err = JSON.parse(xhr.responseText);
			alertify.alert("Exception", err.apierror.message);
		}
	});

}

function updateRoom(hotelId, roomId, jsonData, callback) {
	$.ajax({
		type : 'put',
		url : "/hotels/" + hotelId + "/rooms/" + roomId,
		contentType : 'application/json',
		data : jsonData,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : callback,
		statusCode : {
			400 : function() {

				alertify.alert('Please enter all valid data!');
			}
		},
		error : function(xhr, status, error) {
			try{
				var err = JSON.parse(xhr.responseText);
				alertify.alert("Exception", err.apierror.message);
			}catch(err)
			{
				alertify.alert("Exception", xhr.responseText + status + error);
			}
		}
	});

}

function validateNewRoomData(formData) {
	var roomNumber = formData["roomNumber"];
	var floorNumber = formData["floorNumber"];
	var capacity = formData["capacity"];
	var pricePerNight = formData["pricePerNight"];

	if (roomNumber === "") {
		alertify.notify("Please enter a room number");
		return false;
	}
	if (!isInt(roomNumber)) {
		alertify.notify("Please enter a valid number for room number");
		return false;
	}
	if (floorNumber === "") {
		alertify.notify("Please enter a floor number");
		return false;
	}
	if (!isInt(floorNumber)) {
		alertify.notify("Please enter a valid number for floor number");
		return false;
	}
	if (capacity === "") {
		alertify.notify("Please enter a capacity");
		return false;
	}
	if (!isInt(capacity)) {
		alertify.notify("Please enter a valid number for capacity");
		return false;
	}
	if (!isDouble(pricePerNight)) {
		alertify.notify("Please enter a valid price");
		return false;
	}

	for (el in formData) {

		if (el.startsWith('room_sp_') && formData[el] === "") {
			alertify.notify("Please fill in all prices");
			return false;
		}

		if (el.startsWith('start_date_') && formData[el] === "") {
			alertify.notify("Please fill in all start dates");
			return false;
		}

		if (el.startsWith('end_date_') && formData[el] === "") {
			alertify.notify("Please fill in all end dates");
			return false;
		}

	}

	return true;

}

function isInt(value) {
	var er = /^-?[0-9]+$/;
	return er.test(value);
}

function isDouble(value) {
	var er = /^[+-]?\d+(\.\d+)?$/;
	return er.test(value);
}
