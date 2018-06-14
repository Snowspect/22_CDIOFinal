
//Updates a user using PUT
function updateRaavare() {
	myJSON = getRaavareFromHTML();
	$.ajax({
		url : "cargostock/raavare",
		type : 'PUT',
		data : JSON.stringify(myJSON),
		contentType : 'application/json',
		success: function(data) {
			alert("update succesful");
			toRUpdate();
		}, error: function(message) {
			alert(message.responseText);
		}
	});
	document.getElementById("myForm").reset();	//Clear the form
	return false; // To prevent button posting data (default behavior).
}

//Creates a Raavare using POST 
function submitRaavare() { 
	myJSON = getRaavareFromHTML(); 
	$.ajax({ //Starts an asynchronous ajax call
		url : "cargostock/raavare", //specified end point
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
 
//  Loads users from list using GET and displays them in table
function loadRaavare(){
	$(function() {
		$.ajax({ //Starts an asynchronous ajax call
			url : 'cargostock/raavare', //specified end point
			type : 'GET', //Type of HTTP request (GET is default)
			contentType : 'application/json',
			// The code below does not run
			success : function(data)
			{//Function to be performed when data is collected
				iterateRaavare(data);
			}, error: function(message) {
				alert("Raavare get failed - error code Rax03");
			}
		});
	});
}

// Draws Raavare information from html into a "Raavare" variable
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

//Loads all the Raavare into the corresponding table when called through LoadRaavare
// Iterates through each data instance, first parsing it into JSON and then stringify it into javascript
// calls insert and adds all to the html table
function iterateRaavare(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
		insertIntoRaavareTable(this.ravareId, this.name, this.supplier);
	});
}

//The function to insert the Raavare into the RaavareTable
function insertIntoRaavareTable(ravareId, name, supplier) {
	var table = document.getElementById("RaavareTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);

	cell1.innerHTML = ravareId;
	cell2.innerHTML = name;
	cell3.innerHTML = supplier;
}