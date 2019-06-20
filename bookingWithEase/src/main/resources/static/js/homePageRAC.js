findVehicles();

$(document).ready(function() {

	var currentUser = JSON.parse(localStorage.getItem('currentUser'));

	getRAC(currentUser);
});

function fillSelect() {
	$.ajax({
		type : 'GET',
		url : "/rentacars/" + localStorage.getItem("userCompanyId")
				+ "/branchs",
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillOptions,
		error : function(data) {
			alert(data);
		}
	});
}

function fillOptions(data) {

	var opts = data == null ? [] : (data instanceof Array ? data : [ data ]);

	var select = $(localStorage.getItem('selectId'));

	$.each(opts, function(index, branch) {

		var option = $('<option name="' + branch.id + '" value="' + branch.id
				+ '">' + branch.name + '<option>');
		select.append(option);
	});
	localStorage.removeItem('selectId');
}

function openDiv(evt, divName) {

	if (divName === 'addVehicleDiv') {
		localStorage.setItem("selectId", '#selectbranch');
		fillSelect();
	}

	var i, tabcontent, tablinks;
	tabcontent = document.getElementsByClassName("tabcontent");
	for (i = 0; i < tabcontent.length; i++) {
		tabcontent[i].style.display = "none";
	}
	tablinks = document.getElementsByClassName("tablinks");
	for (i = 0; i < tablinks.length; i++) {
		tablinks[i].className = tablinks[i].className.replace(" active", "");
	}
	$('#' + divName).slideToggle(1000);
	evt.currentTarget.className += " active";
}

$(document).on('click', '#logoutClicked', function(e) {
	e.preventDefault();
	logout();
});

function getRAC(user) {
	var racId = user.company.id;

	$.ajax({
		url : "/rentacars/" + racId,
		type : "GET",
		dataType : 'json',
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillRacInfo,
		error : function(response) {
			alertify.alert("Something went wrong! :(");
		}
	});
}

function fillRacInfo(data) {
	$('#rac_name').empty().append(data.name);
	$('#rac_address').empty().append(data.address);
	$('#rac_latitude').empty().append(data.latitude);
	$('#rac_longitude').empty().append(data.longitude);
	$('#rac_description').empty().append(data.description);
	$('#rac_rating').empty().append(data.rating);

	$('#edit_rac_id').val(data.id);
	$('#edit_rac_name').val(data.name);
	$('#edit_rac_address').val(data.address);
	$('#edit_rac_latitude').val(data.latitude);
	$('#edit_rac_longitude').val(data.longitude);
	$('#edit_rac_description').val(data.description);

	$('#edit_rac_form').on(
			'submit',
			function(e) {

				e.preventDefault();
				var iden = this.id;

				var formData = getFormData("#edit_rac_form");
				var jsonData = JSON.stringify(formData);
		
				$.ajax({
					type : 'put',
					url : "/rentacars",
					contentType : 'application/json',
					dataType : 'json',
					data : jsonData,
					beforeSend : function(xhr) {
						/* Authorization header */
						xhr.setRequestHeader("Authorization", "Bearer "
								+ getJwtToken());
					},
					success : function(data){
						window.location.href = "homePageRAC.html";
						alertify.success("Saved");
					},
					error : function(data) {
						alertify.alert(data);
					}
				});

				//alertify.notify("Saved");
				getRAC(JSON.parse(localStorage.getItem('currentUser')));
			});

}

$(document).on('submit', '#addVehicleForm', function(e) {

	e.preventDefault();
	var formData = getFormData("#addVehicleForm");
	var jsonData = JSON.stringify(formData);
	console.log("Token sent " + getJwtToken());
	var iden = $('#selectbranch').val();
	if (iden === "") {
		alert('branch null');
		return;
	}
	$.ajax({
		url : "/branchs/" + iden + "/vehicles",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(response, HttpStatus) {
			alertify.success("Vehicle added");
			findVehicles();
		},
		statusCode : {
			409 : function() {
				alert('Already exists');
			},
			422 : function() {
				alert('Please enter all required fields');
			},
			500 : function() {
				alert('Internal server error');
			}
		},
	});

});

