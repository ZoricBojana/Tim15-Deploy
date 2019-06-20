findVehicles();

function findVehicles() {
	$.ajax({
		type : 'GET',
		url : "/vehicles",
		dataType : "json",
		success : fillTable,
		error : function(data) {
			alert(data);
		}
	});
}

function fillTable(data) {
	var veh_list = data == null ? []
			: (data instanceof Array ? data : [ data ]);
	var table = $('#tab-vehicles');
	$('#tab-vehicles').empty();
	$('#tab-vehicles')
			.append(
					'<tr><th>Id</th><th>Color</th><th>Type</th><th>Gear</th><th>Registration number</th></tr>');

	$.each(veh_list, function(index, vehicle) {

		var tr = $('<tr></tr>');
		var form = $('<td><form class="formsedit" id="form' + vehicle.id
				+ '"><input name="ident" value=' + vehicle.id
				+ ' readonly></form></td><td><input name="color" form="form'
				+ vehicle.id + '" value="' + vehicle.color
				+ '"></td><td><input name="type" form="form' + vehicle.id
				+ '" value="' + vehicle.type
				+ '"></td><td><input name="gear" form="form' + vehicle.id
				+ '" value="' + vehicle.gear
				+ '"></td><td><input name="registrationNumber" form="form'
				+ vehicle.id + '" value="' + vehicle.registrationNumber
				+ '"></td><td><input type="submit" form="form' + vehicle.id
				+ '" id="bform' + vehicle.id
				+ '"></td><td><button class="delBtns" id="delBtn' + vehicle.id
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
			url : "/vehicles/" + iden,
			success : function(response) {
				// alert("Vehicle deleted :)");
				window.location.href = "vehicles.html";
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
			url : "/vehicles/edit",
			contentType : 'application/json',
			dataType : 'json',
			data : jsonData,
			success : findVehicles,
			error : function(data) {
				alert(data);
			}
		});
	});

}
