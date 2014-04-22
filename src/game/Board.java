package game;

import java.util.ArrayList;

public class Board {
	public static final int WHITEBAR = 0, BLACKBAR = 25, WHITEBEAROF = 26,
			BLACKBEAROF = 27, TOTAL = 28;

	private Color[] colorArray;
	private int[] amountArray;

	private ArrayList<Move> validMoves;
	private ArrayList<Integer> possibleMoves;
	private int[] dices;

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

			default:
				colorArray[i] = Color.NONE;
				amountArray[i] = 0;
				break;
			}
		}
		for (int i = 0; i < 28; i++) {
			System.out.println(colorArray[i] + " " + amountArray[i]);
		}
	}

	public boolean isMovesLeft() {
		if (dices == null)
			return false;
		return true;
	}
	// public void move(int position, int amount, Color curPlayer) {
	// fields[position].takePiece();
	// fields[position + amount].putPiece(curPlayer);
	// }

	public void getPossibleMoves() {
		evalDices();
		System.out.println(possibleMoves);
		for (int i = 0; i < TOTAL; i++) {
			// WHITE ONLY
			if (colorArray[i] == Color.WHITE) {
				for (Integer amount : possibleMoves) {
					// boundary check
					// has to be playable area only
					// but bear off move is weird
					if ((i + amount) < TOTAL) {
						// move own pieces check
						if (colorArray[i] == Color.WHITE) {
							// bear of move

							// move to own color or empty
							if (colorArray[i + amount] == Color.WHITE
									|| colorArray[i + amount] == Color.NONE) {
								validMoves.add(new Move(MoveType.NORMAL, i, i
										+ amount, amount));
								// hit move
							} else if (colorArray[i + amount] == Color.BLACK
									|| amountArray[i + amount] == 1) {
								validMoves.add(new Move(MoveType.CAPTURE, i, i
										+ amount, amount));
							}
						}
					}
				}
			}
		}
		System.out.println(validMoves);
	}

	public void setDices(int[] dices) {
		if (dices[0] == dices[1]) {
			this.dices = new int[] { dices[0], dices[0], dices[0], dices[0] };
		} else {
			this.dices = new int[] { dices[0], dices[1] };
		}
	}

	public void evalDices() {
		possibleMoves = new ArrayList<Integer>();

		switch (dices.length) {
		case 4:
			for (int i = 1; i < 5; i++) {
				possibleMoves.add(dices[0] * i);
			}
			break;
		case 3:
			for (int i = 1; i < 4; i++) {
				possibleMoves.add(dices[0] * i);
			}
			break;
		case 2:
			possibleMoves.add(dices[0]);
			possibleMoves.add(dices[1]);
			possibleMoves.add(dices[1] + dices[0]);
			break;
		case 1:
			possibleMoves.add(dices[0]);
			break;

		default:
			break;
		}
	}

	public void move(int moveIndex) {
		Move chosenMove = validMoves.get(moveIndex);
		int start = chosenMove.getStartField();
		int end = chosenMove.getEndField();
		switch (chosenMove.getMoveType()) {
		case NORMAL:
			// make normal move
			amountArray[start] = amountArray[start] - 1;
			amountArray[end] = amountArray[end] + 1;

			// check if field is left empty
			if (amountArray[start] == 0)
				colorArray[start] = Color.NONE;

			// check if moved to empty field
			if (colorArray[end] == Color.NONE)
				colorArray[end] = Color.WHITE;
			break;
		case CAPTURE:
			amountArray[start] = amountArray[start] - 1;
			// amount at [end] stays same

			// check if field is left empty
			if (amountArray[start] == 0)
				colorArray[start] = Color.NONE;

			// change color after capturing field
			colorArray[end] = Color.WHITE;
			break;
		default:
			break;
		}
		validMoves.clear();
		useMove(chosenMove.getMoveAmount());
	}

	private void useMove(int moveAmount) {

		int diceAddUp = 0;
		
		switch (dices.length) {
		case 4:
			if (moveAmount == dices[0]) {
				dices = new int[]{dices[0],dices[0],dices[0]};
			} else if (moveAmount == (dices[0]*2)){
				dices = new int[]{dices[0],dices[0]};
			} else if (moveAmount == (dices[0]*3)){
				dices = new int[]{dices[0]};
			} else {
				dices = null;
			}

			// check 1*1 = amount, trough away 1, left 2,3,4
			// check 1*2 = amount, left 3,4
			// check 1*3 = amount, left 4
			// no more moves
			break;
		case 3:
			if (moveAmount == dices[0]) {
				dices = new int[]{dices[0],dices[0]};
			} else if (moveAmount == (dices[0]*2)){
				dices = new int[]{dices[0]};
			} else {
				dices = null;
			}
			// check 1 = amount, trough away 1, left 2,3
			// check 1*2 = amount, through away 1,2 left 3
			// no more moves
			break;
		case 2:
			if (moveAmount == dices[0]) {
				dices = new int[]{dices[1]};
			} else if (moveAmount == (dices[1])){
				dices = new int[]{dices[0]};
			} else {
				dices = null;
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

	// public void captureMove(int position, int amount, Color curPlayer) {
	// // taking away captured piece
	// fields[position + amount].takePiece();
	// // if current player moving is white then black was captured and vice
	// // versa
	// if (curPlayer == Color.WHITE) {
	// fields[BLACKBAR].putPiece(Color.BLACK);
	// } else {
	// fields[WHITEBAR].putPiece(Color.WHITE);
	// }
	//
	// // make original move
	// fields[position].takePiece();
	// fields[position + amount].putPiece(curPlayer);
	// }
	//
	// public void bearOfMove(int position, int amount, Color curPlayer) {
	// // taking away piece to bear off
	// fields[position].takePiece();
	// // if current player moving is white then move of white, same for black
	// if (curPlayer == Color.WHITE) {
	// fields[WHITEBEAROF].putPiece(Color.WHITE);
	// } else {
	// fields[BLACKBEAROF].putPiece(Color.BLACK);
	// }
	// }
	//
	// public Field[] getFields() {
	// return fields;
	// }
}
