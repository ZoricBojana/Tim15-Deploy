var TOKEN_KEY = 'jwtToken';

function getJwtToken() {
	return sessionStorage.getItem(TOKEN_KEY);
};

function setJwtToken(token) {
	sessionStorage.setItem(TOKEN_KEY, token);
};

function removeJwtToken() {
	sessionStorage.removeItem(TOKEN_KEY);
};

function logout() {
	sessionStorage.clear();
	localStorage.clear();
	window.location.replace("index.html");
}