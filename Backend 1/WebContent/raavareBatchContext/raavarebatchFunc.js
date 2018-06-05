	/**
	 * Creates a user using PUT, uses the cargostock/user/create path
	 */
	function submitRaavareBatch() { //Formen kalder denne function, sikre at alle felter er udfyldt
		myJSON = getRaavarebatchFromHTML(); //myJSON is an object just like "bruger"
		$.ajax({ //Indleder et asynkront ajax kald
			url : "cargostock/raavarebatch", //specificerer endpointet
			type : 'POST', //Typen af HTTP requestet
			data : 	JSON.stringify(myJSON),
			contentType : 'application/json',
			//Nedenstående bliver ikke kørt
			success : function(data) {//Funktion der skal udføres når data er hentet
				alert("success"); //Manipulerer #mydiv.
			}, failure: function(data){
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
	function loadRaavareBatch(){
		$(function() {
			$.ajax({ //Indleder et asynkront ajax kald
				url : 'cargostock/raavarebatch', //specificerer endpointet
				type : 'GET', //Typen af HTTP requestet (GET er default)
				contentType : 'application/json',
				//Nedenstående bliver ikke kørt
				success : function(data)
				{//Funktion der skal udføres når data er hentet
					iterateRaavareBatch(data);
					//alert("data");
				}, failure: function()
				{
					alert("fail");
				}
			});
		});
	}

	//draws person information from html into a "bruger" variable
	function getRaavarebatchFromHTML() {
		var rbId = document.getElementById("rbId").value;
		var rId = document.getElementById("raavareId").value
		var amount = document.getElementById("maengde").value

		var raavarebatch = {
			"rbId" : rbId,
			"raavareId" : rId,
			"maengde" : amount,
		}
		return raavarebatch;
	}

	/**
	 * Iterates through each data instance, first stringifying it into JSON and then parsing it into JSO
	 * calls insert and adds all to the html table
	 * @param data
	 */
	function iterateRaavareBatch(data) {
		$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
			insertIntoRaavareBatchTable(this.rbId, this.raavareId, this.maengde);
		});
	}

	/**
	 * Adds each JSO to the html table
	 * @param id
	 * @param userName
	 * @param ini
	 * @param cpr
	 * @param passwd
	 * @param role
	 * @returns
	 */
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


	/**
	 * loads the page that allows you to create a user
	 * @returns
	 */

