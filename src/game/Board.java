package game;

public class Board {
	private Field[] fields;

	public Board() {
		// creating fields on the board 24 playable, 2 for captured pieces, [id]
		// 24 and 25 white/black respectively
		fields = new Field[26];
		for (int i = 0; i < fields.length; i++) {
			switch (i) {
			case 0:
				fields[i] = new Field(Field.Color.BLACK, 2);
				break;
			case 5:
			case 12:
				fields[i] = new Field(Field.Color.WHITE, 5);
				break;
			case 7:
				fields[i] = new Field(Field.Color.WHITE, 3);
				break;
			case 11:
			case 18:
				fields[i] = new Field(Field.Color.BLACK, 5);
				break;
			case 16:
				fields[i] = new Field(Field.Color.BLACK, 3);
				break;
			case 23:
				fields[i] = new Field(Field.Color.WHITE, 2);
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
}
