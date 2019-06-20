$( document ).ready(function() {
	findVehicles();
});


function findVehicles() {
	var value = localStorage.getItem('showVeh');
	if(localStorage.getItem('vehicleBegin') != null){
		$('#racIdHidden').val(value);
		$('#vehpickup').val(localStorage.getItem('vehicleBegin'));
		$('#vehdropoff').val(localStorage.getItem('vehicleEnd'));
		
		var formData = getFormData("#formsrcvehs");
		var jsonData = JSON.stringify(formData);
		$.ajax({
			type : 'POST',
			url : '/rentacars/vehicles/search',
			contentType : 'application/json',
			dataType : 'json',
			data : jsonData,
			beforeSend : function(xhr) {
				/* Authorization header */
				xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
			},
			success : fillTable
		});
		
		localStorage.removeItem('vehicleBegin');
		localStorage.removeItem('vehicleEnd');
	}else{
	
	
	$.ajax({
		type : 'GET',
		url : "/rentacars/" + value + "/vehicles",
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillTable,
		error : function(data) {
			alert(data);
		}
	});}
}

function fillTable(data) {
	var veh_list = data == null ? []
			: (data instanceof Array ? data : [ data ]);

	var vehsDiv = $('#vehsDiv');
	vehsDiv.empty();
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
								+ '<img src="../images/cars.jpg" height = 150 width= 150>'
								+ '<h3>' + veh.registrationNumber + '</h3>');

						vehDiv.append('<p>' + veh.type + '</p>' + '</div>');
						vehDiv
								.append('<p style="position: absolute;top:65%;left:25%;">'
										+ veh.gear + '</p>');
						if (veh.pricePerDay != undefined && veh.pricePerDay != 0) {
							localStorage.setItem('totalPriceVeh' + veh.id,
									veh.pricePerDay);
							vehDiv.append('<p class="totalPrice">'
									+ veh.pricePerDay + '&#8364;</p>');
						}
						
						vehDiv
								.append('<button class="show_details_btn reserve_veh" id="reserve_veh' + veh.id + '">Make reservation</button>');

						counter++;
						vehsDiv.append(vehDiv);

					});
	$('.reserve_veh').on('click', function(e) {
		e.preventDefault();
		
		if(localStorage.getItem('currentUser') === null){
			alertify.error('Please, log in');
			return;
		}
		
		var vehId = this.id.substring(11);
		var vrData = collectVehicleReservationData(vehId);
		
		if(vrData == null){
			return;
		}
		
		if(!validateVehicleSearchData()){
			return;
		}
		
		localStorage.setItem('vrData',JSON.stringify(vrData) )

		//reserveVehicle(vrData);
		openFormRSO();
	});
}


$(document).on('submit', '#formsrcvehs', function(e) {
	e.preventDefault();
	
	if(!validateVehicleSearchData()){
		return;
	}
	
	$('#racIdHidden').val(localStorage.getItem('showVeh'));
	var formData = getFormData("#formsrcvehs");
	var jsonData = JSON.stringify(formData);
	$.ajax({
		type : 'POST',
		url : '/rentacars/vehicles/search',
		contentType : 'application/json',
		dataType : 'json',
		data : jsonData,
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillTable
	});

});

function validateVehicleSearchData(){
	var date1 = $('#vehpickup').val();
	var date2 = $('#vehdropoff').val();
	var now = new Date();
	if(new Date(date1).getTime() < now.getTime()){
		alertify.alert("Date not valid", "Pick up date is past");
		return false;
	}
	
	if(date2 < date1){
		alertify.alert("Date not valid", "Drop off date must be greater than pick up date");
		return false;
	}
	return true;
}


