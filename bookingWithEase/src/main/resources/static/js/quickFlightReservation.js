findReservations();

function findReservations() {
	$.ajax({
		type: 'GET',
		url: "/quickFlightReservation/" + localStorage.getItem('showFlt') + "/quickReservations",
		//url: "/quickFlightReservation/" + 1 + "/quickReservations",
		dataType: "json",
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
	var reservations_list = data == null ? [] : (data instanceof Array ? data : [data]);

	var airsDiv = $('#airsDiv');
	airsDiv.empty();
	var counter = 0;

	$.each(reservations_list, function(index, reservation) {
		var airDiv = $('<div class="airDiv" id="airDiv'
			+ counter
			+ '" style="bottom:'
			+ (60 - counter * 40)
			+ '%; top:'
			+ (3 + counter * 40)
			+ '%;"'
			+ '>'
			+ '<img src="../images/air.jpg" height = 90% width= 18%>'
			+ '<h3>' + reservation.startD + ' - ' + reservation.finalD + '</h3>');

		airDiv.append('<p>Date: ' + reservation.dateFligh + '</p></div>');
		airDiv.append('<div class="seat_number"><p>Seat: ' + reservation.seatId + '</p></div>');
		airDiv.append('<div class="guest_ratings">Original price: ' + reservation.originalPrice + '€' + '</div>');
		airDiv.append('<div class="flight_duration">Discount: ' + reservation.discount + '%' + '</div>');
		airDiv.append('<div class="current_price">Current price: ' + reservation.currentPrice + '€' + '</div>');
		airDiv.append('<button class="show_flights_btn" id="quickReserveFli_' + reservation.qfrId + '">Reserve</button>');

		counter++;
		airsDiv.append(airDiv);

	});

	$('.show_flights_btn').on('click', function(e) {
		//e.preventDefault();
		var iden = this.id.substring(16);

		var currentUserId = JSON.parse(localStorage.getItem('userId'));
		$.ajax({
			type: 'GET',
			url: '/quickFlightReservation/' + iden + '/' + currentUserId,
			contentType: 'application/json',
			dataType: 'json',
			beforeSend: function(xhr) {
				/* Authorization header */
				xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
			},
			success: function(data) {
			},
			error: function() {
				alert('Login to reservate!');
			}
		});

		window.location.href = "index.html";
	});

}