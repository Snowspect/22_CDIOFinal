// Function to load the page that allows you to create a Produktbatch
function toCreateProduktbatch(){
	$(function(){
		$("#transform").load("ProduktbatchContext/Produktbatch.html");
	})
}

//Function to load the page that shows the produktbatches
function toViewProduktbatch()
{
	$(function(){
		$("#transform").load("ProduktbatchContext/ViewProduktbatch.html", null, function() {
			loadProducts();
		});
	});
}

//Function to load the recept html page
function toCreateRecept(){
	$(function(){
		$("#transform").load("receptContext/recept.html");
	})
}

//Function to load the page that shows the recepts
function toViewRecept(){
	$(function(){
		$("#transform").load("receptContext/ShowRecept.html",null,function() {
			loadRecept();
		})
	})
}




// loads the page that allows you to create a user
function toCreate(){
	$(function(){
		$("#transform").load("userContext/opret.html");
	})
}

// Loads the page that allows you to delete a user
function toDelete(){
	$(function() {
		$("#transform").load("userContext/deleteUser.html",null,function() {
			loadUsers();
		});
	});
}

// Loads the page that allows you to view the users.
function toView()
{
	$(function loadViewUsers(){
		$("#transform").load("userContext/ViewUsers.html");
		loadUsers(); //now not automatically executed once front page loads.
	});
}

//Loads the page that allows you to update the users.
function toUpdate()
{
	$(function() {
		$("#transform").load("userContext/UpdateUser.html",null,function() {
			loadUsers();
		});
	});
}

//Loads the page that allows you to create raavareBatches
function toRbCreate(){
	$(function(){
		$("#transform").load("raavareBatchContext/opretRaavareBatch.html");
	})
}

//Loads the page that allows you to view raavareBatches
function toRbView()
{
	$(function loadViewRb(){
		$("#transform").load("raavareBatchContext/viewRaavareBatch.html");
		loadRaavareBatch(); //now not automatically executed once front page loads.
	});
}

//Loads the Administrator page
function toAdministrator(){
	$(function(){
		$("#buttons_container").load("admin.html");
	})
}

//Loads the Farmaceut page
function toFarmaceut(){
	$(function(){
		$("#buttons_container").load("farmaceut.html");
	})
}

//Loads the Produktionsleder page
function toProduktionsleder(){
	$(function(){
		$("#buttons_container").load("produktionsleder.html");
	})
}

//Loads the page that allows you to update Raavare
function toRUpdate()
{
	$(function() {
		$("#transform").load("RaavareContext/RaavareUpdate.html",null,function() {
			loadRaavare();
		});
	});
}

//Loads the page that allows you to create Raavare
function toRCreate(){
	$(function(){
		$("#transform").load("RaavareContext/Raavare.html");
	})
}

//Loads the page that allows you to view Raavare
function toRView()
{
	$(function loadViewRb(){
		$("#transform").load("RaavareContext/RaavareView.html");
		loadRaavare(); //now not automatically executed once front page loads.
	});
}
