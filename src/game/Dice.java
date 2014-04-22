package game;

import java.util.ArrayList;
import java.util.Random;

public class Dice {
	private Random rn;
	private int diceX;
	private int diceY;

	public Dice() {
		rn = new Random();
	}

	public void trowDices() {
		diceX = 1 + rn.nextInt(5);
		diceY = 1 + rn.nextInt(5);
	}
	
	public int[] getDices() {
		return new int[]{diceX,diceY};
	}

//	public ArrayList<Integer> getEvalValue() {
//		returnVals = new ArrayList<Integer>();
//		if (diceX == diceY) {
//			for (int i = 0; i < 4; i++) {
//				returnVals.add(diceX);
//			}
//			return returnVals;
//		} else {
//			returnVals.add(diceX);
//			returnVals.add(diceY);
//			return returnVals;
//		}
//	}
}
