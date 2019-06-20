alertify.set('notifier', 'position', 'top-right');
$(document).on('click', '#cancelAdminReg', function(e) {
	e.preventDefault();
	$('.transbox_admin_reg').css('opacity', '0');
});

$(document).on('click', '#registerAdmin', function(e) {
	e.preventDefault();
	adminRegistration();
});

$(document).on('click', '#saveDiscounts', function(e) {
	e.preventDefault();

	var disc = JSON.parse(localStorage.getItem("discounts"));
	disc.price = $("#discount-price").val();
	disc.points = $("#discount-points").val();
	
	$('#discount-price').val(disc.price);
	$('#discount-points').val(disc.points);
	
	updateDiscount(disc, function(){alertify.notify("Saved"); localStorage.setItem("discount", disc);});
		
});

$(document).on('click', '#addDiscount', function(e) {
	e.preventDefault();
	var disc = JSON.parse(localStorage.getItem("discounts"));
	
	var d = {};
	d["points"] = $('#discount-points2').val();
	d["discount"] = $('#discount-discount').val();

	$('#disc_table').append('<tr><td>' + d.points + '</td><td>' + d.discount+ ' %</td>' +
	 '<td><button class="delete_discount" >Delete</button></td></tr>');
	alertify.notify("Added");
	
});

function allUsers() {
	window.location.href = "users.html";
}

function adminRegistrationChoosen() {

	$('.transbox_company_reg').css('opacity', '0');
	$('.transbox_admin_reg').css('opacity', '0.9');
	$('.transbox_discounts').css('opacity', '0');

	$('.transbox_admin_reg').css('z-index', '3');
	$('.transbox_company_reg').css('z-index', '2');
	$('.transbox_discounts').css('z-index', '2');
	
	$('#myprofilediv').css('display', 'none');
	$('#changepswdiv').css('display', 'none');
	
	$('input:radio[name="adminType"]').change(function() {
		if ($(this).val() == 'airline') {
			getAirlines();
		} else if ($(this).val() == 'hotel') {
			getHotels(fillHotels);
		} else if ($(this).val() == 'rent-a-car') {
			getRentacars();
		}
	});
}

function fillAdmins(adminType) {
	getUsers();

}

function myProfileChoosen()
{
	$('.transbox_admin_reg').css('opacity', '0');
	$('.transbox_company_reg').css('opacity', '0');
	$('.transbox_discounts').css('opacity', '0');
	openCity(event, 'myprofilediv');
}

function companyRegistrationChoosen() {
	$('.transbox_admin_reg').css('opacity', '0');
	$('.transbox_company_reg').css('opacity', '0.9');
	$('.transbox_discounts').css('opacity', '0');

	$('.transbox_company_reg').css('z-index', '3');
	$('.transbox_admin_reg').css('z-index', '2');
	$('.transbox_discounts').css('z-index', '2');
	
	$('#myprofilediv').css('display', 'none');
	$('#changepswdiv').css('display', 'none');

	$('input:radio[name="cmpType"]').change(function() {
		if ($(this).val() == 'airline') {
			adminType = "AIRLINE";
		} else if ($(this).val() == 'hotel') {
			adminType = "HOTEL";
		} else if ($(this).val() == 'rentacar') {
			adminType = "RENT_A_CAR";
		}

		fillAdmins($(this).val());
	});
}

function discountsChoosen() {
	$('.transbox_admin_reg').css('opacity', '0');
	$('.transbox_company_reg').css('opacity', '0');
	$('.transbox_discounts').css('opacity', '0.9');

	$('.transbox_company_reg').css('z-index', '2');
	$('.transbox_admin_reg').css('z-index', '2');
	$('.transbox_discounts').css('z-index', '3');
	
	$('#myprofilediv').css('display', 'none');
	$('#changepswdiv').css('display', 'none');
	
	getDiscounts(fillDiscounts);
	
	
	
}

function fillDiscounts(data)
{
	//alert("fill discounts " + JSON.stringify(data));
	localStorage.setItem('discounts', JSON.stringify(data));
	//alert("a " + localStorage.getItem('discounts'));
	$('#discount-price').val(data[0].price);
	$('#discount-points').val(data[0].points);
	
	if(data[0].discounts < 1){return;}
	
	var table = $('#disc_table');
	table.empty();
	table.append('<th>Points</th><th>Discount</th><th>&nbsp;</th>');
	
	$.each(data[0].discounts, function(index, d) {

		table.append('<tr><td>' + d.points + '</td><td>' + d.discount+ ' %</td>' +
					 '<td><button class="delete_discount" >Delete</button></td></tr>');
	});
	
}

