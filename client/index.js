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

var can_buzz_in = false;
var buzzed_in = false;
var cycles_waiting = 3; // TODO variable?
document.getElementById("maininput").disabled = true;
document.getElementById("maininput").value = "";

// Set timer for being updated on game information
setInterval(get_request, 100, "/game", (response) => {
	if (response.question) document.getElementById("question").innerHTML = response.question;
	if (response.progress) document.getElementById("progress").value = response.progress;
	if (response.end) document.getElementById("time_left").innerHTML =
		Math.max(0, (Date.now() - response.end) / (-1000));
	can_buzz_in = (response.can_buzz_in) ? true : false; // Ignore undefined and null stuff
	if (response.canswer) {
		var buzz_ans = document.getElementById("buzz_ans");
		buzz_ans.innerHTML = "<b>" + response.canswer[1] + "</b>: " + response.canswer[0];
	} else document.getElementById("buzz_ans").innerHTML = "";

	if (response.players) {
		response.players.sort((a, b) => b.score - a.score);
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

	if (response.notes) {
		var notes = document.getElementById("notes");
		notes.innerHTML = "";
		for (var i = 0; i < response.notes.length; i++)
			notes.innerHTML += response.notes[i];
	}
});

setInterval(() => {
	if (buzzed_in) {
		var maininput = document.getElementById("maininput");
		send_post_request(id + "," + maininput.value, "buzz");
	}
}, 100);

// What to do in the event of a buzz
function buzz_in () {
	var maininput = document.getElementById("maininput");
	send_post_request(id + "," + maininput.value, "buzz");
	maininput.disabled = false;
	maininput.focus();
	buzzed_in = true;
	cycles_waiting = 3;
	setTimeout(() => { unbuzz_in() }, 7000); // TODO variable
}

function unbuzz_in () {
	var maininput = document.getElementById("maininput");
	maininput.value = "";
	maininput.disabled = true;
	maininput.blur();
	buzzed_in = false;
	can_buzz_in = false;
}

// Set events for pausing, skipping, buzzing in
document.onkeydown = function (e) {
	// Make sure keybinds don't get in the way of entering information
	if (document.activeElement.tagName.toLowerCase() == "input") {
		// console.log(document.activeElement.tagName);
		return;
	}

	switch (e.keyCode) {
		case 32: // the space key
			if (can_buzz_in) buzz_in();
			break;
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
	// Make sure this is escaped TODO client and server side
	send_post_request(id + "," + change_name.value, "change_name");
});
