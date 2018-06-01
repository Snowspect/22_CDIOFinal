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
	var receptId = document.getElementById("receptID").value;
	var receptNavn = document.getElementById("receptNavn").value
	
	var recept = {
		receptId : receptId,
		receptNavn : receptNavn,
		ingrediens : []		
	};
	//{"raavareId": 2, "nomNetto": 2, "tolerance": 2}
	
	
	$("tr").each(function(element,index){
		var ravaareId = document.getElementById("raavareID" + i).value;
		var nomNetto = document.getElementById("nomNetto" + i).value;
		var tolerance = document.getElementById("tolerance" + i).value;
		var obj = {raavareId : ravaareId, nomNEtto: nomNetto, tolerance: tolerance};
		recept.ingrediens.push(obj);
	});
	


	return recept;
}