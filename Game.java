package pb;

import java.lang.StringBuilder;
import java.util.ArrayList;

import pb.Question;
import pb.Player;

public class Game {

private Question current_question;
private ArrayList<Player> players;

public Game () {
	players = new ArrayList<Player>();
}

public void addPlayer (String s) {
	// TODO look up if player with this id exists
	players.add(new Player (s));
}

public void change_name (String s) {
	String[] data = s.split(",", 2);
	get_player(data[0].trim()).set_name(data[1].trim());
}

public String getJSON () {
	StringBuilder returnString = new StringBuilder("{");

	// Get question information
	returnString.append("\"question\":\"");
	if (current_question != null && current_question.get_status() != Status.paused) {
		returnString.append(current_question.get_public_question()); // TODO make sure is escaped
		returnString.append("\",\"progress\":" + Math.floor(current_question.progress() * 100));
		returnString.append(",\"end\":" + current_question.get_end_date());
	}
	else if (current_question != null && current_question.get_status() == Status.paused)
		returnString.append("\""); // Give no more information about the question
	else returnString.append("waiting for the question to begin\"");

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

	returnString.append("}");
	return returnString.toString();
}

public void skip_question (String player_id) {
	// TODO disallow skips with static variable, check valid playerid
	current_question = new Question ("Lorem Ipsum",
		"What is this sample text? This is some more padding. Some more filler text. (The answer is"
		+ " Lorem Ipsum dolor sit amet.)");
}

public void toggle_pause (String s) {
	// TODO disallow pauses with static variable, check valid playerid
	System.out.println("Toggle pause request from " + s);
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