$(document).on('submit', '#editVehicleForm', function(e) {

	e.preventDefault();

	var formData = getFormData("#editVehicleForm");
	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/vehicles",
		type : "PUT",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : /*findVehicles*/ alertify.success("Vehicle updated"),
		error : function(response) {
			alert("Something went wrong! :(");
		}
	});

});

$(document).on(
		'submit',
		'#addBranchForm',
		function(e) {

			e.preventDefault();

			var formData = getFormData("#addBranchForm");
			var jsonData = JSON.stringify(formData);
			console.log("Token sent " + getJwtToken());
			$.ajax({
				url : "/rentacars/" + localStorage.getItem("userCompanyId")
						+ "/branchs",
				type : "POST",
				contentType : "application/json",
				data : jsonData,
				dataType : 'json',
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Bearer "
							+ getJwtToken());
				},
				success : /*findBranchs*/ alertify.success("Branch added"),
				statusCode : {
					409 : function() {
						alert('Already exists');
					},
					422 : function() {
						alert('Please enter all required fields');
					},
					500 : function() {
						alert('Internal server error');
					}
				}
			});

		});

$(document).on('submit', '#editBranchForm', function(e) {

	e.preventDefault();

	var formData = getFormData("#editBranchForm");
	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/branchs",
		type : "PUT",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : /*findBranchs*/ alertify.success("Branch edited"),
		statusCode : {
			409 : function() {
				alert('Already exists');
			},
			422 : function() {
				alert('Please enter all required fields');
			},
			500 : function() {
				alert('Internal server error');
			}
		},
	});

});

$(document).on('click', '#mybranchsbtn', function(e) {
	findBranchs();
});

$(document).on('click', '#myquickreservations', function(e) {
	findQuickReservations();
});

function findBranchs() {

	$.ajax({
		type : 'GET',
		url : "/rentacars/" + localStorage.getItem("userCompanyId")
				+ "/branchs",
		dataType : "json",
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillBranchTable,
		error : function(data) {
			alert(data);
		}
	});
}

function fillBranchTable(data) {
	var br_list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#tableCap').html("My branchs");
	$('#branchSearch').empty();
	var table = $('#allBranchesTable');
	table.empty();
	table.append(
			'<tr><th>Id</th><th>Name</th><th>Address</th></tr>');

	$
			.each(
					br_list,
					function(index, branch) {

						var tr = $('<tr></tr>');
						var form = $('<td>'
								+ branch.id
								+ '</td><td>'
								+ branch.name
								+ '</td><td>'
								+ branch.address
								+ '</td><td><button class="beditBtns" id="beditbtn'
								+ branch.id
								+ '">Edit</button></td><td><button class="bdelBtns" id="bdelBtn'
								+ branch.id + '">Delete</button></td>');
						tr.append(form);
						table.append(tr);
					});

	$('.bdelBtns').on(
			'click',
			function(e) {
				e.preventDefault();
				var iden = this.id.substring(7);

				alertify.confirm("Delete branch", "Are you sure?", function() {
					$.ajax({
						type : 'delete',
						url : "/branchs/" + iden,
						beforeSend : function(xhr) {
							xhr.setRequestHeader("Authorization", "Bearer "
									+ getJwtToken());
						},
						success : function(response) {
							findBranchs();
						},
						error : function(data) {
							alert(data);
						}
					});
					alertify.notify('Branch deleted', 'success', 2);
				}, function() {
				});
			});

	$('.beditBtns').on(
			'click',
			function(e) {
				e.preventDefault();
				var iden = this.id;
				openDiv(event, 'editBranchDiv');
				localStorage.setItem("editBranchId", iden);

				$.ajax({
					type : 'GET',
					url : "/branchs/" + iden.substring(8),
					dataType : "json",
					beforeSend : function(xhr) {
						xhr.setRequestHeader("Authorization", "Bearer "
								+ getJwtToken());
					},
					success : function(data) {
						$('#editIdBr').val(data.id);
						$('#editNameBr').val(data.name);
						$('#editAddrBr').val(data.address);
					},
					error : function(data) {
						alert(data);
					}
				});

			});

}

