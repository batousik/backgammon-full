package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	int integ;
	
	private Dice dice;
	private Board board;
	private boolean movesLeft;
	public Game() {
		dice = new Dice();
		board = new Board();
		play();
	}
	
	private void play() {
		dice.trowDices();
		board.setDices(dice.getDices());
		movesLeft = true;
		while(movesLeft) {
			
			board.getPossibleMoves();
			
			System.out.print("Enter choice");
		
		    try{
		         integ = Integer.parseInt(br.readLine());
		    }catch(NumberFormatException nfe){
		        System.err.println("Invalid Format!");
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			board.move(integ);
			movesLeft = board.isMovesLeft();
		}
	}
}
