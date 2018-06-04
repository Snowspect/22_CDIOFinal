/**
 * Generate n input fields in form depending on number of needed raavare
 */
function generateProdukt() {
	var number = 0;
	number = document.getElementById("produktCount").value;
	
	for(i = 0; i < number; i++) {
		var form = '	<tr>' + 
		'				<td><center>UserID</center>'+
		'				<center>'+
		'					<input type="number" id="userID' + i + '" required pattern="[0-9.]+">'+
		'				</center></td>'+
		'				<br>'+
		'				<td><center>rbID</center>'+
		'				<center>'+
		'					<input type="number" id="rbID' + i + '" step="0.01" required pattern="[0-9.]+">'+
		'				</center></td>'+
		'				<br>'+
		'				<td><center>Tara</center>'+
		'				<center>'+
		'					<input type="number" id="tara' + i + '" step="0.01" required pattern="[0-9.]+">'+
		'				</center></td>' +
		'				<td><center>Netto</center>'+
		'				<center>'+
		'					<input type="number" id="Netto' + i + '" step="0.01" required pattern="[0-9.]+">'+
		'				</center></td>' +
		'				</tr>';
		
			
		$("#numberOfProductBatch").append(form);
	}
}





function submitProduct() { //Formen kalder denne function, sikre at alle felter er udfyldt
	myJSON = getProduktFromHTML(); //myJSON is an object just like "bruger"
	$.ajax({ //Indleder et asynkront ajax kald¨
		url : "cargostock/produktbatch", //specificerer endpointet
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
	var rcpId = document.getElementById("receptId").value;
	var status = document.getElementById("Status").value;
	
	// lav afvejnings inputs

	var produktbatch = {
		pbId : pbId,
		receptId : rcpId,
		status : status,
		afvejning: []
	//	"afvejning" : [{"userId"a: /*input*/, "rbId":/*input*/, "tara": /*input*/, "netto": /*input*/ }]
	};
	
	$("tr").each(function(index,element){
		//debugger;
		var UserID = document.getElementById("userID" + index).value;
		var rbId = document.getElementById("rbID" + index).value;
		var tara = document.getElementById("tara" + index).value;
		var netto = document.getElementById("Netto" + index).value;
		var obj = {userId : UserID, rbId: rbId, tara: tara, netto: netto};
		produktbatch.afvejning.push(obj);
	});	
	
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
				iterateProductTable(data);
				//alert("data");
			}, failure: function()
			{
				alert("fail");
			}
		});
	});
}

function iterateProductTable(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function(index,element) {  
		insertIntoProductTable(this.pbId, this.receptId, this.status, this.afvejning[index].userId, this.afvejning[index].rbId, this.afvejning[index].tara, this.afvejning[index].netto);
	}); 
}

function insertIntoProductTable(pbId, rcpId, Status, userId, rbId, tara, netto) {
	var table = document.getElementById("productBatchTable");
	var row = table.insertRow(1);
	var cell1 = row.insertCell(0);
	var cell2 = row.insertCell(1);
	var cell3 = row.insertCell(2);
	var cell4 = row.insertCell(3);
	var cell5 = row.insertCell(4);
	var cell6 = row.insertCell(5);
	var cell7 = row.insertCell(6);

	cell1.innerHTML = pbId;
	cell2.innerHTML = rcpId;
	cell3.innerHTML = Status;
	cell4.innerHTML = userId;
	cell5.innerHTML = rbId;
	cell6.innerHTML = tara;
	cell7.innerHTML = netto;
}