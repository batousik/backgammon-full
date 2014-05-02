package game;

/**
 * 
 * @author 130017964
 * @version 4.20(release)
 * 
 *          Class stores move for Backgammon
 * 
 *          stores start position, amount moved, end position
 * 
 *          in special case for bear off move stores amount from dice needed and
 *          actual amount needed to reach bear off
 * 
 */

public class Move {
	private MoveType moveType;
	private int startField;
	private int endField;
	private int moveAmount;
	private int specialBearOff;

	public Move(MoveType mt, int start, int moveAmount) {
		moveType = mt;
		startField = start;
		this.moveAmount = moveAmount;
		endField = start + moveAmount;
	}

	// for bear off move
	public Move(MoveType mt, int start, int moveAmount, int endPos,
			int actualMoveAmount) {
		moveType = mt;
		startField = start;
		this.moveAmount = moveAmount;
		endField = endPos;
		specialBearOff = actualMoveAmount;
	}

	@Override
	public String toString() {
		return moveType.name() + " " + startField + " + " + moveAmount + " = "
				+ endField;
	}

	public MoveType getMoveType() {
		return moveType;
	}

	public int getStartField() {
		return startField;
	}

	public int getEndField() {
		return endField;
	}

	public int getMoveAmount() {
		return moveAmount;
	}

	public int getBEAROFFMoveAmount() {
		return specialBearOff;
	}
}
