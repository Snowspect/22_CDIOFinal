

// Generate n input fields in form depending on number of needed raavare
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

//Returns the Recept object from the html form
function getDataFromHTML() {
	var receptId = document.getElementById("receptID").value;
	var receptNavn = document.getElementById("receptNavn").value
	
	var recept = {
		receptId : receptId,
		receptNavn : receptNavn,
		receptKomponent : []		
	};
	
	
	$("tr").each(function(index,element){
		var receptIdT = receptId;
		var ravaareIdT = document.getElementById("raavareID" + index).value;
		var nomNettoT = document.getElementById("nomNetto" + index).value;
		var toleranceT = document.getElementById("tolerance" + index).value;
		var obj = {receptId: receptIdT, raavareId : ravaareIdT, nomNetto: nomNettoT, tolerance: toleranceT};
		recept.receptKomponent.push(obj);
	});
	return recept;
}

// The function to create a recept in the database from the html
function submitRecept() { 
	myJSON = getDataFromHTML(); 
	$.ajax({ //Starts an asynchronous ajax call
		url : "cargostock/recept", //specified end point
		type : 'POST', //Type of HTTP request
		data : 	JSON.stringify(myJSON),
		contentType : 'application/json',
		// The code below does not run
		success : function(data) {//Function to be performed when data is collected
			alert("success"); //Manipulates #mydiv.
		}, error: function(message) {
			alert(message.responseText);
		}
	});
	document.getElementById("myForm").reset();	//Clear the form
	return false; // To prevent button posting data (default behavior).
}

// Loads the raavare in the recepts into the corresponding table.
function loadReceptRaavare() {
	var id = document.getElementById("receptID").value;
	toViewRecept();
	$(function() {
		$.ajax({
			url: 'cargostock/recept/' + id,
			type : 'GET',
			contentType : 'application/json',
			success : function(data)
			{
				iterateReceptID(data);
			},
			error : function(message)
			{
				alert("something went wrong");
			}
		})
	})
}

//Loads the recepts into the corresponding table.
function loadRecept() {
	$(function() {
		$.ajax({ //Starts an asynchronous ajax call
			url : 'cargostock/recept', //specified end point
			type : 'GET', //Type of HTTP request (GET is default)
			contentType : 'application/json',
			// The code below does not run
			success : function(data)
			{//Function to be performed when data is collected
				iterateRecept(data);
			}, error: function(message) {
				alert("Recept get failed");
			}
		});
	});
}
// Iterates through each data instance, first parsing it into JSON and then stringify it into javascript
// calls insert and adds all to the html table
// The function to load recepts into it's corresponding table. 
function iterateRecept(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function(index, element) {
		insertIntoReceptTable(this.receptId, this.receptNavn);
	});
}

//The function to load a recepts raavare into it's corresponding table. 
function iterateReceptID(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function(index,element) {
			insertIntoRaavareReceptTable(this.raavareId, this.nomNetto, this.tolerance)
	});
}
/**
 * Adds each JSO to the html table
 * @param RaavareId
 * @param name
 * @param supplier
 * @returns
 */
// Inserts the recepts into it's corresponding table 
//Adds the JSON objects to the table. 
function insertIntoReceptTable(receptId, receptNavn) {
	var table = document.getElementById("receptTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);

	cell1.innerHTML = receptId;
	cell2.innerHTML = receptNavn;
}

// Inserts the raavare into the corresponding table
function insertIntoRaavareReceptTable(raavareId, nomNetto, tolerance)
{
	var table = document.getElementById("raavareReceptTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	
	cell1.innerHTML = raavareId;
	cell2.innerHTML = nomNetto;
	cell3.innerHTML = tolerance;
}