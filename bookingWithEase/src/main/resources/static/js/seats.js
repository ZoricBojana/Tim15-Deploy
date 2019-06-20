findSeats();

function findSeats() {
	$.ajax({
		type : 'GET',
		url : localStorage.getItem('showSeats') != null ? "/seats/flight/" + localStorage.getItem('showSeats') : "/seats",
		dataType : "json",
		beforeSend: function (xhr) {
	        /* Authorization header */
	        xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
	    },
		success : drawSeats,
		error : function(data) {
			alert(data);
		}
	});
}

function drawSeats(data) {
	localStorage.removeItem('selectedSeats');
	
	var seats_list = data == null ? [] : (data instanceof Array ? data : [ data ]);

	var seatsTableLeft = $('#seatsTableLeft');
	var seatsTableRight = $('#seatsTableRight');
	seatsTableLeft.empty();
	seatsTableRight.empty();
	
	var seatTableLeft = "";
	var seatTableRight = "";
	
	var counter = 0;
	var seatSpliter = 4.0;
	
	$.each(seats_list, function(index, seat) {
		if(counter % seatSpliter == 0){
			seatTableLeft += '<tr>';
			seatTableRight += '<tr>';
		}
		
		if(seat.seatNumber % seatSpliter <= 2){
			seatTableLeft += '<td><button id="seat_' + seat.seatNumber + '" class="av_' + seat.available + '_type_' + seat.type + '">' + seat.seatNumber + '</button></td>';
		}else{
			seatTableRight += '<td><button id="seat_' + seat.seatNumber + '" class="av_' + seat.available + '_type_' + seat.type + '">' + seat.seatNumber + '</button></td>';
		}
		
		if(counter % seatSpliter == 3){
			seatTableLeft += '</tr>';
			seatTableRight += '</tr>';
		}

		counter++;
	});
			
	seatsTableLeft.append(seatTableLeft);
	seatsTableRight.append(seatTableRight);
	
	$("button[class^='av_true']").on('click', function(e) {
		e.preventDefault();
		var selected_seats_list = localStorage.getItem("selectedSeats") != null ? JSON.parse(localStorage.getItem("selectedSeats")) : [];
		var idSeat = this.id.substring(5);
		var class_name = this.className;
		
		if(class_name.includes("_selected")){
			for(var i = 0; i < selected_seats_list.length; i++){ 
				if (selected_seats_list[i] === idSeat) {
					selected_seats_list.splice(i, 1);
					
					var lastIndex = class_name.lastIndexOf("_selected");
					this.className = class_name.substring(0, lastIndex);
					break;
				}
			}
		}else{
			selected_seats_list.push(idSeat);
			
			this.className = class_name + "_selected";
		}
		
		localStorage.setItem('selectedSeats', JSON.stringify(selected_seats_list));
	});
}

function back() {
	window.location.href = "flights.html";
}

function next() {
	window.location.href = "friends.html";
}