$(document).on('click', '#myvehiclesbtn', function(e) {
	findVehicles();
});

function findVehicles() {

	$.ajax({
		type : 'GET',
		url : "/vehicles",
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillVehicleTable,
		error : function(data) {
			alert(data);
		}
	});
}

function fillVehicleTable(data) {
	var vh_list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#tableCap').html("My Vehicles");
	$('#branchSearch').empty();
	var select = $('<select id="selectBranchSearch"><option> </option></select>')
	$('#branchSearch').append('Branch: ');
	$('#branchSearch').append(select);
	localStorage.setItem("selectId", '#selectBranchSearch')
	fillSelect();

	$('#selectBranchSearch').change(
			function() {
				var value = $(this).val();
				if (!$.trim(value)) {
					findVehicles();
				} else {
					$.ajax({
						type : 'GET',
						url : "/branchs/" + value + "/vehicles",
						dataType : "json",
						beforeSend : function(xhr) {
							xhr.setRequestHeader("Authorization", "Bearer "
									+ getJwtToken());
						},
						success : fillVehicleTable,
						error : function(data) {
							alert(data);
						}
					});
				}
			});

	var table = $('#allVehiclesTable');
	table.empty();
	table
			.append(
					'<tr><th>Id</th><th>Registration number</th><th>Type</th><th>Gear</th><th>Color</th></tr>');

	$.each(vh_list, function(index, vehicle) {

		var tr = $('<tr></tr>');
		var form = $('<td>' + vehicle.id + '</td><td>'
				+ vehicle.registrationNumber + '</td><td>' + vehicle.type
				+ '</td><td>' + vehicle.gear + '</td><td>' + vehicle.color
				+ '</td><td><button class="veditBtns" id="veditbtn'
				+ vehicle.id + '">Edit</button></td>');
		tr.append(form);
		if (vehicle.vehicleReservations.length == 0) {
			tr.append($('<td><button class="vdelBtns" id="vdelBtn' + vehicle.id
					+ '">Delete</button></td>'));
		}
		table.append(tr);
	});

	$('.vdelBtns').on(
			'click',
			function(e) {

				e.preventDefault();
				var iden = this.id.substring(7);
				alertify.confirm("Vehicle delete", "Are you sure?", function() {

					console.log(iden);

					$.ajax({
						type : 'delete',
						url : "/vehicles/" + iden,
						beforeSend : function(xhr) {
							xhr.setRequestHeader("Authorization", "Bearer "
									+ getJwtToken());
						},
						success : function(response) {
							findVehicles();
						},
						error : function(data) {
							alert(data);
						}
					});
					alertify.success('Ok', 2);
				}, function() {
					alertify.error('Cancel', 2);
				});

			});

	$('.veditBtns').on(
			'click',
			function(e) {
				e.preventDefault();
				var iden = this.id;
				openDiv(event, 'editVehicleDiv');

				$.ajax({
					type : 'GET',
					url : "/vehicles/" + iden.substring(8),
					dataType : "json",
					beforeSend : function(xhr) {
						xhr.setRequestHeader("Authorization", "Bearer "
								+ getJwtToken());
					},
					success : function(data) {
						$('#editIdVh').val(data.id);
						$('#editRegVh').val(data.registrationNumber);
						$('#editTypeVh').val(data.type);
						$('#editGearVh').val(data.gear);
						$('#editColorVh').val(data.color);
						$('#editPriceVh').val(data.pricePerDay);
					},
					error : function(data) {
						alert(data);
					}
				});

			});

}

