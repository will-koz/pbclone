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
	if (current_question != null) {
		returnString.append(current_question.get_public_question()); // TODO make sure is escaped
		returnString.append("\", \"progress\":" + Math.floor(current_question.progress() * 100));
	}
	else returnString.append("waiting for the question to begin\"");

	// Get player information

	returnString.append("}");
	return returnString.toString();
}

public void skip_question (String player_id) {
	// TODO disallow skips with static variable, check valid playerid
	current_question = new Question ("Lorem Ipsum",
		"What is this sample text? This is some more padding. Some more filler text. (The answer is"
		+ " Lorem Ipsum dolor sit amet.)");
}

public Player get_player (String player_id) {
	for (Player p : players)
		if (p.get_id().equals(player_id))
			return p;
	return null;
}

}
