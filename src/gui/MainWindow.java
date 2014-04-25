package gui;

import game.Board;
import game.Dice;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements ActionListener, ItemListener,
		MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int NUMBER_OF_STONES = 30;
	private JPanel mainContentPane;
	private JLayeredPane gamePanel;
	private JLabel background;
	private JMenuBar menuBar;
	private JMenu menuGameTitle, aboutGameTitle;
	private JMenuItem newGame, exit, help;
	private JLabel[] stones;
	private Dimension preferredSize;
	private int stoneDragged = NUMBER_OF_STONES;
	private int clickX = 0;
	private int clickY = 0;
	private HashMap<game.Color, String> stoneImage;
	private HashMap<Integer, int[]> locations;
	
	private int currStone;
	
	public MainWindow() {
		createGUI();
		stoneImage = new HashMap<game.Color, String>();
		stoneImage.put(game.Color.BLACK, "SilverNSH.gif");
		stoneImage.put(game.Color.WHITE, "GoldNSH.gif");
		locations = new HashMap<Integer, int[]>();
		locations.put(0, new int[] {355,509});
		locations.put(1, new int[] {655, 509});
		locations.put(2, new int[] {605, 509});
		locations.put(3, new int[] {555, 509});
		locations.put(4, new int[] {505, 509});
		locations.put(5, new int[] {455, 509});
		locations.put(6, new int[] {405, 509});
		locations.put(7, new int[] {305, 509});
		locations.put(8, new int[] {255, 509});
		locations.put(9, new int[] {205, 509});
		locations.put(10, new int[] {155, 509});
		locations.put(11, new int[] {105, 509});
		locations.put(12, new int[] {55, 509});
		locations.put(13, new int[] {55, 51});
		locations.put(14, new int[] {105, 51});
		locations.put(15, new int[] {155, 51});
		locations.put(16, new int[] {205, 51});
		locations.put(17, new int[] {255, 51});
		locations.put(18, new int[] {305, 51});
		locations.put(19, new int[] {405, 51});
		locations.put(20, new int[] {455, 51});
		locations.put(21, new int[] {505, 51});
		locations.put(22, new int[] {555, 51});
		locations.put(23, new int[] {605, 51});
		locations.put(24, new int[] {655, 51});
		locations.put(25, new int[] {355, 51});
		locations.put(26, new int[] {705, 51});
		locations.put(27, new int[] {705, 509});
		currStone = 0;
		play();
	}

	private void createGUI() {
		preferredSize = new Dimension(800, 600);
		mainContentPane = new JPanel();
		mainContentPane.setOpaque(true);
		mainContentPane.setLayout(null);
		mainContentPane.setBackground(new Color(0, 0, 0));
		mainContentPane.setPreferredSize(preferredSize);
		gamePanel = new JLayeredPane();
		gamePanel.setBackground(new Color(200, 200, 200));
		gamePanel.setBounds(0, 0, 800, 600);
		gamePanel.setOpaque(true);
		setSize(preferredSize);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setTitle("BackGammon RUS edition v4.20");

		background = new JLabel(new ImageIcon("BackGammon.jpg"));
		background.setBounds(0, 0, 800, 600);
//		background.setVisible(false);
//		gamePanel.setOpaque(false);
//		mainContentPane.setOpaque(false);
		// creating menu
		createMenu();

		// adding stones;
		initStones();
		for (int i = 0; i < stones.length; i++) {
			gamePanel.add(stones[i], 0);
		}
		// setting background to 1 Z order
		gamePanel.add(background, 1);
		// set Z level to 0
		for (int i = 0; i < stones.length; i++) {
			gamePanel.setComponentZOrder(stones[i], 0);
		}
		mainContentPane.add(gamePanel);
		setContentPane(mainContentPane);
		pack();

	}

	private void createMenu() {
		menuBar = new JMenuBar();

		menuGameTitle = new JMenu("Game");

		newGame = new JMenuItem("New Game");
		newGame.addActionListener(this);
		menuGameTitle.add(newGame);

		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menuGameTitle.add(exit);

		aboutGameTitle = new JMenu("About");

		help = new JMenuItem("Help");
		help.addActionListener(this);
		aboutGameTitle.add(help);

		// adding to menu bar
		menuBar.add(menuGameTitle);
		menuBar.add(aboutGameTitle);
		setJMenuBar(menuBar);

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// if (e.getSource() == exit) {
		// System.out.println("1");
		// System.exit(0);
		// }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			System.exit(0);
		}
	}

	private void initStones() {
		stones = new JLabel[NUMBER_OF_STONES];
		for (int i = 0; i < stones.length; i++) {
			if (i < NUMBER_OF_STONES / 2) {
				stones[i] = new JLabel(new ImageIcon("SilverNSH.gif"));
			} else {
				stones[i] = new JLabel(new ImageIcon("GoldNSH.gif"));
			}
			stones[i].setName(i + "");
			stones[i].setSize(40,40);
			stones[i].addMouseListener(this);
			stones[i].addMouseMotionListener(this);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		stoneDragged = Integer.parseInt(e.getComponent().getName());
		clickX = e.getX();
		clickY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// no stone is dragged
		stoneDragged = NUMBER_OF_STONES;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// off screen check
		int yLoc;
		int xLoc;

		if (stoneDragged >= 0 && stoneDragged < NUMBER_OF_STONES) {
			xLoc = e.getComponent().getX() + e.getX() - clickX;
			yLoc = e.getComponent().getY() + e.getY() - clickY;
			stones[stoneDragged].setLocation(xLoc, yLoc);

			if (stones[stoneDragged].getX() <= 50)
				stones[stoneDragged].setLocation(51, 51);
			if (stones[stoneDragged].getY() <= 50)
				stones[stoneDragged].setLocation(51, 51);
			if (stones[stoneDragged].getX() >= 660)
				stones[stoneDragged].setLocation(659, 509);
			if (stones[stoneDragged].getY() >= 510)
				stones[stoneDragged].setLocation(659, 509);

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println(stones[0].getLocation());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	boolean isWhite;

	private void play() {
		Dice dice;
		Board board;
		boolean movesLeft;

		dice = new Dice();
		board = new Board();
		isWhite = true;

		// while not won
		stones[0].setLocation(500, 500);
		while (true) {
			dice.trowDices();
			board.setDices(dice.getDices());
			board.setPlayers(isWhite);
			movesLeft = board.getPossibleMoves();

			while (movesLeft) {
				//stones[0].setLocation(500, 500);
				//stones[0].setBounds(500, 500, 40, 40);
				stones[0].setIcon(new ImageIcon("GoldNSH.gif"));
				stones[0].setLocation(500, 500);
				//stones[0].setBounds(500, 500, 40, 40);
				gamePanel.add(stones[0],0);
				//stones[0].setIcon(new ImageIcon("GoldNSH.gif"));
				System.out.println(stones[0].getIcon().getIconHeight());
				//stones[0].setVisible(true);
				//stones[0].setOpaque(true);
				//stones[0].setPreferredSize(new Dimension(500,500));
				//stones[0].setBackground(new Color(255,255,255));
				//stones[0].revalidate();
				//stones[0].repaint();
				//gamePanel.setComponentZOrder(stones[0], 0);
				System.out.println(stones[0].getLayout());
				System.out.println(stones[0].getLocation());
				setStones(board.getAmountArray(), board.getColorArray());
				//System.out.print(isWhite + " Enter choice");
//				gamePanel.repaint();
//				gamePanel.revalidate();
//				System.out.println(stones[0].getLayout());
//				System.out.println(stones[0].getLocation());
				break;
				//board.move(userInput);
				//movesLeft = board.getPossibleMoves();
			}
			break;
			//changePlayer();

		}
	}

	private void setStones(int[] amountArray, game.Color[] colorArray) {
		currStone = 0;
		for (int i = 0; i < Board.NO_OF_PLAYABLE_FIELDS;i++){
			if (amountArray[i] != 0) {
				placeStones(i, amountArray[i], stoneImage.get(colorArray[i]));
			}
		}
	}

	private void placeStones(int startPos, int amount, String picture) {
		for (int i = 0; i < amount;i++){
			stones[currStone].setIcon(new ImageIcon(picture));;
			if (startPos < 13) {
				stones[currStone].setLocation(locations.get(startPos)[0], locations.get(startPos)[1] - i*40);
			} else {
				stones[currStone].setLocation(locations.get(startPos)[0], locations.get(startPos)[1] + i*40);
			}
			stones[currStone].setSize(40, 40);
			
//			gamePanel.setComponentZOrder(stones[currStone], 0);
//			System.out.println(stones[currStone].getLocation());
//			System.out.println(gamePanel.getComponent(i).getLocation());
			currStone++;
		}
	}

	private void changePlayer() {
		if (isWhite) {
			isWhite = false;
		} else {
			isWhite = true;
		}
	}

}