$(document).on('submit', '#addOfferForm', function(e) {

	e.preventDefault();
	
	if(!validateRACSOData()){
		return;
	}
	
	var formData = getFormData("#addOfferForm");
	var jsonData = JSON.stringify(formData);
	iden = localStorage.getItem('userCompanyId');
	$.ajax({
		url : "/rentacars/" + iden + "/specialOffers",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(response, HttpStatus) {
			console.log(HttpStatus);
			findOffers();
			alertify.success("Offer added");
			$('#soracname').val("");
			$('#special_offer_description').val("");
			$('#inputPrice').val("");
		},
		statusCode : {
			409 : function() {
				alert('Already exists');
			},
			422 : function() {
				alert('Please enter all required fields');
			},
			500 : function() {
				alert('Internal server error');
			}
		},
	});

});

function validateRACSOData(){
	var price = $('#inputPrice').val();
	if(!$.isNumeric(price)){
		alertify.alert("Invalid input", "Price must be a number");
		return false;
	}
	
	return true;
}

$(document).on(
		'submit',
		'#editOfferForm',
		function(e) {

			e.preventDefault();

			var formData = getFormData("#editOfferForm");
			var jsonData = JSON.stringify(formData);

			$.ajax({
				url : "/rentacars/" + localStorage.getItem('userCompanyId')
						+ "/specialOffers",
				type : "PUT",
				contentType : "application/json",
				data : jsonData,
				dataType : 'json',
				beforeSend : function(xhr) {
					xhr.setRequestHeader("Authorization", "Bearer "
							+ getJwtToken());
				},
				success : findOffers,
				error : function(response) {
					alert("Something went wrong! :(");
				}
			});

		});

$(document).on('click', '#myoffersbtn', function(e) {
	findOffers();
});

function findOffers() {

	$.ajax({
		type : 'GET',
		url : "/rentacars/" + localStorage.getItem('userCompanyId')
				+ "/specialOffers",
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillOfferTable,
		error : function(data) {
			alert(data);
		}
	});
}

function fillOfferTable(data) {
	var of_list = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#tableCap').html("My Offers");
	$('#branchSearch').empty();
	var table = $('#specialOffersTable');
	table.empty();
	table
			.append(
					'<tr><th>Id</th><th>Name</th><th>Description</th><th>Price</th></tr>');

	$
			.each(
					of_list,
					function(index, offer) {

						var tr = $('<tr></tr>');
						var form = $('<td>'
								+ offer.id
								+ '</td><td>'
								+ offer.name
								+ '</td><td>'
								+ offer.description
								+ '</td><td>'
								+ offer.price
								+ '</td><td><button class="oeditBtns" id="oeditbtn'
								+ offer.id
								+ '">Edit</button></td><td><button class="odelBtns" id="odelBtn'
								+ offer.id + '">Delete</button></td>');
						tr.append(form);
						table.append(tr);
					});

	$('.odelBtns').on(
			'click',
			function(e) {

				e.preventDefault();
				var iden = this.id.substring(7);
				alertify.confirm("Offer delete", "Are you sure?", function() {

					console.log(iden);

					$.ajax({
						type : 'delete',
						url : "/rentacars/specialOffers/" + iden,
						beforeSend : function(xhr) {
							xhr.setRequestHeader("Authorization", "Bearer "
									+ getJwtToken());
						},
						success : function() {
							findOffers();
						},
						error : function(data) {
							alert(data);
						}
					});
					alertify.success('Ok', 2);
				}, function() {
					alertify.error('Cancel', 2);
				});

			});

	$('.oeditBtns').on(
			'click',
			function(e) {
				e.preventDefault();
				var iden = this.id;
				openDiv(event, 'editOfferDiv');

				$.ajax({
					type : 'GET',
					url : "/rentacars/" + localStorage.getItem('userCompanyId')
							+ "/specialOffers/" + iden.substring(8),
					dataType : "json",
					beforeSend : function(xhr) {
						xhr.setRequestHeader("Authorization", "Bearer "
								+ getJwtToken());
					},
					success : function(data) {
						$('#editIdOf').val(data.id);
						$('#editNameOf').val(data.name);
						$('#editDescOf').val(data.description);
						$('#editPriceOf').val(data.price);
					},
					error : function(data) {
						alert(data);
					}
				});

			});

}

