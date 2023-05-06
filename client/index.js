function send_post_request (x, page) {
	fetch(window.location.origin + "/" + page, {
		method: "POST",
		// headers: { "Content-Type": "application/json" }, // POSTs don't need any response
		body: x
	});
}

// Join game

// Generate ID
id = "super_secret_key";
console.log(id);
// Send post request to server
var x = send_post_request(id, "join");

// Set timer for being updated on game information
// Set events for pausing, skipping, buzzing in
