package pb;

import java.util.ArrayList;

import pb.Question;
import pb.Player;

public class Game {

private Question current_question;
private ArrayList<Player> players;

public Game () {
	current_question = new Question ("Lorem Ipsum", "What is this sample text?");
	players = new ArrayList<Player>();
}

public void addPlayer (String s) {
	// TODO look up if player with this id exists
	players.add(new Player (s));
}

}
