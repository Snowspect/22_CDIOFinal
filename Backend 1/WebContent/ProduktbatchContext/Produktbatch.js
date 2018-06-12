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
//	
//	$("tr").each(function(index,element){
//		//debugger;
//		var pbIdA = pbIdT;
//		var rbIdA = document.getElementById("rbID" + index).value;
//		var taraA = document.getElementById("tara" + index).value;
//		var nettoA = document.getElementById("Netto" + index).value;
//		var rolle_idA = document.getElementById("rolle_id" + index).value;
//		var obj = {pbId : pbIdA, rbId: rbIdA, tara: taraA, netto: nettoA, rolle_id: rolle_idA};
//		produktbatch.produktBatchKomponent.push(obj);
//	});	
	
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
				alert("produkt batch komp fejlet");
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
				//alert("data");
			}, error: function(message) {
				alert("Produktbatch get failed");
			}
		});
	});
}

// Loads all the Products into the corresponding table when called through LoadProducts
function iterateProductTable(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
		insertIntoProductTable(this.pbId, this.receptId, this.status);
	}); 
}

function iterateProductKompTable(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function(){
		insertIntoProductKompTable(this.rolle_id, this.rbId, this.tara, this.netto);		
	});
}

function insertIntoProductTable(pbId, rcpId, Status) {
	var table = document.getElementById("productBatchTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
//	var cell4 = row.insertCell(3);
//	var cell5 = row.insertCell(4);
//	var cell6 = row.insertCell(5);
//	var cell7 = row.insertCell(6);

	cell1.innerHTML = pbId;
	cell2.innerHTML = rcpId;
	cell3.innerHTML = Status;
//	cell4.innerHTML = rolle_id;
//	cell5.innerHTML = rbId;
//	cell6.innerHTML = tara;
//	cell7.innerHTML = netto;
}

function insertIntoProductKompTable(rolle_id, rbId, tara, netto) {
	var table = document.getElementById("produktBatchKompTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);
//	var cell5 = row.insertCell(4);
//	var cell6 = row.insertCell(5);
//	var cell7 = row.insertCell(6);

//	cell1.innerHTML = pbId;
//	cell2.innerHTML = rcpId;
//	cell3.innerHTML = Status;
	cell1.innerHTML = rolle_id;
	cell2.innerHTML = rbId;
	cell3.innerHTML = tara;
	cell4.innerHTML = netto;
}