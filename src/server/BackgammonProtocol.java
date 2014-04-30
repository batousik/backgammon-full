package server;

import game.Game;

public class BackgammonProtocol {
	private static final int WAITING_FOR_CLIENT = 0;
	private static final int SENT_HELLO_RESPONSE = 1;
	private static final int SENDING_MOVES = 2;
	
	private int state = WAITING_FOR_CLIENT;
	
	public String process(String input, Game game) {
		if (state == WAITING_FOR_CLIENT) {
			state = SENT_HELLO_RESPONSE;
			return "hello";
		}
		if (state == SENT_HELLO_RESPONSE) {
			state = SENDING_MOVES;
			return "ok";
		}
		if (state == SENDING_MOVES) {
			game.play(input);
			return game.getMoveMade();
		}
		return null;
	}
}
