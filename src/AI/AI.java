package AI;

import java.util.ArrayList;

import server.Server;
import game.*;

public class AI {
	
	public static final int BEAROFF_WEIGHT = 125;
	public static final int CAPTURE_WEIGHT = 5;
	public static final int SINGLE_CHECKER_WEIGHT = -8;
	public static final int ENTER_HOME_WEIGHT = 15;
	public static String lastMoveMade;
	
	private ArrayList<Move> possibleMoves;
	private ScoredMove[] scoredMoves;
	private int bestMoveIndex;
	private String moveMade;
	private Board board;
	
	public AI (Board board) {
		this.board = board;
		this.possibleMoves = board.getValidMoves();
		scoredMoves = new ScoredMove[possibleMoves.size()];
		for (int i = 0; i < possibleMoves.size(); i++) {
			scoredMoves[i] = new ScoredMove(possibleMoves.get(i));
		}
		int max = 0;
		for (int i = 0; i < possibleMoves.size(); i++) {
			int score = scoredMoves[i].getScore();
			if (score > max) {
				bestMoveIndex = i;
			}
		}
		board.move(bestMoveIndex);
		lastMoveMade = parseMoveMade(possibleMoves.get(bestMoveIndex));
	}
	
	public String parseMoveMade(Move moveMade) {
		board.getDices();
		moveMade.getEndField();
		moveMade.getStartField();
		return "(" + moveMade.getStartField() + "|" + moveMade.getEndField() + ")";
	}
}
