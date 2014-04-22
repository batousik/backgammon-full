package game;

public class Field {
	private Color colorOfPieces;
	private int noOfPieces;
	
	// constructor for filled field
	public Field(Color color, int amount) {
		this.noOfPieces = amount;
		this.colorOfPieces = color;
	}
	
	// constructor for empty field
	public Field() {
		colorOfPieces = Color.NONE;
		noOfPieces = 0;
	}
	
	// takes away piece from the field, if no pieces left sets it to null type field
	public void takePiece() {
		noOfPieces--;
		if (noOfPieces == 0) {
			colorOfPieces = Color.NONE;
		}
	}
	
	// adds a piece to the field, if field was empty
	// sets the color, else assumes color is same as added
	// and increments number of pieces 
	public void putPiece(Color color) {
		if (colorOfPieces == Color.NONE) {
			this.colorOfPieces = color;
			noOfPieces = 1;
		} else {
			noOfPieces++;
		}
	}
		

	public Color getColorOfPieces() {
		return colorOfPieces;
	}

	public int getNoOfPieces() {
		return noOfPieces;
	}

	@Override
	public String toString() {
		return colorOfPieces + " amount: " + noOfPieces;
	}
}
