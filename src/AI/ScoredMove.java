package AI;

import game.*;

public class ScoredMove {
	
	private int score;
	private Move move;
	private Board board;
	
	public ScoredMove(Move move, Board board) {
		this.move = move;
		this.board = board;
		getMoveScore();
	}
	
	public int getScore() {
		return this.score;
	}
	
	private void getMoveScore() {
		if (this.move.getMoveType() == MoveType.BEAROFF) {
			score += AI.BEAROFF_WEIGHT;
		}
		if (this.move.getMoveType() == MoveType.CAPTURE) {
			score += AI.CAPTURE_WEIGHT;
		}
		if (board.getCurrentPlayer().equals(Color.SILVER)) {
			if (this.move.getEndField() <= 6) {
				score += AI.ENTER_HOME_WEIGHT;
			}
		}
		else {
			if (this.move.getEndField() >= 19) {
				score += AI.ENTER_HOME_WEIGHT;
			}
		}
	}
}
