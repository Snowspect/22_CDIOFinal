/**
 * 
 */

//Updates a user using PUT
function update() {
	alert("update called");
	myJSON = getRaavareFromHTML();
	$.ajax({
		url : "cargostock/raavare/",
		type : 'PUT',
		data : JSON.stringify(myJSON),
		contentType : 'application/json',
		success: function(data) {
			alert("update succesful");
			toUpdate();
		}, failure: function(){
			alert("fail");
		}
	});
	document.getElementById("myForm").reset();	//Clear the form
	return false; //For at undgå at knappen poster data (default behavior).
}

/**
 * Creates a user using POST, uses the cargostock/user/create path
 */
function submit() { //Formen kalder denne function, sikre at alle felter er udfyldt
	myJSON = getRaavareFromHTML(); //myJSON is an object just like "bruger"
	$.ajax({ //Indleder et asynkront ajax kald
		url : "cargostock/raavare", //specificerer endpointet
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

/**
 * Loads users from list using GET and displays them in table
 * @returns
 */
function loadRaavare(){
	$(function() {
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'cargostock/raavare', //specificerer endpointet
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

//draws person information from html into a "bruger" variable
function getRaavareFromHTML() {
	var ravareId = document.getElementById("RaavareId").value;
	var name = document.getElementById("RaavareNavn").value;
	var supplier = document.getElementById("Supplier").value;
	

	var raavare = {
		ravareId : ravareId,
		name : name,
		supplier : supplier
	}
	return raavare;
}

/**
 * Iterates throuch each data instance, first stringifying it into JSON and then parsing it into JSO
 * calls insert and adds all to the html table
 * @param data
 */
function iterate(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
		insert(this.ravareId, this.name, this.supplier);
	});
}

/**
 * Adds each JSO to the html table
 * @param RaavareId
 * @param name
 * @param supplier
 * @returns
 */
function insert(ravareId, name, supplier) {
	var table = document.getElementById("RaavareTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);

	cell1.innerHTML = ravareId;
	cell2.innerHTML = name;
	cell3.innerHTML = supplier;
}