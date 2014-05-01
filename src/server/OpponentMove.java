package server;

public class OpponentMove {
	
	private int[] diceRoll;
	private int startPos;
	private int endPos;
	private int numberMoves;
	
	public OpponentMove(int[] diceRoll, int startPos, int endPos) {
		this.diceRoll = diceRoll;
		this.startPos = startPos;
		this.endPos = endPos;
	}
	
	public int[] getDiceRoll() {
		return diceRoll;
	}
	
	public int getStartPos() {
		return startPos;
	}
	
	public int getEndPos() {
		return endPos;
	}
}
