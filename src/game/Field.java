package game;

public class Field {
	public enum Color {WHITE, BLACK, NONE};
	
	private Color colorOfPieces;
	private int noOfPieces;
	
	public Field(Color color, int amount) {
		this.noOfPieces = amount;
		this.colorOfPieces = color;
	}
	
	public Field() {
		colorOfPieces = Color.NONE;
		noOfPieces = 0;
	}
	
	@Override
	public String toString() {
		return "Color: " + colorOfPieces + " amount: " + noOfPieces;
	}
}
