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
        location.href = "opret.html";
}
function toCreate(){
		location.href = "ViewUsers.html"
}