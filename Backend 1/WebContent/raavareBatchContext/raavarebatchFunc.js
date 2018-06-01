/**
 * 
 */
function insert(rbId, rId, amount) {
	var table = document.getElementById("raavarebatchTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);

	cell1.innerHTML = rbId;
	cell2.innerHTML = rId;
	cell3.innerHTML = amount;

}

function submit() { //Formen kalder denne function, sikre at alle felter er udfyldt
	myJSON = getPersonFromHTML(); //myJSON is an object just like "bruger"
	$.ajax({ //Indleder et asynkront ajax kald
		url : "cargostock/raavarebatch/", //specificerer endpointet
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

function loadRaavarebatches(){
	$(function() {
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'cargostock/raavarebatch', //specificerer endpointet
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

//draws raavarebatch information from html into a "raavarebatch" variable
function getRaavarebatchFromHTML() {
	var rbId = document.getElementById("rbId").value;
	var rId = document.getElementById("rId").value
	var amount = document.getElementById("amount").value


	var raavarebatch = {
		"rbId" : rbId,
		"rId" : rId,
		"amount" : amount,
	}
	return raavarebatch;
}

/**
 * Iterates throuch each data instance, first stringifying it into JSON and then parsing it into JSO
 * calls insert and adds all to the html table
 * @param data
 */
function iterate(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
		insert(this.rbId, this.rId, this.amount);
	});
}