$(document).on('click', '#registerDestination', function(e){
    e.preventDefault();
    window.location.href = "RegistrationDestination.html";
});



$(document).on('click', '#destinationTable', function(e){
    e.preventDefault();
    window.location.href = "destination.html";
});

$(document).ready(function() {
    getAirlines();
});




function getAirlines() {
	$.ajax({
		type : 'GET',
		url : "/airlines",
		dataType : "json",
		success : fillTable,
		error : function(data) {
			alert(data);
		}
	});
}

function fillTable(data) {
	var airlines = data == null ? []
			: (data instanceof Array ? data : [ data ]);
	var table = $('#airlinesTable');
	$('#airlinesTable').empty();

	var cont = $('#help');
	cont.empty();
	var form = $('<form align =left><input value=ID><input value= NAME><input value = ADDRESS><input value=DESCRIPTION></form>');
	cont.append(form);

	$.each(airlines, function(index, airline) {
	
		var cont2 = $('<div></div>');
		var form = $('<form align=left class="formsedit" id="form' + airline.id
				+ '"><input name="ident" value="' + airline.id
				+ '" readonly><input name="name" value="' + airline.name
				+ '"><input name="address" value="' + airline.address
				+ '"><input name="description" value="' + airline.description
				+ '"><input type="submit" id="bform'+ airline.id +'"></form>');

		cont2.append(form);
		cont.append(cont2);
	}
	
	);

	
$('.formsedit').on('submit', function(e) {
		
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
		
		var jsonData = JSON.stringify(formData);
		console.log("ovde" + jsonData);
		$.ajax({
			type : 'POST',
			url : "/airlines/edit",
			contentType: 'application/json',
			dataType: 'json',
			data: jsonData,
			success : getAirlines,
			error : function(data) {
				alert(data);
			}
		});
	});
	

}