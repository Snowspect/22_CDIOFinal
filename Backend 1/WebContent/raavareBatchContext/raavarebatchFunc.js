	// Creates the raavareBatch in the database with the info from the html form.
	function submitRaavareBatch() { 
		myJSON = getRaavarebatchFromHTML(); 
		$.ajax({ //Starts an asynchronous ajax call
			url : "cargostock/raavarebatch", //specified end point
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

	 // Loads users from list using GET and displays them in table
	function loadRaavareBatch(){
		$(function() {
			$.ajax({ //Starts an asynchronous ajax call
				url : 'cargostock/raavarebatch', //specified end point
				type : 'GET', //Type of HTTP request (GET is default)
				contentType : 'application/json',
				// The code below does not run
				success : function(data)
				{//Function to be performed when data is collected
					//Manipulates #mydiv.
					iterateRaavareBatch(data);
				}, error: function(message) {
					alert("Raavarebatch get failed");
				}
			});
		});
	}

	// Draws RaavareBatch information from html into a "raavarebatch" variable
	function getRaavarebatchFromHTML() {
		var rbId = document.getElementById("rbId").value;
		var rId = document.getElementById("raavareId").value
		var amount = document.getElementById("maengde").value

		var raavarebatch = {
			"rbId" : rbId,
			"raavareId" : rId,
			"amount" : amount,
		}
		return raavarebatch;
	}

	// Loads all the RaavareBatchs into the corresponding table when called through LoadRaavareBatch
	// Iterates through each data instance, first parsing it into JSON and then stringify it into javascript
	// calls insert and adds all to the html table
	function iterateRaavareBatch(data) {
		$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
			insertIntoRaavareBatchTable(this.rbId, this.raavareId, this.amount);
		});
	}

	// The function to insert the RaavareBatchs into the raavarebatchTable
	function insertIntoRaavareBatchTable(rbId, rId, amount) {
		var table = document.getElementById("raavarebatchTable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
	
		cell1.innerHTML = rbId;
		cell2.innerHTML = rId;
		cell3.innerHTML = amount;	
	}