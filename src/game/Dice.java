package game;

import java.util.Random;

public class Dice {
	private Random rn;
	private int diceX;
	private int diceY;

	public Dice() {
		rn = new Random();
	}

	public void throwDices() {
		diceX = 1 + rn.nextInt(5);
		diceY = 1 + rn.nextInt(5);
	}
	
	public int[] getDices() {
		return new int[]{diceX,diceY};
	}
}
