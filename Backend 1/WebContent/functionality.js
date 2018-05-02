
function myFunction() {
	var id = document.getElementById("ID").value;
	var navn = document.getElementById("Brugernavn").value
	var ini = document.getElementById("ini").value
	var cpr = document.getElementById("CPR").value
	var passwd = document.getElementById("password").value
	var rolle = document.getElementById("rolle").value
	
	var bruger = {
		"userId" : id,
		"userName" : navn,
		"ini" : ini,
		"cpr" : cpr,
		"password" : passwd,
		"roles" : rolle
	}
	return bruger;

}
	//$(function() { // Sikrer sig at dokumentet er indlæst, 
		// før click handlers håndteres
		//$('#submit').click(function() { //Sætter en click handler på knappen

		function submit() { //Formen kalder denne function, sikre at alle felter er udfyldt
					alert("Function kaldt");
		//debugger;
		myJSON = myFunction();
			$.ajax({ //Indleder et asynkront ajax kald
				url : 'rest/backend/create', //specificerer endpointet
				type : 'POST', //Typen af HTTP requestet (GET er default)
				data : 	JSON.stringify(myJSON),
				contentType : 'application/json',
				//Nedenstående bliver ikke kørt
				success : function(data) {//Funktion der skal udføres når data er hentet
					alert("data"); //Manipulerer #mydiv.
				}, failure: function(){
					alert("fail");
				}
			});
			return false; //For at undgå at knappen poster data (default behavior).
		}
	//});
		
		
function toView(){
        location.href = "ViewUsers.html";
}
function toCreate(){
		location.href = "opret.html"
}
$(function() {
		$.ajax({ //Indleder et asynkront ajax kald
			url : 'rest/backend/list', //specificerer endpointet
			type : 'GET', //Typen af HTTP requestet (GET er default)
			contentType : 'application/json',
			//Nedenstående bliver ikke kørt
			success : function(data) {//Funktion der skal udføres når data er hentet
				iterate(data);
				//alert("data");
			}, failure: function(){
				alert("fail");
			}
	});
	});
		
	function iterate(data) {
		$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
			insert(this.userId, this.userName, this.ini, this.cpr, this.password, this.roles);
	});
	}
	
	function insert(id, userName, ini, cpr, passwd, role) {
		var table = document.getElementById("userTable");
		var row = table.insertRow(1);
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		var cell4 = row.insertCell(3);
		var cell5 = row.insertCell(4);
		var cell6 = row.insertCell(5);

		cell1.innerHTML = id;
		cell2.innerHTML = userName;
		cell3.innerHTML = ini;
		cell4.innerHTML = cpr;
		cell5.innerHTML = passwd;
		cell6.innerHTML = role;
		}