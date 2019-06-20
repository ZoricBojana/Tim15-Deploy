$(document).ready(function() {
    getUsers();
});


function getUsers()
{
	$.ajax({
		url : "/users",
		type : "GET",
		dataType : 'json',
		success : fillTable,
		error : function(response) {
			alert("Something went wrong! :(");
		}
	});
}

function fillTable(data) {
	var users = data == null ? []
			: (data instanceof Array ? data : [ data ]);
	$('#usersTable').empty();
	$('#usersTable').append('<tr><th>Username</th><th>First name</th><th>Last name</th><th>Email</th><th>User type</th></tr>');
	$.each(users, function(index, user) {

		var tr = $('<tr></tr>');
		tr.append('<td>' + user.username + '</td>' + '<td>'
				+ user.firstName + '</td>' + '<td>'
				+ user.lastName + '</td>' + '<td>'
				+ user.email + '</td>');
		
		if(user.type !== null)
		{
			tr.append('<td>' + user.type.toLowerCase() + ' admin' + '</td>');
		}else
		{
			tr.append('<td>' + ' registered user' + '</td>');
		}
		
		$('#usersTable').append(tr);

	});

}