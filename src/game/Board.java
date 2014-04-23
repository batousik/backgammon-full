package game;

import java.util.ArrayList;

public class Board {
	public static final int WHITEBAR = 0, BLACKBAR = 25, WHITEBEAROF = 26,
			BLACKBEAROF = 27, TOTAL = 28, PLAYABLE = 26; // 0-25

	private Color[] colorArray;
	private int[] amountArray;

	private ArrayList<Move> validMoves;
	private ArrayList<Integer> possibleMoves;
	private int[] dices;

	private Color opponent;
	private Color currPlayer;

	public Board() {
		// creating fields on the board
		// [id] 1-24 playable,
		// 2 for captured pieces (bar),
		// [id] 0/ white/black
		// 2 for taken away from board pieces (bear off),
		// [id] 26/27 white/black
		// total 28
		colorArray = new Color[TOTAL];
		amountArray = new int[TOTAL];
		validMoves = new ArrayList<Move>();

		for (int i = 0; i < TOTAL; i++) {
			switch (i) {
			case 1:
				colorArray[i] = Color.WHITE;
				amountArray[i] = 2;
				// 2
				break;
			case 6:
			case 13:
				colorArray[i] = Color.BLACK;
				amountArray[i] = 1;
				// 5
				break;
			case 8:
				colorArray[i] = Color.BLACK;
				amountArray[i] = 1;
				// 3
				break;
			case 12:
			case 19:
				colorArray[i] = Color.WHITE;
				amountArray[i] = 5;
				break;
			case 17:
				colorArray[i] = Color.WHITE;
				amountArray[i] = 3;
				break;
			case 24:
				colorArray[i] = Color.BLACK;
				amountArray[i] = 1;
				// 2
				break;
			case WHITEBEAROF:
				colorArray[i] = Color.WHITE;
				amountArray[i] = 0;
				break;
			case BLACKBEAROF:
				colorArray[i] = Color.BLACK;
				amountArray[i] = 0;
				break;
			case WHITEBAR:
				colorArray[i] = Color.WHITE;
				amountArray[i] = 0;
				break;
			case BLACKBAR:
				colorArray[i] = Color.BLACK;
				amountArray[i] = 0;
				break;
			default:
				colorArray[i] = Color.NONE;
				amountArray[i] = 0;
				break;
			}
		}
		// for (int i = 0; i < 28; i++) {
		// System.out.println(colorArray[i] + " " + amountArray[i]);
		// }
	}

	public boolean isMovesLeft() {
		if (dices == null)
			return false;
		return true;
	}

	public void setPlayers(boolean isWhite) {
		if (isWhite) {
			currPlayer = Color.WHITE;
			opponent = Color.BLACK;
		} else {
			currPlayer = Color.BLACK;
			opponent = Color.WHITE;
			for (int i = 0; i < dices.length; i++) {
				dices[i] *= (-1);
			}
		}
	}

	public boolean getPossibleMoves() {
		boolean barFlag = true;
		evalDices();

		System.out.println(possibleMoves);

		// playable area, going through all fields
		for (int i = 0; i < PLAYABLE; i++) {
			// move own color check
			if (colorArray[i] == currPlayer && amountArray[i] > 0) {
				for (Integer amount : possibleMoves) {
					// but bear off move is weird

					if (allAtHome(currPlayer)) {
						// bear of move
						// goes of the grid
						if ((i + amount) > 24 || (i + amount) < 0) {
							validMoves
									.add(new Move(MoveType.BEAROFF, i, amount));
						}
					}

					// boundary check
					// has to be playable area only

					if ((i + amount) < PLAYABLE && (i + amount) > 0) {
						// pieces from bar have to be moved first

						// move to own color or empty
						if (colorArray[i + amount] == currPlayer
								|| colorArray[i + amount] == Color.NONE) {
							validMoves
									.add(new Move(MoveType.NORMAL, i, amount));
							// hit move
						} else if (colorArray[i + amount] == opponent
								&& amountArray[i + amount] == 1) {
							validMoves
									.add(new Move(MoveType.CAPTURE, i, amount));
						}
					}
				}
			}
			// only moves from bar are allowed if it is not empty
			if (barNotEmpty(currPlayer)&&barFlag) {
				i = BLACKBAR - 1;
				barFlag = false;
			}

		}

		System.out.println();
		for (int i = 0; i < validMoves.size(); i++) {
			System.out.println("[" + i + "] " + validMoves.get(i).toString());
		}
		System.out.println();
		// if no valid moves turn changes
		if (validMoves.size() == 0)
			return false;
		return true;
	}

