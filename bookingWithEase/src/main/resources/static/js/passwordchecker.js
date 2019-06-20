$(document).ready(function() {
    $('#registrationpassword').keyup(function() {
        $('#result').html(checkStrength($('#registrationpassword').val()))
    });
    
    $('#registrationconfpassword').keyup(function() {
        $('#match').html(checkMatching($('#registrationpassword').val(), $('#registrationconfpassword').val()));
    });
});
function checkStrength(password) {
    var strength = 0
    if (password.length < 5) {
        $('#result').removeClass()
        $('#result').addClass('short')
        return 'Too short password'
    }
    if (password.length > 7) strength += 1
    // If password contains both lower and uppercase characters, increase strength value.
    if (password.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) strength += 1
    // If it has numbers and characters, increase strength value.
    if (password.match(/([a-zA-Z])/) && password.match(/([0-9])/)) strength += 1
    // If it has one special character, increase strength value.
    if (password.match(/([!,%,&,@,#,$,^,*,?,_,~])/)) strength += 1
    // If it has two special characters, increase strength value.
    if (password.match(/(.*[!,%,&,@,#,$,^,*,?,_,~].*[!,%,&,@,#,$,^,*,?,_,~])/)) strength += 1
    // Calculated strength value, we can return messages
    // If value is less than 2
    if (strength < 2) {
        $('#result').removeClass()
        $('#result').addClass('weak')
        return 'Weak password'
    } else if (strength == 2) {
        $('#result').removeClass()
        $('#result').addClass('good')
        return 'Good password'
    } else {
        $('#result').removeClass()
        $('#result').addClass('strong')
        return 'Strong password'
    }
}

function checkMatching(p1, p2){
	$('#match').removeClass();
	
	if (p1 === p2){
		$('#match').addClass('strong');
		return "Passwords match."
	}else{
		$('#match').addClass('short');
		return "Passwords do not match!"
	}
}
