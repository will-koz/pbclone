package pb;

import java.lang.StringBuilder;
import java.util.ArrayList;

import pb.Note;
import pb.Question;
import pb.Player;

public class Game {

public ArrayList<Note> notes;
private ArrayList<Player> players;
private Question current_question;

public static int max_notes = 15;

public Game () {
	notes = new ArrayList<Note>();
	players = new ArrayList<Player>();

	notes.add(0, new Note("Started game", ""));
}

public void addPlayer (String s) {
	// TODO look up if player with this id exists
	Player p = new Player(s);
	players.add(p);
	notes.add(0, new Note("2d7751", "<b>%s</b> joined the game.", "", p));
}

public void buzz (String s) {
	String[] data = s.split(",", 2);
	if (current_question.get_status() == Status.buzzed) {
		if (get_player(data[0]) == current_question.get_active_player())
			current_question.update_answer(data[1]);
	}
	else if (current_question.get_status() == Status.running) {
		current_question.buzz(get_player(data[0]));
	}
}

public void change_name (String s) {
	String[] data = s.split(",", 2);
	get_player(data[0].trim()).set_name(data[1].trim());
}

public String getJSON () {
	StringBuilder returnString = new StringBuilder("{");

	// Get question information
	returnString.append("\"question\":\"");

	// Running
	if (current_question != null && current_question.get_status() == Status.running) {
		returnString.append(current_question.get_public_question()); // TODO make sure is escaped
		returnString.append("\",\"progress\":" + Math.floor(current_question.progress() * 100));
		returnString.append(",\"end\":" + current_question.get_end_date());
		returnString.append(",\"status\":\"running\"");
	}

	// Paused
	else if (current_question != null && current_question.get_status() == Status.paused) {
		returnString.append("\",\"status\":\"paused\"");
	}

	// Buzzed
	else if (current_question != null && current_question.get_status() == Status.buzzed) {
		returnString.append("\",\"status\":\"buzzed\"");
		// Current answer is a list of [answer, player name]
		returnString.append(",\"canswer\":[\"");
		returnString.append(current_question.get_current_answer());
		returnString.append("\",\"");
		returnString.append(current_question.get_active_player().get_name());
		returnString.append("\"]");
		returnString.append(",\"end\":" + current_question.get_unbuzz_date());
	}

	// Question completed
	else if (current_question != null && current_question.get_status() == Status.complete) {
		// TODO explain the 1
		returnString.append("\",\"status\":\"complete\",\"end\":1");
	}

	// Waiting
	else returnString.append("waiting for the question to begin\"");

	// TODO optimize (same comparison as above)
	returnString.append(",\"can_buzz_in\":");
	if (current_question != null && current_question.get_status() == Status.running)
		returnString.append("true");
	else returnString.append("false");

	// Get player information
	returnString.append(",\"players\":[");
	for (int i = 0; i < players.size(); i++) {
		if (i != 0) returnString.append(",{");
		else returnString.append("{");

		returnString.append("\"name\":\"" + players.get(i).get_name() + "\""); // TODO escaped
		returnString.append(",\"score\":" + players.get(i).get_score());

		returnString.append("}");
	}
	returnString.append("]");

	// Get notes
	returnString.append(",\"notes\":[");
	for (int i = 0; i < Math.min(notes.size(), max_notes); i++) {
		if (i != 0) returnString.append(",\"");
		else returnString.append("\"");

		returnString.append(notes.get(i).toString());

		returnString.append("\"");
	}
	returnString.append("]");

	returnString.append("}");
	return returnString.toString();
}

public void skip_question (String player_id) {
	// TODO disallow skips with static variable, check valid playerid
	current_question = new Question ("Lorem Ipsum",
		"What is this sample text? This is some more padding. Some more filler text. (The answer is"
		+ " Lorem Ipsum dolor sit amet.)", this);
}

public void toggle_pause (String s) {
	// TODO disallow pauses with static variable, check valid playerid
	if (current_question.get_status() == Status.paused) current_question.unpause();
	else if (current_question.get_status() == Status.running) current_question.pause();
}

public Player get_player (String player_id) {
	for (Player p : players)
		if (p.get_id().equals(player_id))
			return p;
	return null;
}

}
