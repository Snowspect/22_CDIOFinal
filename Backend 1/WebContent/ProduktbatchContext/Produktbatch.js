/**
 * Generate n input fields in form depending on number of needed raavare
 */
function generateProdukt() {
	var number = 0;
	number = document.getElementById("produktCount").value;
	
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
		'				</tr>' +
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



function submit() { //Formen kalder denne function, sikre at alle felter er udfyldt
	myJSON = getProduktFromHTML(); //myJSON is an object just like "bruger"
	$.ajax({ //Indleder et asynkront ajax kald
		url : "../cargostock/produktbatch/", //specificerer endpointet
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


function getProduktFromHTML() {
	var pbId = document.getElementById("pbId").value;
	var rcpId = document.getElementById("receptId").value
	var status = document.getElementById("Status").value
	var antal = document.getElementById("antal").value
	
	// lav afvejnings inputs

	var produktbatch = {
		"pbId" : pbId,
		"receptId" : rcpId,
		"Status" : status,
		"antal" : antal,
	//	"afvejning" : [{"userId": /*input*/, "rbId":/*input*/, "tara": /*input*/, "netto": /*input*/ }]
	}
	return produktbatch;
}

function loadProducts(){
	$(function() {
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'cargostock/produktbatch', //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			contentType : 'application/json',
			//Nedenstående bliver ikke kørt
			success : function(data)
			{//Funktion der skal udføres når data er hentet
				iterate(data);
				//alert("data");
			}, failure: function()
			{
				alert("fail");
			}
		});
	});
}

function iterate(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
		insert(this.pbId, this.receptId, this.Status, this.antal);
	});
}

function insert(pbId, rcpId, Status, antal) {
	var table = document.getElementById("userTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);

	cell1.innerHTML = pbId;
	cell2.innerHTML = rcpIp;
	cell3.innerHTML = Status;
	cell4.innerHTML = antal;

}

