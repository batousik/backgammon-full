package game;

import javax.swing.SwingUtilities;

import gui.MainWindow;

public class BackGammon {
	private Game game;
	private MainWindow gui;
	
	public BackGammon() {
		SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	            	playGame();
	            }
	        });
	}
	
	private void playGame(){
		game = new Game();
		gui = new MainWindow();
	}
}
