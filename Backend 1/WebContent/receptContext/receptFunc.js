/**
 * 
 */

function generateRaavare() {
	var number = 0;
	number = document.getElementById("raavareCount").value;
	
	for(i = 0; i < number; i++) {
		var form = '<br>'+
		'				<center>RaavareID</center>'+
		'				<center>'+
		'					<input type="number" id="raavareID' + i + '" required pattern="[0-9.]+">'+
		'				</center>'+
		'				<br>'+
		'				<center>NomNetto i kg</center>'+
		'				<center>'+
		'					<input type="number" id="nomNetto' + i + '" step="0.01" required pattern="[0-9.]+">'+
		'				</center>'+
		'				<br>'+
		'				<center>Tolerance i %</center>'+
		'				<center>'+
		'					<input type="number" id="tolerance' + i + '" step="0.01" required pattern="[0-9.]+">'+
		'				</center>';
			
		$("#numberOfRaavare").append(form);
	}
}