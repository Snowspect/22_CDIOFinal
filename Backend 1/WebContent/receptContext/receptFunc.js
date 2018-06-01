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
	
	
	$("tr").each(function(index,element){
		//debugger;
		var ravaareId = document.getElementById("raavareID" + index).value;
		var nomNetto = document.getElementById("nomNetto" + index).value;
		var tolerance = document.getElementById("tolerance" + index).value;
		var obj = {raavareId : ravaareId, nomNetto: nomNetto, tolerance: tolerance};
		recept.ingrediens.push(obj);
	});
	return recept;
}



/**
 * Creates a user using POST, uses the
 */
function submit() { //Formen kalder denne function, sikre at alle felter er udfyldt
	myJSON = getDataFromHTML(); //myJSON is an object just like "bruger"
	$.ajax({ //Indleder et asynkront ajax kald
		url : "../cargostock/recept", //specificerer endpointet
		type : 'POST', //Typen af HTTP requestet
		data : 	JSON.stringify(myJSON),
		contentType : 'application/json',
		//Nedenstående bliver ikke kørt
		success : function(data) {//Funktion der skal udføres når data er hentet
			alert("success"); //Manipulerer #mydiv.
		}, failure: function(){
			alert("fail");
		}
	});
	document.getElementById("myForm").reset();	//Clear the form
	return false; //For at undgå at knappen poster data (default behavior).
}