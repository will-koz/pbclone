package pb;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import pb.PBStrings;

public class PBServer {

private Game game;
private HttpContext index;
private HttpServer server;

enum Type { css, game, index, js, join }

class PBHandler implements HttpHandler {
	private Type type;

	@Override
	public void handle (HttpExchange exchange) throws IOException {
		String response = "";
		int status = 404;

		if ("POST".equals(exchange.getRequestMethod())) {
			// Convert InputStreamReader -> BufferedReader -> StringBuilder -> String -> JsonObject
			BufferedReader streamReader = new BufferedReader (
				new InputStreamReader(exchange.getRequestBody(), "UTF-8"));
			StringBuilder responseStringBuilder = new StringBuilder();
			String inputString;
			while ((inputString = streamReader.readLine()) != null)
				responseStringBuilder.append(inputString);

			switch (type) {
				case join: game.addPlayer(responseStringBuilder.toString()); break;
			}
			status = 204;
		}
		else {
	 		switch (type) {
				case index: response = PBStrings.index_html; break;
				case css: response = PBStrings.index_css; break;
				case js: response = PBStrings.index_js; break;
				case game: response = "ggame"; break;
			}
			status = 200;
		}

		exchange.sendResponseHeaders(200, response.getBytes().length);
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	public PBHandler (Type _type) { type = _type; }
}

public PBServer (Game _game) throws IOException {
	game = _game;

	server = HttpServer.create(new InetSocketAddress(12345), 0);

	// GET contexts
	server.createContext("/", new PBHandler(Type.index)); // Returns the client HTML
	server.createContext("/css", new PBHandler(Type.css)); // style information for the webpage
	server.createContext("/js", new PBHandler(Type.js)); // javascript for the webpage
	server.createContext("/game", new PBHandler(Type.game)); // JSON data related to the room

	// POST contexts
	server.createContext("/join", new PBHandler(Type.join)); // New player provides ID

	server.setExecutor(null); // I don't know what this does
	server.start();
}

}
