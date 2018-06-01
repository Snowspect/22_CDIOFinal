/**
 * Generate n input fields in form depending on number of needed raavare
 */
function generateRaavare() {
	var number = 0;
	number = document.getElementById("raavareCount").value;
	
	for(i = 0; i < number; i++) {
		var form = '	<tr>' + 
		'				<td><center>RaavareID</center>'+
		'				<center>'+
		'					<input type="number" id="raavareID' + i + '" required pattern="[0-9.]+">'+
		'				</center></td>'+
		'				<br>'+
		'				<td><center>NomNetto i kg</center>'+
		'				<center>'+
		'					<input type="number" id="nomNetto' + i + '" step="0.01" required pattern="[0-9.]+">'+
		'				</center></td>'+
		'				<br>'+
		'				<td><center>Tolerance i %</center>'+
		'				<center>'+
		'					<input type="number" id="tolerance' + i + '" step="0.01" required pattern="[0-9.]+">'+
		'				</center></td>' +
		'				</tr>';
			
		$("#numberOfRaavare").append(form);
	}
}


function getDataFromHTML() {
	var receptId = document.getElementById("ID").value;
	var receptNavn = document.getElementById("Brugernavn").value
	
	
	var raavareID = document.getElementById("ini").value
	var cpr = document.getElementById("CPR").value
	var passwd = document.getElementById("password").value
	var rolle = document.getElementById("rolle").value

	var recept = {
		"receptId" : receptId,
		"receptNavn" : receptNavn,
		"ingrediens" : [{"raavareId": , "nomNetto": , "tolerance": }]
		
	}
	return recept;
}