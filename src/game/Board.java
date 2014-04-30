package game;

import java.util.ArrayList;

public class Board {
	public static final int WHITEBAR = 0, BLACKBAR = 25, WHITEBEAROF = 26,
			BLACKBEAROF = 27, TOTAL_NO_OF_FIELDS = 28,
			NO_OF_PLAYABLE_FIELDS = 26; // 0-25

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
		colorArray = new Color[TOTAL_NO_OF_FIELDS];
		amountArray = new int[TOTAL_NO_OF_FIELDS];
		validMoves = new ArrayList<Move>();

		// create default field
		for (int i = 0; i < TOTAL_NO_OF_FIELDS; i++) {
			switch (i) {
			case 1:
				colorArray[i] = Color.WHITE;
				amountArray[i] = 2;
				break;
			case 6:
			case 13:
				colorArray[i] = Color.BLACK;
				amountArray[i] = 5;
				break;
			case 8:
				colorArray[i] = Color.BLACK;
				amountArray[i] = 3;
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
				amountArray[i] = 2;
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
		//TODO REMOVE
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

	/**
	 * 
	 * @return checks if valid moves array list has moves left
	 * 
	 */
	public boolean isValidMovesLeft() {
		// if no possible moves turn changes
		if (validMoves.size() == 0)
			return false;
		return true;
	}

	public void searchForValideMoves() {
		evalDices();
		findValidMoves();
	}

	private void findValidMoves() {

		boolean barFlag = true;

		// playable area, going through all fields
		for (int i = 0; i < NO_OF_PLAYABLE_FIELDS; i++) {
			// move own piece check
			if (colorArray[i] == currPlayer && amountArray[i] > 0) {
				for (Integer amount : possibleMoves) {
					// but bear off move is weird

					if (isAllAtHome(currPlayer)) {
						// bear of move
						// goes of the grid
						if(currPlayer == Color.WHITE){
							if ((i + amount) >= BLACKBAR) {
								validMoves
								.add(new Move(MoveType.BEAROFF, i, amount, WHITEBEAROF));
							}
						} else {
							if ((i + amount) <= WHITEBAR){
								validMoves
								.add(new Move(MoveType.BEAROFF, i, amount, BLACKBEAROF));
							}
						}
					}

					// boundary check
					// has to be playable area only
					// move to: for white up untill BlackBar, for black up until
					// WHITEBAR
					if ((i + amount) < BLACKBAR && (i + amount) > WHITEBAR) {
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
			// for both players it need s to be checked so location 0 and 24
			// are checked
			if (barNotEmpty(currPlayer) && barFlag) {
				// counter will get increased by 1
				i = BLACKBAR - 1;
				barFlag = false;
			}

		}

		// TODO refine valid moves

		// TODO remove these
		System.out.println();
		for (int i = 0; i < validMoves.size(); i++) {
			System.out.println("[" + i + "] " + validMoves.get(i).toString());
		}
		System.out.println(currPlayer.toString());
		System.out.println();

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
	/**
	 * Method checks if all pieces for given player are in home quadrant 
	 * @param homePlayer current player
	 * @return true if all at home, false otherwise
	 */
	private boolean isAllAtHome(Color homePlayer) {
		// checks that no pieces of player color are outside home area
		// home for white 19-24 inclusive
		for ( int i = 0;i < colorArray.length; i++) {
			System.out.print(colorArray[i]+ " ");
		}
		System.out.println();
		for ( int i = 0;i < colorArray.length; i++) {
			System.out.print(amountArray[i]+ " ");
		}
		System.out.println();
		switch (homePlayer) {
		case WHITE:
			for (int i = WHITEBAR; i <= 18; i++) {
				if (colorArray[i] == Color.WHITE && amountArray[i] > 0) {
					return false;
				}
			}
			break;
		// home for black 1-6 inclusive
		case BLACK:
			for (int i = BLACKBAR; i >= 7; i--) {
				if (colorArray[i] == Color.BLACK && amountArray[i] > 0) {
					return false;
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * sets dices in case of a double, creates 4 dices
	 * 
	 * @param dices
	 */
	public void setDices(int[] dices) {
		if (dices[0] == dices[1]) {
			this.dices = new int[] { dices[0], dices[0], dices[0], dices[0] };
		} else {
			this.dices = dices;
		}
	}

	public void evalDices() {
		possibleMoves = new ArrayList<Integer>();
		// if no more dices left, possible moves = null, no valid moves
		if (dices == null) {
			return;
		}
		if (dices.length >= 3) {
			for (int i = 1; i <= dices.length; i++) {
				possibleMoves.add(dices[0] * i);
			}
		} else if (dices.length == 2) {
			if (dices[0] == dices[1]) {
				possibleMoves.add(dices[0]);
				possibleMoves.add(dices[0] + dices[0]);
			} else {
				possibleMoves.add(dices[0]);
				possibleMoves.add(dices[1]);
				// TODO indicates next line update
				possibleMoves.add(dices[0] + dices[1]);
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

		default:
			break;
		}
		validMoves.clear();
		useMove(chosenMove.getMoveAmount());
	}

	private void useMove(int moveAmount) {
		switch (dices.length) {
		case 4:
			if (moveAmount == dices[0]) {
				dices = new int[] { dices[0], dices[0], dices[0] };
			} else if (moveAmount == dices[0] * 2) {
				dices = new int[] { dices[0], dices[0] };
			} else if (moveAmount == dices[0] * 3) {
				dices = new int[] { dices[0] };
			} else {
				dices = null;
			}
			break;
		case 3:
			if (moveAmount == dices[0]) {
				dices = new int[] { dices[0], dices[0] };
			} else if (moveAmount == dices[0] * 2) {
				dices = new int[] { dices[0] };
			} else {
				dices = null;
			}
			break;
		case 2:
			if (moveAmount == dices[0] + dices[1]) {
				dices = null;
			} else if (moveAmount == dices[0]) {
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

	/**
	 * implements rule, where if with some moves you can use more moves, you
	 * have to use them, and other moves become invalid
	 */
	private void refineValidMoves() {

	}

	public ArrayList<Move> getValidMoves() {
		return this.validMoves;
	}

	public void setCurrentPlayer(Color color) {
		this.currPlayer = color;
	}

	public Color getCurrentPlayer() {
		return this.currPlayer;
	}
}
