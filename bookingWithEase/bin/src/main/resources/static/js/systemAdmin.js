$(document).ready(function() {
	
	
function ucitajPodatke(putanjaControlera, idTabeleZaPrikaz) {
	let tabela = $("#" + idTabeleZaPrikaz);
	$.ajax({
		type: "GET",
		url: putanjaControlera,
		success: function(response) {
			$.each(response, function(i, podatak) {
				prikazi(podatak, tabela);
			});
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("AJAX error: " + errorThrown);
		}
	});
}

function prikazi(podatak, tabelaZaPrikaz) {
	let noviRed = $("<tr></tr>");
	noviRed.append("<td>" + podatak.name + "</td>");
	noviRed.append("<td>" + podatak.address + "</td>");
	noviRed.append("<td>" + podatak.promotionalDescription + "</td>");
	tabelaZaPrikaz.append(noviRed);
}

