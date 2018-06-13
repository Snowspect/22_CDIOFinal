
//Updates a user using POST
function updateUser() {
	myJSON = getPersonFromUpdateHTML();
	$.ajax({
		url : "cargostock/users",
		type : 'PUT',
		data : JSON.stringify(myJSON),
		contentType : 'application/json',
		success: function(data) {
			alert("update succesful");
			toUpdate();
		}, error: function(message) {
			alert(message.responseText);
		}
	});
	document.getElementById("myForm").reset();	//Clear the form
	return false; // To prevent button posting data (default behavior).
}


 // Creates a user using PUT, uses the cargostock/user/create path
function submitUser() { 
	myJSON = getPersonFromHTML(); 
	$.ajax({ //Starts an asynchronous ajax call
		url : "cargostock/users/", //specified end point
		type : 'POST', //Type of HTTP request
		data : 	JSON.stringify(myJSON),
		contentType : 'application/json',
		// The code below does not run
		success : function(data) {//Function to be performed when data is collected
			alert("success"); //Manipulates #mydiv.
		}, error: function(message) {
			alert(message.responseText);;
		}
	});
	document.getElementById("myForm").reset();	//Clear the form
	return false; // To prevent button posting data (default behavior).
}


// Loads users from list using GET and displays them in corresponding table
function loadUsers(){
	$(function() {
		$.ajax({ //Starts an asynchronous ajax call
			url : 'cargostock/users', //specified end point
			type : 'GET', //Type of HTTP request (GET is default)
			contentType : 'application/json',
			// The code below does not run
			success : function(data)
			{//Function to be performed when data is collected
				iterateUsers(data);
			}, error: function(message) {
				alert("Users get failed");
			}
		});
	});
}



// Removes user using DELETE
// Sets users status to 0
function removeUser() {
	var id = document.getElementById("ID").value;
	$.ajax({
		url : "cargostock/users/" + id,
		type : 'DELETE',
		contentType: 'application/json',
		success : function(data)
		{
			alert("successful delete");
			toDelete(); //currently called as there is no direct method for emptying a table, 
			//and as such we reload the html
		}, error: function(message) {
			alert(message.responseText);
		}
	});
	document.getElementById("deleteForm").reset();	//Clear the form
}

// Draws person information from html into a "bruger" variable
function getPersonFromHTML() {
	var id = document.getElementById("ID").value;
	var navn = document.getElementById("Brugernavn").value;
	var ini = document.getElementById("ini").value;
	var cpr = document.getElementById("CPR").value;
	var rolle = document.getElementById("rolle").value;

	var bruger = {
		userId : id,
		userName : navn,
		ini : ini,
		cpr : cpr,
		roles : rolle,
		status : true
	}
	return bruger;
}

// Draws person information from html into a "bruger" variable
// Used for updating the user
function getPersonFromUpdateHTML() {
	var id = document.getElementById("ID").value;
	var navn = document.getElementById("Brugernavn").value
	var ini = document.getElementById("ini").value
	var cpr = document.getElementById("CPR").value
	var rolle = document.getElementById("rolle").value
	
	var bruger = {
		userId : id,
		userName : navn,
		ini : ini,
		cpr : cpr,
		roles : rolle,
		status : true
	}
	return bruger;
}

//Loads all the Users into the corresponding table when called through LoadUsers
// Iterates through each data instance, first parsing it into JSON and then stringify it into javascript
// calls insert and adds all to the html table
function iterateUsers(data) {
	$(jQuery.parseJSON(JSON.stringify(data))).each(function() {  
		insertIntoUserTable(this.userId, this.userName, this.ini, this.cpr, this.roles, this.status);
	});
}

//The function to insert the Users into the userTable
function insertIntoUserTable(id, userName, ini, cpr, role, status) {
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
	cell5.innerHTML = role;
	cell6.innerHTML = status;
}