// reservations
function reserveVehicle(vrData) {

	alertify.set('notifier', 'position', 'top-right');

	var racId = localStorage.getItem('showVeh');
	
	if(vrData['checkInDate'] === "" || vrData['checkOutDate'] === ""){
		alertify.error('Please, choose dates.');
		return;
	}
	
	//vrData['totalPrice'] = 

	var jsonData = JSON.parse(vrData);
	// alert('rr ' + jsonData);
	$.ajax({
		type : 'POST',
		url : '/vehicles/' + jsonData['vehicle_id'] + '/reservations',
		contentType : 'application/json',
		dataType : 'json',
		data : vrData,
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(data) {
			alertify.notify('Vehicle reserved!');
			
			var flightAndCar = localStorage.getItem('flightAndCar');
			if(flightAndCar != null){
				localStorage.setItem('flightAndCar', data.id);
				window.location.replace("passengers.html");
			}else{
				window.location.replace("vehicles.html");
			}
			alertify.notify('Vehicle reserved!');
		},
		statusCode : {
			403 : function() {

				alertify.error('You must first log in!');
			},
			417 : function() {

				alertify.alert('Ooops, someone just take this vehicle!');
			},
			409 : function() {

				alertify.alert('Ooops, someone just take this vehicle. Please, try again!');
			}
		}
	});
}

function collectVehicleReservationData(vehicleId) {

	var vrData = {};
	vrData['vehicle_id'] = vehicleId;
	vrData['racId'] = localStorage.getItem('showVeh');
	vrData['checkInDate'] = $('#vehpickup').val();
	if(!vrData['checkInDate']){
		alertify.alert("", "Please, enter pick up date");
		return;
	}
	vrData['checkOutDate'] = $('#vehdropoff').val();
	
	if(!vrData['checkOutDate']){
		alertify.alert("", "Please, enter drop off date");
		return null;
	}
	
	if (JSON.parse(localStorage.getItem('currentUser')) != null)
		vrData['user_id'] = JSON.parse(localStorage.getItem('currentUser')).id;

	var vehPrice = localStorage.getItem('totalPriceVeh' + vehicleId);
	vrData['totalPrice'] = vehPrice * differenceBetweenDates(vrData['checkInDate'], vrData['checkOutDate']);

	return vrData;

}

function getRACSpecialOffers(racId) {

	$.ajax({
		url : "/rentacars/" + racId + "/specialOffers",
		type : "GET",
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillRACPopupForm,
		error : function(response) {
			alert("Something went wrong! :(");
		}
	});
}



