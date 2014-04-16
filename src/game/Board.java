package game;

public class Board {
	public static final int WHITEBAR = 24, BLACKBAR = 0, WHITEBEAROF = 25,
			BLACKBEAROF = 26;

	private Field[] fields;

	public Board() {
		// creating fields on the board
		// [id] 0-23 playable,
		// 2 for captured pieces (bar),
		// [id] 24/25 white/black
		// 2 for taken away from board pieces (bear off),
		// [id] 26/27 white/black
		// total 28
		fields = new Field[28];

		for (int i = 0; i < fields.length; i++) {
			switch (i) {
			case 1:
				fields[i] = new Field(Color.BLACK, 2);
				break;
			case 6:
			case 13:
				fields[i] = new Field(Color.WHITE, 5);
				break;
			case 8:
				fields[i] = new Field(Color.WHITE, 3);
				break;
			case 12:
			case 19:
				fields[i] = new Field(Color.BLACK, 5);
				break;
			case 17:
				fields[i] = new Field(Color.BLACK, 3);
				break;
			case 24:
				fields[i] = new Field(Color.WHITE, 2);
				break;

			default:
				fields[i] = new Field();
				break;
			}
		}
		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i]);
		}
	}

	public void move(int position, int amount, Color curPlayer) {
		fields[position].takePiece();
		fields[position + amount].putPiece(curPlayer);
	}

	public void captureMove(int position, int amount, Color curPlayer) {
		// taking away captured piece
		fields[position + amount].takePiece();
		// if current player moving is white then black was captured and vice
		// versa
		if (curPlayer == Color.WHITE) {
			fields[BLACKBAR].putPiece(Color.BLACK);
		} else {
			fields[WHITEBAR].putPiece(Color.WHITE);
		}

		// make original move
		fields[position].takePiece();
		fields[position + amount].putPiece(curPlayer);
	}

	public void bearOfMove(int position, int amount, Color curPlayer) {
		// taking away piece to bear off
		fields[position].takePiece();
		// if current player moving is white then move of white, same for black
		if (curPlayer == Color.WHITE) {
			fields[WHITEBEAROF].putPiece(Color.WHITE);
		} else {
			fields[BLACKBEAROF].putPiece(Color.BLACK);
		}
	}

	public Field[] getFields() {
		return fields;
	}
}
