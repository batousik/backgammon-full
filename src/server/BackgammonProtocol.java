package server;

import game.Game;
import AI.AI;

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
			game.play(parseInput(input));
			return AI.lastMoveMade;
		}
		return null;
	}
	
	public static OpponentMove[] parseInput(String input) {
		String[] split = input.split(":");
		String diceRoll = split[0];
		int[] diceRollArray = {Integer.parseInt(diceRoll.substring(0,1)), Integer.parseInt(diceRoll.substring(1,2))};
		String[] startEndBracketBar = split[1].split(",");
		String moves[][] = new String[4][1];
		moves[0] = startEndBracketBar[0].split("|");
		moves[1] = startEndBracketBar[1].split("|");
		if (startEndBracketBar.length == 4) {
			moves[2] = startEndBracketBar[2].split("|");
			moves[3] = startEndBracketBar[3].split("|");
			OpponentMove[] output = new OpponentMove[4];
			for (int i = 0; i < output.length; i++) {
				output[i] = new OpponentMove(diceRollArray, Integer.parseInt(moves[i][0]), Integer.parseInt(moves[i][1]));
			}
			return output;
		}
		OpponentMove[] output = new OpponentMove[2];
		for (int i = 0; i < output.length; i++) {
			output[i] = new OpponentMove(diceRollArray, Integer.parseInt(moves[i][0]), Integer.parseInt(moves[i][1]));
		}
		return output;	
	}

	private void sendMove() {
			
	}
	
}
