findFriends();

function findFriends() {
	$.ajax({
		type : 'GET',
		url : "/users/friends/" + JSON.parse(localStorage.getItem('userId')),
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

function fillTable(data) {
	localStorage.removeItem('invFriends');
	
	var friends_list = data == null ? []
			: (data instanceof Array ? data : [ data ]);

	var friendsDiv = $('#friendsDiv');
	friendsDiv.empty();
	
	var friendDiv = "";
	friendDiv += '<h2>All friends:</h2>';
	friendDiv += '<table id=friendTable><tr><td>First name</td><td>Last name</td><td>Invite</td></tr>';
	
	var counter = 0;
	
	$.each(friends_list, function(index, friend) {
		var friendTr = '<tr><td class=friendsRow>' + friend.firstName
				+ '</td><td class=friendsRow>' + friend.lastName
				+ '</td><td class=friendsRow><input type=checkbox class="inv_friends_cb" id="fri_' + friend.id + '" name=fri' + counter + ' value=fr' + counter + '></td></tr>';
				
		counter++;
		friendDiv += friendTr;
	});
			
	friendDiv += '</table></br>';
	friendsDiv.append(friendDiv);
	
	friendsDiv.append('<button onclick="back();">Back</button>');
	friendsDiv.append('<button onclick="next();">Next</button>');
}

function back() {
	window.location.href = "seats.html";
}

function next() {
	var inv_friends_cb_list = document.getElementsByClassName("inv_friends_cb");
	var invited_friends = localStorage.getItem("invFriends") != null ? JSON.parse(localStorage.getItem("invFriends")) : [];
	
	for(var i=0; i< inv_friends_cb_list.length; i++){
		var iden = inv_friends_cb_list[i].id.substring(4);
		var isChecked = document.getElementById("fri_"+iden).checked;
		
		if(isChecked){
			invited_friends.push(iden);
		}
	}
	
	localStorage.setItem('invFriends', JSON.stringify(invited_friends));
	inviteFriends();
	window.location.href = "passengers.html";
}

function inviteFriends() {
	
	var jsonData = JSON.stringify({
		"flightReservationId":localStorage.getItem("flightReservationId"),
		"invFriends":JSON.parse(localStorage.getItem('invFriends'))
	});
	
	$.ajax({
		type : "POST",
		contentType : "application/json",		
		url : "/users/inviteFriends",
		dataType : "json",
		data : jsonData,
		beforeSend: function (xhr) {
	        /* Authorization header */
	        xhr.setRequestHeader("Authorization", "Bearer " + getJwtToken());
	    },
		success : function() {
			alert("invites send");
		},
		error : function() {
			alert('ERROR INV');
		}
	});
}