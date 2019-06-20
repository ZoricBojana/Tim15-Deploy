findFlights();

function findFlights() {
	$.ajax({
		type : 'GET',
		url : "/flights",
		dataType : "json",
		success : fillTable,
		error : function(data) {
			alert(data);
		}
	});
}

function fillTable(data) {
	var f_list = data == null ? []
			: (data instanceof Array ? data : [ data ]);
	var table = $('#tab-flights');
	$('#tab-flights').empty();
	$('#tab-flights')
			.append(
					'<tr><th>ID</th><th>Start destination</th><th>End destination</th><th>Date flight</th><th>Date landing</th><th>Length travel</th><th>Price ticket</th></tr>');

	$.each(f_list, function(index, flight) {

		var tr = $('<tr></tr>');
		var form = $('<td><form class="formsedit" id="form' + flight.id
				+ '"><input name="ident" value=' + flight.id
				+ ' readonly></form></td><td><input name="startD" form="form'
				+ flight.id + '" value="' + flight.startD
				+ '"></td><td><input name="finalD" form="form' + flight.id
				+ '" value="' + flight.finalD
				+ '"></td><td><input name="dateFligh" form="form' + flight.id
				+ '" value="' + flight.dateFligh
				+ '"></td><td><input name="dateLand" form="form'
				+ flight.id + '" value="' + flight.dateLand
				+ '"></td><td><input name="lengthTravel" form="form' + flight.id
				+ '" value="' + flight.lengthTravel
				+ '"></td><td><input name="priceTicket" form="form'+ flight.id
				+ '" value="' + flight.priceTicket
				+ '"></td><td><button class="delBtns" id="delBtn' + flight.id
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
			type : 'delete',
			url : "/flights/" + iden,
			success : function(response) {
				// alert("Flight deleted :)");
				window.location.href = "flights.html";
			},
			error : function(data) {
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
			if (record.name === "ident") {
				formData["id"] = record.value;
			} else {
				formData[record.name] = record.value;
			}
		}

		var jsonData = JSON.stringify(formData);
		$.ajax({
			type : 'post',
			url : "/flights/edit",
			contentType : 'application/json',
			dataType : 'json',
			data : jsonData,
			success : findFlights,
			error : function(data) {
				alert(data);
			}
		});
	});

}