// quick reservations

function findQuickReservations() {

	$.ajax({
		type : 'GET',
		url : "/rentacars/" + localStorage.getItem("userCompanyId")
				+ "/quickReservations",
		dataType : "json",
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillQuickReservationsTable,
		error : function(data) {
			alert(data);
		}
	});
}

function fillQuickReservationsTable(data) {
	var qr_list = data == null ? [] : (data instanceof Array ? data : [ data ]);

	var table = $('#quickReservationsTable');
	$('#quickReservationsTable').empty();
	$('#quickReservationsTable')
			.append(
					'<tr><th>Id</th><th>Vehicle Id</th><th>Pick Up</th><th>Drop Off</th><th>Discount</th><th>Final Price</th></tr>');

	$
			.each(
					qr_list,
					function(index, qres) {

						var tr = $('<tr></tr>');
						var form = $('<td>'
								+ qres.id
								+ '</td><td>'
								+ qres.vehicle.vehicleId
								+ '</td><td>'
								+ qres.checkInDate.substring(0, 10)
								+ '</td><td>'
								+ qres.checkOutDate.substring(0, 10)
								+ '</td><td>'
								+ qres.discount
								+ '</td><td>'
								+ qres.finalPrice
								+ '</td><td><button class="qeditBtns" id="qeditbtn'
								+ qres.id
								+ '">Edit</button></td><td><button class="qdelBtns" id="qdelBtn'
								+ qres.id + '">Delete</button></td>');
						tr.append(form);
						table.append(tr);
					});

	$('.qdelBtns').on(
			'click',
			function(e) {
				e.preventDefault();
				var iden = this.id.substring(7);

				alertify.confirm("Delete Quick Reservation", "Are you sure?",
						function() {
							$.ajax({
								type : 'delete',
								url : "/vehicleReservations/" + iden,
								beforeSend : function(xhr) {
									xhr.setRequestHeader("Authorization",
											"Bearer " + getJwtToken());
								},
								success : function(response) {
									findQuickReservations();
								},
								error : function(data) {
									alert(data);
								}
							});
							alertify.notify('Quick Reservation Deleted',
									'success', 2);
						}, function() {
						});
			});

	$('.qeditBtns').on(
			'click',
			function(e) {
				e.preventDefault();
				var iden = this.id;
				openDiv(event, 'editQuickReservationDiv');
				localStorage.setItem("editQuickReservationId", iden);

				$.ajax({
					type : 'GET',
					url : "/quickVehicleReservations/" + iden.substring(8),
					dataType : "json",
					beforeSend : function(xhr) {
						xhr.setRequestHeader("Authorization", "Bearer "
								+ getJwtToken());
					},
					success : function(data) {
						$('#editIdQr').val(data.id);
						$('#editNameQr').val(data.name);
						$('#editAddrQr').val(data.address);
					},
					error : function(data) {
						alert(data);
					}
				});

			});

}

$(document).on('change', '.qvrDate', function(e) {

	var newCheckIn = $('#qvr_checkIn').val();
	var newCheckOut = $('#qvr_checkOut').val();
	if (newCheckIn === "" || newCheckOut === "") {
		return;
	}

	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var racId = currentUser.company.id;

	var formData = {};
	// formData["floorNumber"] = -11;
	formData["rentacarId"] = racId;
	formData["type"] = "";
	formData["gear"] = "";
	formData["pickUp"] = newCheckIn;
	formData["dropOff"] = newCheckOut;

	searchVehiclesQvr(formData);

});

