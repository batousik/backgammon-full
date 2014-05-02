package game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import server.OpponentMove;
import AI.AI;

/**
 * 
 * @author 130017964
 * @version 4.20(release)
 * 
 * Text Based version of backgammon
 */

public class Game {

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int userInput;

	private Dice dice;
	private Board board;
	private boolean movesLeft;
	private boolean isWhite;

	public Game() {
		dice = new Dice();
		board = new Board();
	}

	public void play(OpponentMove[] opponentMove) {
		board.setDices(opponentMove[0].getDiceRoll());
		board.setPlayers(true);
		board.searchForValidMoves();
		movesLeft = board.hasValidMovesLeft();

		for (int j = 0; j < opponentMove.length; j++) {
			for (int i = 0; i < board.getValidMoves().size(); i++) {
				if (opponentMove[j].getStartPos() == board.getValidMoves()
						.get(i).getStartField()) {
					if (opponentMove[j].getEndPos() == board.getValidMoves()
							.get(i).getEndField()) {
						board.move(i);
					}
				}
			}
		}

		changePlayer();
		dice.throwDices();
		board.setDices(dice.getDices());
	}

	// TODO remove text based interface

	/** 
	 * CLI representaion
	 * @version 1.0
	 */
	private void draw() {
		System.out.println();
		for (int i = 13; i < 25; i++) {
			if (board.getAmountArray()[i] != 0) {
				System.out.print("| " + board.getAmountArray()[i] + " "
						+ board.getColorArray()[i] + " | ");
			} else {
				System.out.print("|         | ");
			}
		}
		if ((board.getAmountArray()[25] != 0)) {
			System.out.print("| " + board.getAmountArray()[25] + " BB | ");
		} else {
			System.out.print("|         | ");
		}
		System.out.println();
		System.out
				.println("-----------------------------------------------------------");
		for (int i = 12; i > 0; i--) {
			if (board.getAmountArray()[i] != 0) {
				System.out.print("| " + board.getAmountArray()[i] + " "
						+ board.getColorArray()[i] + " | ");
			} else {
				System.out.print("|         | ");
			}
		}
		if ((board.getAmountArray()[0] != 0)) {
			System.out.print("| " + board.getAmountArray()[0] + " WB | ");
		} else {
			System.out.print("|         | ");
		}
		System.out.println();

	}

	private void changePlayer() {
		if (isWhite) {
			isWhite = false;
		} else {
			isWhite = true;
		}
	}
}