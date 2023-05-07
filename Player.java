package pb;

public class Player {

private String id;
private String name;
private int score;

public Player (String _id) {
	id = _id;
	name = "Jane Austen";
	score = 0;
}

public String get_id () { return id; }
public String get_name () { return name; }
public int get_score () { return score; }

public void set_name (String _name) { name = _name; }

public void add_score (int s) { score += s; }

}
