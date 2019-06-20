findRentacars();

function showHideSearch() {
	var x = document.getElementById("div-rac-search");
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

function findRentacars() {
	$.ajax({
		type : 'GET',
		url : "/rentacars",
		dataType : "json",
		beforeSend : function(xhr) {
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
	
	if(!validateRACSearchData()){
		return;
	}
	
	var begin = $('#srcRacPickUp').val();
	var end = $('#srcRacDropOff').val();
	localStorage.setItem("vehicleBegin", begin);
	localStorage.setItem("vehicleEnd", end);
	var formData = getFormData("#formsrc");
	var jsonData = JSON.stringify(formData);
	$.ajax({
		type : 'POST',
		url : '/rentacars/search',
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

function validateRACSearchData(){
	var date1 = $('#srcRacPickUp').val();
	var date2 = $('#srcRacDropOff').val();
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

function formToJSON() {
	var id = $('#src-rac-id').val();
	var name = $('#src-rac-name').val();
	var addr = $('#src-rac-addr').val();
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
	var rac_list = data == null ? []
			: (data instanceof Array ? data : [ data ]);

	var racsDiv = $('#racsDiv');
	racsDiv.empty();
	var counter = 0;

	$
			.each(
					rac_list,
					function(index, rac) {
						var racDiv = $('<div class="company-div" id="racDiv_'
								+ counter
								+ '" style="bottom:'
								+ (60 - counter * 40)
								+ '%; top:'
								+ (3 + counter * 40)
								+ '%;"'
								+ '>'
								+ '<img src="../images/cars.jpg" height = 90% width= 18%>'
								+ '<h3>' + rac.name + '</h3>');

						racDiv
								.append('<p>'
										+ rac.address
										+ '</p>'
										+ '<a href="javascript:showMap('+ rac.latitude +' , '+ rac.longitude +')"><img class="show_on_map" src="../images/show_on_map.png" height = 17 width= 18 ><div class="show_on_map">Show on map</div></a>'
										+ '</div>');
						racDiv
								.append('<p style="position: absolute;top:65%;left:25%;">'
										+ rac.description + '</p>');
						racDiv
								.append('<div class="guest_ratings"> Guest ratings: '
										+ (rac.rating == null ? " no ratings" : rac.rating)
										+ ' / 5 </div>');
						racDiv
								.append('<button class="show_details_btn" id="showv_'
										+ rac.id + '">Show vehicles</button>');

						counter++;
						racsDiv.append(racDiv);

					});

	$('.show_details_btn').on('click', function(e) {
		e.preventDefault();
		var iden = this.id.substring(6);
		if (localStorage.getItem("showVeh") === null) {
			localStorage.removeItem('showVeh');
		}
		localStorage.setItem('showVeh', iden);
		window.location.href = "vehicles.html";
	});

}