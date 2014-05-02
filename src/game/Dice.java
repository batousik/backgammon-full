package game;

import java.util.Random;

/**
 * 
 * @author 130017964
 * @version 4.20(release)
 * 
 *          Used to generate pair of random values in range (1-6) for backgammon
 *          dice throw
 */
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
		// TODO change
		// return new int[]{diceX,diceY};
		return new int[] { diceX, diceY};
	}
}
