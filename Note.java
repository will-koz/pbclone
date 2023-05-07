package pb;

import pb.Player;

public class Note {

private String color;
private String content;
private String header;

private Player p;

public Note (String _color, String _header, String _content, Player _p) {
	color = _color;
	content = _content;
	header = _header;
	p = _p;
}

public Note (String _color, String _header, String _content) {
	this(_color, _header, _content, null);
}

public Note (String _header, String _content, Player _p) { this("a0a0a0", _header, _content, _p); }
public Note (String _header, String _content) { this("a0a0a0", _header, _content); }

public String toString () {
	String returnString = "<div style='background-color: #";
	returnString += color;
	returnString += "30; border: 1px solid #";
	returnString += color;
	returnString += "a0'>";
	returnString += (p == null) ? header : String.format(header, p.get_name());
	returnString += "";
	if (!content.equals("")) {
		returnString += "<br />";
		returnString += (p == null) ? content : String.format(content, p.get_name());
	}
	returnString += "</div>";
	return returnString;
}

}