function searchVehiclesQvr(formData) {

	var jsonData = JSON.stringify(formData);
	// alert(jsonData);
	$.ajax({
		url : "/rentacars/vehicles/search",
		type : "POST",
		dataType : 'json',
		contentType : "application/json",
		data : jsonData,
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : addVehiclesToQrr,
		error : function(response) {
			alert("Something went wronggg! :(");
		}
	});
}

function addVehiclesToQrr(data) {
	// alert(JSON.stringify(data));
	var vehs = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#qvr_vehicles').empty();
	var counter = 0;
	$
			.each(
					vehs,
					function(index, veh) {

						counter++;
						var newItem = $('<div align="left"></div');
						newItem
								.append('<input type="checkbox" class= "qvr_vehicle_checkbox" name="qvr_vehicle_checkbox'
										+ veh.id
										+ '" value="'
										+ veh.id
										+ '" />'
										+ veh.registrationNumber
										+ " "
										+ veh.type);
						$('#qvr_vehicles').append(newItem);

					});

	if (counter == 0)
		$('#qvr_vehicles').append("none available");
}

$(document).on(
		'click',
		'#addQuickVehicleReservationBtn',
		function(e) {
			e.preventDefault();
			
			if(!validateQVRData()){
				return;
			}
			
			var formData = getFormData('#addQuickVehicleReservationForm');

			var newFormData = {};
			var vehicles = [];

			for ( var el in formData) {
				if (el.startsWith('qvr_vehicle_')) {
					vehicles.push(formData[el]);

				} else if (!el.startsWith('start_date_')
						&& !el.startsWith('end_date')) {
					newFormData[el] = formData[el];
				}
			}

			newFormData["vehicles"] = vehicles;

			var currentUser = JSON.parse(localStorage.getItem('currentUser'));
			var racId = currentUser.company.id;

			addQuickVehicleReservations(racId, newFormData);

		});

function validateQVRData(){
	var date1 = $('#qvr_checkIn').val();
	var date2 = $('#qvr_checkOut').val();
	var now = new Date();
	if(new Date(date1).getTime() < now.getTime()){
		alertify.alert("Date not valid", "Pick up date is past");
		return false;
	}
	
	if(date2 < date1){
		alertify.alert("Date not valid", "Drop off date must be greater than pick up date");
		return false;
	}
	
	var disc = $('#qvr_discount').val();
	if(!$.isNumeric(disc)){
		alertify.alert("Invalid input", "Discount must be a number");
		return false;
	}
	return true;
}

function addQuickVehicleReservations(racId, formData) {

	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/rentacars/" + racId + "/quickReservations",
		type : "POST",
		dataType : 'json',
		contentType : 'application/json',
		data : jsonData,
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(data) {
			alertify.success("Quick Room Reservation Saved!");
		},
		statusCode : {
			200 : function() {
				alertify.success("Quick Room Reservation Saved!");
			}
		}
	});
}


$(document).on('submit', '#reportDatesForm', function(e) {

	e.preventDefault();

	var currentUser = JSON.parse(localStorage.getItem('currentUser'));
	var racId = currentUser.company.id;
	
	var formData = getFormData("#reportDatesForm");
	var jsonData = JSON.stringify(formData);

	$.ajax({
		url : "/rentacars/" + racId + "/income",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(data){
			$("#totalIncomeH2").html("Total income: " + data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("Something went wrong! :( " + errorThrown);
		}
	});
	
	$.ajax({
		url : "/rentacars/" + racId + "/avgRatePeriod",
		type : "POST",
		contentType : "application/json",
		data : jsonData,
		dataType : 'json',
		beforeSend : function(xhr) {
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : function(data){
			$("#avgRateH2").html("Average company rate: " + data);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert("Something went wrong! :( " + errorThrown);
		}
	});

});