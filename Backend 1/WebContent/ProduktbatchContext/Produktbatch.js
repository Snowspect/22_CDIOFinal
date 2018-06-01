
function submit() { //Formen kalder denne function, sikre at alle felter er udfyldt
	myJSON = getProduktFromHTML(); //myJSON is an object just like "bruger"
	$.ajax({ //Indleder et asynkront ajax kald
		url : "cargostock/produktbatch/", //specificerer endpointet
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

	var produkt = {
		"pbId" : pbId,
		"receptId" : rcpId,
		"Status" : status,
		"antal" : antal,
	}
	return produkt;
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

