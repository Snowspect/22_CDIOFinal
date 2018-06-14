function submitProduct() { 
	myJSON = getProduktFromHTML();
	$.ajax({ //Starts an asynchronous ajax call
		url : "cargostock/produktbatch", //specified end point
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

// Returns the produktbatch object from the html form
function getProduktFromHTML() {
	var pbIdT = document.getElementById("pbId").value;
	var rcpIdT = document.getElementById("receptId").value;
	
	// Make weighing inputs

	var produktbatch = {
		pbId : pbIdT,
		receptId : rcpIdT,
		status : 0,
		produktBatchKomponent: []
	};	

	return produktbatch;
}

// Loads all the produktKomp into the corresponding table
function loadproduktKomp() {
	var id = document.getElementById("produktbatchID").value;
	toViewProduktbatch();
	$(function() {
		$.ajax({
			url : 'cargostock/produktbatch/' + id,
			type: 'GET',
			contentType : 'application/json',
			success: function(data)
			{
				iterateProductKompTable(data);
			}, error : function(message)
			{
				alert(message.responseText);
			}
		})
	})
}

// Loads all the Products into the corresponding table
function loadProducts(){
	$(function() {
		$.ajax({ //Starts an asynchronous ajax call
			url : 'cargostock/produktbatch', //specified end point
			type : 'GET', //Type of HTTP request (GET is default)
			contentType : 'application/json',
			// The code below does not run
			success : function(data)
			{//Function to be performed when data is collected
				iterateProductTable(data);
			}, error: function(message) {
				alert(message.responseText);
				alert("Produktbatch get failed - Error code Pbx04");
			}
		});
	});
}

// Loads all the Products into the corresponding table when called through LoadProducts
// Iterates through each data instance, first parsing it into JSON and then stringify it into javascript
// calls insert and adds all to the html table
function iterateProductTable(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
		insertIntoProductTable(this.pbId, this.receptId, this.status);
	}); 
}
//Loads all the ProductKomp into the corresponding table when called through LoadproductKomp
function iterateProductKompTable(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function(){
		insertIntoProductKompTable(this.rolle_id, this.rbId, this.tara, this.netto);		
	});
}

// The function to insert the Products into the ProductsTable
// Adds the JSON objects to the table.
function insertIntoProductTable(pbId, rcpId, Status) {
	var table = document.getElementById("productBatchTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);

	cell1.innerHTML = pbId;
	cell2.innerHTML = rcpId;
	cell3.innerHTML = Status;
}

//The function to insert the productKomps into the ProductKompTable
function insertIntoProductKompTable(rolle_id, rbId, tara, netto) {
	var table = document.getElementById("produktBatchKompTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);

	cell1.innerHTML = rolle_id;
	cell2.innerHTML = rbId;
	cell3.innerHTML = tara;
	cell4.innerHTML = netto;
}