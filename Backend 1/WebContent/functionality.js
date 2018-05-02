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