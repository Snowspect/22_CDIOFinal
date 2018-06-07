/**
 * Generate n input fields in form depending on number of needed raavare
 */
function generateRecept() {
	var number = 0;
	number = document.getElementById("receptCount").value;
	
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
			
		$("#numberOfRecept").append(form);
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
function submitRecept() { //Formen kalder denne function, sikre at alle felter er udfyldt
	myJSON = getDataFromHTML(); //myJSON is an object just like "bruger"
	$.ajax({ //Indleder et asynkront ajax kald
		url : "cargostock/recept", //specificerer endpointet
		type : 'POST', //Typen af HTTP requestet
		data : 	JSON.stringify(myJSON),
		contentType : 'application/json',
		//Nedenstående bliver ikke kørt
		success : function(data) {//Funktion der skal udføres når data er hentet
			alert("success"); //Manipulerer #mydiv.
		}, error: function(message) {
			alert(message.responseText);
		}
	});
	document.getElementById("myForm").reset();	//Clear the form
	return false; //For at undgå at knappen poster data (default behavior).
}

function loadRecept() {
	$(function() {
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'cargostock/recept', //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			contentType : 'application/json',
			//Nedenstående bliver ikke kørt
			success : function(data)
			{//Funktion der skal udføres når data er hentet
				iterateRecept(data);
				//alert("data");
			}, error: function(message) {
				alert("Recept get failed");
			}
		});
	});
}
/**
 * Iterates throuch each data instance, first stringifying it into JSON and then parsing it into JSO
 * calls insert and adds all to the html table
 * @param data
 */
function iterateRecept(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function(index, element) {  
		for(i = 0; i < this.ingrediens.length; i++) {
			console.log("hhellll");
		insertIntoReceptTable(this.receptId, this.receptNavn, this.ingrediens[i].raavareId, this.ingrediens[i].nomNetto, this.ingrediens[i].tolerance);
		}
	});
}
/**
 * Adds each JSO to the html table
 * @param RaavareId
 * @param name
 * @param supplier
 * @returns
 */
function insertIntoReceptTable(receptId, receptNavn, raavareId, nomNetto, tolerance) {
	var table = document.getElementById("receptTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);
	var cell5 = row.insertCell(4);

	cell1.innerHTML = receptId;
	cell2.innerHTML = receptNavn;
	cell3.innerHTML = raavareId;
	cell4.innerHTML = nomNetto;
	cell5.innerHTML = tolerance;
}