function fillRACPopupForm(data) {

	var specialOffers = data == null ? [] : (data instanceof Array ? data
			: [ data ]);

	var formContainer = $('#check_container');
	formContainer.empty();
	var form = $('<form id="so_form"></form>');
	var vrData = JSON.parse(localStorage.getItem('vrData'));
	var price = localStorage.getItem('totalPriceVeh' + vrData['vehicleId']);
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

function openFormRSO() {

	var racId = localStorage.getItem('showVeh');
	getRACSpecialOffers(racId);

	document.getElementById("myVehResForm").style.display = "block";
}

function closeForm() {
	document.getElementById("myVehResForm").style.display = "none";
}


$(document).on(
		'click',
		'#so_rac_confirm_btn',
		function(e) {
			e.preventDefault();
			var soData = getFormData("#so_form");

			var so_list = [];
			$.each(soData, function(index, so) {
				so_list.push(so);
			});
			var vrData = JSON.parse(localStorage.getItem('vrData'));
			vrData['specialOffers'] = so_list;
			closeForm();
			var message = "Check in: " + vrData['checkInDate'] + "</br></br>";
			message += "Check out: " + vrData['checkOutDate'] + "</br></br>";
			message += "Total price: " + vrData['totalPrice'] 
					+ "</br></br>"

			alertify.confirm('Booking confirmation', message, function() {
				reserveVehicle(JSON.stringify(vrData));
			}, function() {
				alertify.notify("Booking canceled");
			});

		});

function differenceBetweenDates(date1, date2){

	// end - start returns difference in milliseconds 
	var diff = new Date(new Date(date2).getTime() - new Date(date1).getTime());

	// get days
	var days = diff/1000/60/60/24;
	
	return days;
}

$(document).on('click', '#info_veh_btn', function(e) {
	e.preventDefault();

	$(this).css('background-color', 'gray');
	$('#all_vehs_btn').css('background-color', 'black');
	$('#qvr_btn').css('background-color', 'black');
	
	var vehsDiv = $('#vehsDiv');
	vehsDiv.empty();

});

$(document).on('click', '#all_vehs_btn', function(e) {
	e.preventDefault();

	$(this).css('background-color', 'gray');
	$('#info_veh_btn').css('background-color', 'black');
	$('#qvr_btn').css('background-color', 'black');

	clearVehicleSearchData();

	findVehicles();

});

$(document).on('click', '#qvr_btn', function(e) {
	e.preventDefault();

	$(this).css('background-color', 'gray');
	$('#info_veh_btn').css('background-color', 'black');
	$('#all_vehs_btn').css('background-color', 'black');

	clearVehicleSearchData();
	
	var vehsDiv = $('#vehsDiv');
	vehsDiv.empty();
	
	findQuickVehicleReservations();

});

function findQuickVehicleReservations(){
	var racId = localStorage.getItem('showVeh');
	$.ajax({
		url : "/rentacars/" + racId + "/quickReservations",
		type : "GET",
		dataType : 'json',
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillTableQvrs,
		error : function(response) {
			alertify.alert("Something went wrong");
		}
	});
}

function fillTableQvrs(data) {

	var qvrs_list = data == null ? [] : (data instanceof Array ? data
			: [ data ]);

	var qvrsDiv = $('#vehsDiv');
	qvrsDiv.empty();
	var counter = 0;

	$
			.each(
					qvrs_list,
					function(index, qvr) {

						//	alert(qrr.id +   ' ' + JSON.stringify(qrr));
						var qvrDiv = $('<div class="company-div" id="qvrDiv_'
								+ counter
								+ '" style="bottom:'
								+ (60 - counter * 40)
								+ '%; top:'
								+ (3 + counter * 40)
								+ '%;"'
								+ '>'
								+ '<img src="../images/cars.jpg" height = 90% width= 18%>'
								+ '<h3>Registration number: ' + qvr.vehicle.vehicleRegistration
								+ '</h3>');

					/*	qvrDiv
								.append('<p style="position: absolute;top:18%;left:22%;"> Floor: '
										+ qrr.room.floorNumber + '</p>');*/
						qvrDiv
								.append('<p style="position: absolute;top:30%;left:22%;"> Type: '
										+ qvr.vehicle.vehicleType + '</p></div>');
						qvrDiv
								.append('<p style="position: absolute;top:42%;left:22%;"> Check in: '
										+ qvr.checkInDate.substring(0, 10)
										+ '</p></div>');
						qvrDiv
								.append('<p style="position: absolute;top:54%;left:22%;"> Check out: '
										+ qvr.checkOutDate.substring(0, 10)
										+ '</p></div>');

						qvrDiv.append('<p class="totalPrice">' + qvr.totalPrice*(100 - qvr.discount)/100
								+ '&#8364;</p>');

						qvrDiv.append('<p class="discount"><b>-' + qvr.discount
								+ '%</b></p>');

						qvrDiv.append('<p class="oldPrice">' + qvr.totalPrice
								+ '&#8364;</p>');

						qvrDiv
								.append('<button class="show_details_btn book_qvr_btn" id="book_qvr_'
										+ qvr.id + '">Reserve now</button>');
						counter++;
						qvrsDiv.append(qvrDiv);

					});

	$('.book_qvr_btn').on('click', function(e) {
		e.preventDefault();
		var qvrId = this.id.substring(9);
		
		alertify.confirm('Booking confirmation', 'Are you sure?', function() {
			reserveQvr(qvrId);
		}, function() {
			alertify.notify("Booking canceled");
		});				

	});

}

function reserveQvr(qvrId) {

	alertify.set('notifier', 'position', 'top-right');
	var user;
	if(localStorage.getItem("currentUser") == null)
	{
		alertify.error('You must first log in!');
		return;
	}
	
	$.ajax({
		type : 'POST',
		url : '/quickVehicleReservations/' + qvrId,
		contentType : 'application/json',
		dataType : 'json',
		data : JSON
				.stringify(JSON.parse(localStorage.getItem("currentUser")).id),
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function() {
			alertify.notify('Reserved!');
			findQuickVehicleReservations();
		},
		statusCode : {
			403 : function() {

				alertify.error('You must first log in!');
			},
			417 : function() {

				alertify.alert('Ooops, someone just take this vehicle!');
			},
			409 : function() {

				alertify.alert('Ooops, someone just take this vehicle. Please, try again!');
			}
		}
	});
}

function clearVehicleSearchData(){
	
	$('#searchType').val("");
	$('#searchMinPrice').val("");
	$('#searchMaxPrice').val("");
	$('#vehpickup').val("");
	$('#vehdropoff').val("");
}