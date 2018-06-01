/**
 * 
 */

function generateRaavare() {
	var number = 0;
	number = document.getElementById("raavareCount").value;
	
	var form = $("#test").html();
	
	
	for(i = 0; i < number; i++) {
//		debugger;
		//TODO
		//Append HTML to div
//		form.id = i;
		$("#numberOfRaavare").append(form);
		
//		document.getElementById("numberOfRaavare").append(form);
		console.log("x");
	}
}