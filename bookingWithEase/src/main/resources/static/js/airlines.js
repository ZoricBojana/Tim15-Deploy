findAirlines();

function showHideSearch() {
	var x = document.getElementById("div-air-search");
	if (x.style.display === "none") {
		x.style.display = "block";
		document.getElementById("showHideBtn").innerText = "Hide search";
	} else {
		x.style.display = "none";
		document.getElementById("showHideBtn").innerText = "Show search";
	}
}

function hello() {
	$.ajax({
		type : 'GET',
		url : "/users/myprofile",
		dataType : "json",
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(data) {
			console.log(data);
		},
		error : function(data) {
			alert(data);
		}
	});
}

function findAirlines() {
	$.ajax({
		type : 'GET',
		url : "/airlines",
		dataType : "json",
		beforeSend: function (xhr) {
	        /* Authorization header */
	        xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
	    },
		success : fillTable,
		error : function(data) {
			alert(data);
		}
	});
}
	
$(document).on('submit', '#formsrc', function(e) {
	e.preventDefault();
	var formData = getFormData("#formsrc");
	var jsonData = JSON.stringify(formData);
	$.ajax({
		type : 'POST',
		url : '/airlines/search',
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

function formToJSON() {
	var id = $('#src-air-id').val();
	var name = $('#src-air-name').val();
	var addr = $('#src-air-addr').val();
	console.log(JSON.stringify({
		"id" : id,
		"name" : name,
		"address" : addr
	}));
	return JSON.stringify({
		"id" : id,
		"name" : name,
		"address" : addr
	});
}

function fillTable(data) {
	var air_list = data == null ? []
			: (data instanceof Array ? data : [ data ]);

	var airsDiv = $('#airsDiv');
	airsDiv.empty();
	var counter = 0;

	$.each(
			air_list,
			function(index, air) {
				var airDiv = $('<div class="airDiv" id="airDiv_'
						+ counter
						+ '" style="bottom:'
						+ (60 - counter * 40)
						+ '%; top:'
						+ (3 + counter * 40)
						+ '%;"'
						+ '>'
						+ '<img src="../images/air.jpg" height = 90% width= 18%>'
						+ '<h3>' + air.name + '</h3>');

				airDiv.append('<p>'
								+ air.address
								+ '</p>'
								+ '<a href="javascript:showMap('+ air.latitude +' , '+ air.longitude +')"><img class="show_on_map" src="../images/show_on_map.png" height = 17 width= 18 ><div class="show_on_map">Show on map</div></a>'
								+ '</div>');
				airDiv.append('<div id="discountTickets" class="seat_number"><a class="discA" id="quick_'+air.id+'" href="">See discount tickets</a></div>');
				airDiv.append('<div class="guest_ratings"> Guest ratings: '
								+ (air.rating == null ? "no ratings"
										: air.rating) + ' / 5 </div>');
				airDiv.append('<button class="show_flights_btn" id="showf_' + air.id + '">Show flights</button>');

				counter++;
				airsDiv.append(airDiv);

			});
					
	$('.show_flights_btn').on('click', function(e) {
		e.preventDefault();
		var iden = this.id.substring(6);
		if (localStorage.getItem("showFlt") === null) {
			  localStorage.removeItem('showFlt');
			}
		localStorage.setItem('showFlt', iden);
		window.location.href = "flights.html";
	});
	
	$('.discA').on('click', function(e) {
		e.preventDefault();
		var iden = this.id.substring(6);
		if (localStorage.getItem("showFlt") === null) {
			  localStorage.removeItem('showFlt');
			}
		localStorage.setItem('showFlt', iden);
		window.location.href = "quickFlightReservation.html";
	});
}

function showAllFlights() {
	localStorage.removeItem('showFlt');
	window.location.href = "flights.html";
}







