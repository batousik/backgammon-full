package simpleAI;

import game.*;

public class ScoredMove {
	
	private int score;
	private Move move;;
	
	public ScoredMove(Move move) {
		this.move = move;
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
		if (this.move.getEndField() <= 5) {
			score += AI.ENTER_HOME_WEIGHT;
		}
	}
}
