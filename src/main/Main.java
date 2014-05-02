package main;

import game.Board;
import gui.MainWindow;

public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		board.setDices(new int [] {6,6});
		board.setPlayers(true);
		board.searchForValidMoves();
//
//		Board boardClone = new Board(board);
//		board.setDices(new int [] {5,5});
//		board.setPlayers(false);
//		board.searchForValidMoves();
//	
//		
//
//		
//		System.out.println("MAIN: " + board.getValidMoves());
//		System.out.println("CLONE: " + boardClone.getValidMoves());
		//MainWindow mw = new MainWindow();
	}
}