	public Color[] getColorArray() {
		return colorArray;
	}

	public int[] getAmountArray() {
		return amountArray;
	}

	private boolean barNotEmpty(Color currPlayer) {
		if ((currPlayer == Color.WHITE && amountArray[WHITEBAR] != 0)
				|| (currPlayer == Color.BLACK && amountArray[BLACKBAR] != 0))
			return true;
		return false;
	}

	private boolean allAtHome(Color homePlayer) {
		// checks that no pieces of player color are outside home area
		// home for white 19-24 inclusive
		switch (homePlayer) {
		case WHITE:
			for (int i = WHITEBAR; i < 19; i++) {
				if (colorArray[i] == Color.WHITE) {
					return false;
				}
			}
			break;
		// home for black 1-6 inclusive
		case BLACK:
			for (int i = BLACKBAR; i > 6; i--) {
				if (colorArray[i] == Color.WHITE) {
					return false;
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

	public void setDices(int[] dices) {
		if (dices[0] == dices[1]) {
			this.dices = new int[] { dices[0], dices[0], dices[0], dices[0] };
		} else {
			this.dices = dices;
		}
	}

	public void evalDices() {
		possibleMoves = new ArrayList<Integer>();

		if (dices.length == 2) {
			if (dices[0] == dices[1]) {
				possibleMoves.add(dices[0]);
			} else {
				possibleMoves.add(dices[0]);
				possibleMoves.add(dices[1]);
			}
		} else {
			possibleMoves.add(dices[0]);
		}
	}

	public void move(int moveIndex) {
		Move chosenMove = validMoves.get(moveIndex);
		int start = chosenMove.getStartField();
		int end = chosenMove.getEndField();
		switch (chosenMove.getMoveType()) {
		case NORMAL:
			// make normal move
			amountArray[start] -= 1;
			amountArray[end] += 1;

			// check if field is left empty
			if (amountArray[start] == 0)
				colorArray[start] = Color.NONE;

			// check if moved to empty field
			if (colorArray[end] == Color.NONE)
				colorArray[end] = currPlayer;
			break;
		case CAPTURE:
			amountArray[start] -= 1;
			// amount at [end] stays same

			// check if field is left empty
			if (amountArray[start] == 0)
				colorArray[start] = Color.NONE;

			// change color after capturing field
			colorArray[end] = currPlayer;

			// capture piece
			if (currPlayer == Color.WHITE) {
				amountArray[BLACKBAR] += 1;
			} else {
				amountArray[WHITEBAR] += 1;
			}
			break;
		case BEAROFF:
			amountArray[start] -= 1;
			if (currPlayer == Color.WHITE) {
				amountArray[WHITEBEAROF] += 1;
			} else {
				amountArray[BLACKBEAROF] += 1;
			}
			// check if field is left empty
			if (amountArray[start] == 0)
				colorArray[start] = Color.NONE;
			// TODO
		default:
			break;
		}
		validMoves.clear();
		useMove(chosenMove.getMoveAmount());
	}

	private void useMove(int moveAmount) {
		switch (dices.length) {
		case 4:
			dices = new int[] { dices[0], dices[0], dices[0] };
			break;
		case 3:
			dices = new int[] { dices[0], dices[0] };
			break;
		case 2:
			if (moveAmount == dices[0]) {
				dices = new int[] { dices[1] };
			} else {
				dices = new int[] { dices[0] };
			}
			// check 1st = amount, trough away 1, left 2
			// check 2nd = amount, viceversa
			// no more moves
			break;
		case 1:
			dices = null;
			// no more moves
			break;

		default:
			break;
		}
	}

}
