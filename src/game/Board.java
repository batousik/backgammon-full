package game;


public class Board {
	public static final int WHITEBAR = 24, BLACKBAR = 25, WHITEBEAROF = 26,
			BLACKBEAROF = 27;

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
			case 0:
				fields[i] = new Field(Color.BLACK, 2);
				break;
			case 5:
			case 12:
				fields[i] = new Field(Color.WHITE, 5);
				break;
			case 7:
				fields[i] = new Field(Color.WHITE, 3);
				break;
			case 11:
			case 18:
				fields[i] = new Field(Color.BLACK, 5);
				break;
			case 16:
				fields[i] = new Field(Color.BLACK, 3);
				break;
			case 23:
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
	

	public Field[] getFields() {
		return fields;
	}
}
