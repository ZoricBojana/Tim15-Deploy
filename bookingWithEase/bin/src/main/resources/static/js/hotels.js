$(document).ready(function() {
    getHotels();
});


function getHotels()
{
	$.ajax({
		url : "/hotels",
		type : "GET",
		dataType : 'json',
		success : fillTable,
		error : function(response) {
			alert("Something went wrong! :(");
		}
	});
}

function fillTable(data) {
	var hotels = data == null ? []
			: (data instanceof Array ? data : [ data ]);
	$('#hotelsTable').empty();
	$('#hotelsTable').append('<tr><th>Name</th><th>Address</th><th>Description</th></tr>');
	$.each(hotels, function(index, hotel) {

		var tr = $('<tr></tr>');
		tr.append('<td>' + hotel.name + '</td>' + '<td>'
				+ hotel.address + '</td>' + '<td>'
				+ hotel.description + '</td>');

		$('#hotelsTable').append(tr);

	});

}