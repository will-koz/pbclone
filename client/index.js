function send_post_request (x, page) {
	fetch(window.location.origin + "/" + page, {
		method: "POST",
		// headers: { "Content-Type": "application/json" }, // POSTs don't need any response
		body: x
	});
}

function get_request (x, f) {
	fetch(x).then(function (response) {
		return response.json();
	}).then(f);
}

var alphabet = "0123456789abcdef";
function getID (x) {
	var returnString = "";
	for (var i = 0; i < x; i++)
		returnString += alphabet[Math.floor(alphabet.length * Math.random())];
	return returnString;
}

// Join game
// Generate ID
var id = "";
if (sessionStorage.getItem("id")) id = sessionStorage.getItem("id");
else {
	id = getID(24);
	sessionStorage.setItem("id", id);
}
console.log("ID: " + id);
// Send post request to server
send_post_request(id, "join");

// Set timer for being updated on game information
// Set events for pausing, skipping, buzzing in
