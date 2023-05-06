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
setInterval(get_request, 100, "/game", (response) => {
	if (response.question) document.getElementById("question").innerHTML = response.question;
	if (response.progress) document.getElementById("progress").value = response.progress;
	if (response.end) document.getElementById("time_left").innerHTML =
		Math.max(0, (Date.now() - response.end) / (-1000));

	if (response.players) {
		response.players.sort((a, b) => a.score - b.score);
		var tableHTML = "<tr><td></td><td>Name</td><td>Score</td></tr>";
		for (var i = 0; i < response.players.length; i++) {
			tableHTML += "<tr>";
			tableHTML += "<td>" + (i + 1) + "</td>";
			tableHTML += "<td>" + response.players[i].name + "</td>";
			tableHTML += "<td>" + response.players[i].score + "</td>";
			tableHTML += "</tr>";
		}
		document.getElementById("scoreboard").innerHTML = tableHTML;
	}

});

// Set events for pausing, skipping, buzzing in
document.onkeydown = function (e) {
	// Make sure keybinds don't get in the way of entering information
	if (document.activeElement.tagName.toLowerCase() == "input") {
		// console.log(document.activeElement.tagName);
		return;
	}

	switch (e.keyCode) {
		case 80: // the P key
			send_post_request(id, "pause");
			break;
		case 83: // the S key
			send_post_request(id, "skip");
			break;
		default:
	}
}

var change_name = document.getElementById("change_name");
change_name.addEventListener("input", function (e) {
	send_post_request(id + "," + change_name.value, "change_name");
});