function adminRegistration() {
	var formData = getFormData("#adminRegForm");
	var radiobtns = $('.companies_radio');

	$.each(radiobtns, function(index, btn) {
		if (btn.checked) {
			formData["companyId"] = btn.value;
		}
	});

	var validData = Boolean(validateAdminRegData(formData));
	
	if (validData) {
		var jsonData = JSON.stringify(formData);
		
		$.ajax({
			url : "/users",
			type : "POST",
			contentType : "application/json",
			data : jsonData,
			dataType : 'json',
			beforeSend : function(xhr) {
				/* Authorization header */
				xhr
						.setRequestHeader("Authorization", "Bearer "
								+ getJwtToken());
			},
			success : function(response) {
				alertify.notify("Admin user saved :)");
				window.location.href = "index.html";
			},
			error : function(response) {
				if (response.status == 409) {
					alertify.notify("Username is already taken");
				} else {
					alertify.notify("Admin user saved :)");
				}

			}
		});
	} else {

	}
}

function validateAdminRegData(formData) {

	var username = formData["username"];
	var firstName = formData["firstName"];
	var lastName = formData["lastName"];
	var email = formData["email"];
	var password = formData["password"];
	var confirmedPassword = formData["confirmPassword"];
	var type = formData["adminType"];

	// alert("username: " + username + " first: " + firstName + " last: "
	// + lastName + " email: " + email + " pass: " + password + " pass2: "
	// + confirmedPassword + " type: " + type);

	if (username === "" || firstName === "" || lastName === "" || email === ""
			|| email === "" || type === undefined) {
		alertify.notify("Please fill in all fields");
		return false;
	}

	if (!validateEmail(email)) {
		alertify.notify("Please insert a valid email address");
		return false;
	}

	if (password !== confirmedPassword) {
		alertify.notify("Entered passwords are not equal");
		return false;
	}

	if (password.length < 8) {
		alertify.notify("Password must contain at least 8 characters");
		return false;
	}

	return true;
}

function validateEmail(email) {
	var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}

function saveUsers(data) {
	var users = data == null ? [] : (data instanceof Array ? data : [ data ]);

	$('#select_admins').empty();
	var counter = 0;
	$
			.each(
					users,
					function(index, user) {

					//	alert(user.type + " " + user.username + " company " + user.company);
						if (user.type == adminType && user.company == null) {
							counter++;
							var newItem = $('<div align="left"></div');
							newItem
									.append('<input type="checkbox" class= "admins_checkbox" name="admins_checkbox" value="'
											+ user.username
											+ '" />'
											+ user.username);
							$('#select_admins').append(newItem);

						}
					});

	if (counter == 0)
		$('#select_admins').append("none available");
}

function getUsers() {
	$.ajax({
		url : "/users",
		type : "GET",
		dataType : 'json',
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : saveUsers,
		error : function(response) {
			alert("Something went wrong when trying to get users! :(");
		}
	});
}

function getDiscounts(callback) {
	$.ajax({
		url : "/discounts",
		type : "GET",
		dataType : 'json',
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : callback,
		error : function(response) {
			alert("Something went wrong when trying to get discounts! :(");
		}
	});
}

function updateDiscount(data, callback)
{
	
	callback();
}

function fillHotels(data) {
	var hotels = data == null ? [] : (data instanceof Array ? data : [ data ]);
	$('#select_company').empty();
	var counter = 0;
	$
			.each(
					hotels,
					function(index, hotel) {

						counter++;
						var newItem = $('<div align="left"></div');
						newItem
								.append('<input type="radio" class= "companies_radio" name="companies_radio" value="'
										+ hotel.id
										+ '" />'
										+ hotel.name
										+ ", "
										+ hotel.address);
						$('#select_company').append(newItem);

					});

	if (counter == 0)
		$('#select_company').append("none available");
}

function getAirlines() {
	$.ajax({
		type : 'GET',
		url : "/airlines",
		dataType : "json",
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillHotels,
		error : function(data) {
			alert(data);
		}
	});
}

function getRentacars() {
	$.ajax({
		type : 'GET',
		url : "/rentacars",
		dataType : "json",
		beforeSend : function(xhr) {
			/* Authorization header */
			xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
		},
		success : fillHotels,
		error : function(data) {
			alert(data);
		}
	});
}

