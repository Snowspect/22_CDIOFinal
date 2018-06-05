function toCreateProduktbatch(){
	$(function(){
		$("#transform").load("ProduktbatchContext/Produktbatch.html");
	})
}

function toViewProduktbatch()
{
	$(function loadViewProduktbatch(){
		$("#transform").load("ProduktbatchContext/ViewProduktbatch.html");
		loadProducts(); //now not automatically executed once front page loads.
	});
}



function toCreateRecept(){
	$(function(){
		$("#transform").load("receptContext/recept.html");
	})
}

function toViewRecept(){
	$(function(){
		$("#transform").load("receptContext/ShowRecept.html",null,function() {
			loadRecept();
		})
	})
}



/**
 * loads the page that allows you to create a user
 * @returns
 */
function toCreate(){
	$(function(){
		$("#transform").load("userContext/opret.html");
	})
}

/**
 * Loads the page that allows you to delete a user
 * @returns
 */
function toDelete(){
	$(function() {
		$("#transform").load("userContext/deleteUser.html",null,function() {
			loadUsers();
		});
	});
}

/**
 * Loads the page that allows you to simply view users.
 * @returns
 */
function toView()
{
	$(function loadViewUsers(){
		$("#transform").load("userContext/ViewUsers.html");
		loadUsers(); //now not automatically executed once front page loads.
	});
}

function toUpdate()
{
	$(function() {
		$("#transform").load("userContext/UpdateUser.html",null,function() {
			loadUsers();
		});
	});
}


function toRbCreate(){
	$(function(){
		$("#transform").load("raavareBatchContext/opretRaavareBatch.html");
	})
}

function toRbView()
{
	$(function loadViewRb(){
		$("#transform").load("raavareBatchContext/viewRaavareBatch.html");
		loadRaavareBatch(); //now not automatically executed once front page loads.
	});
}


function toAdministrator(){
	$(function(){
		$("#buttons_container").load("admin.html");
	})
}

function toFarmaceut(){
	$(function(){
		$("#buttons_container").load("farmaceut.html");
	})
}

function toProduktionsleder(){
	$(function(){
		$("#buttons_container").load("produktionsleder.html");
	})
}

function toRUpdate()
{
	$(function() {
		$("#transform").load("RaavareContext/RaavareUpdate.html",null,function() {
			loadRaavare();
		});
	});
}


function toRCreate(){
	$(function(){
		$("#transform").load("RaavareContext/Raavare.html");
	})
}

function toRView()
{
	$(function loadViewRb(){
		$("#transform").load("RaavareContext/RaavareView.html");
		loadRaavare(); //now not automatically executed once front page loads.
	});
}
