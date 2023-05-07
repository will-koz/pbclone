import pb.Game;
import pb.PBServer;

public class Program {

public static void main (String[] args) {
	Game g = new Game ();
	try {
		PBServer p = new PBServer(g);
	} catch (Exception e) {}
}

}
