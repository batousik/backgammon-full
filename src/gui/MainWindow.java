package gui;

import game.Board;
import game.Dice;
import game.Move;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


/**
 * @author 130017964
 * @version 4.20(release)
 */
public class MainWindow extends JFrame implements ActionListener,
		MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	// gui positioning constants
	private static final int LEFT_BOUNDARY = 50;
	private static final int RIGHT_BOUNDARY = 660;
	private static final int TOP_BOUNDARY = 50;
	private static final int BOTTOM_BOUNDARY = 510;
	private static final int HORIZONTAL_HALFWAY = 250;
	private static final int NUMBER_OF_STONES = 30;
	private static final int STONE_SIZE = 40;
	private static final int MAX_NO_PER_FIELD_NO_STACK = 6;
	
	// gui objects
	private JPanel mainContentPane;
	private JLayeredPane gamePanel;
	private JLabel background;
	private JMenuBar menuBar;
	private JMenu menuGameTitle, aboutGameTitle;
	private JMenuItem newGame, exit, help;
	private JLabel[] stones;
	private Dimension preferredSize;
	
	// vars needed for stones 
	private int stoneDragged = NUMBER_OF_STONES;
	private int clickX = 0;
	private int clickY = 0;
	private int initialX;
	private int initialY;
	private HashMap<game.Color, String> stoneImage;
	private HashMap<Integer, Zone> zones;
	private HashMap<Integer, int[]> locations;
	private int stackProportion;
	
	private Dice dice;
	private Board board;
	private boolean movesLeft;
	private boolean isWhite;
	
	private int currStone;
	private int chosenMove;

	

	public MainWindow() {
		// creates main interface of a program
		createGUI();
		// set pictures for stones
		initStoneColors();
		// create default place for each stone on each field
		initLocs();
		// create zones for each field for dropping stones
		initFieldZones();
		// Initializing the actual game, game logic
		initBackgammon();
		
		// no stone selected
		currStone = NUMBER_OF_STONES;
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
		// background.setVisible(false);
		// gamePanel.setOpaque(false);
		// mainContentPane.setOpaque(false);
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

	private void initStones() {
		stones = new JLabel[NUMBER_OF_STONES];
		for (int i = 0; i < stones.length; i++) {
			stones[i] = new JLabel();
			stones[i].setName(i + "");
			stones[i].setSize(STONE_SIZE, STONE_SIZE);
			stones[i].addMouseListener(this);
			stones[i].addMouseMotionListener(this);
		}
	}
	
	private void initBackgammon() {
		// creating new dice object
		dice = new Dice();
		// creating new backgammon board, that implements backgammon rules
		board = new Board();
		// White start
		isWhite = true;
		// throw and set dices for first move
		dice.trowDices();
		board.setDices(dice.getDices());
		// set current player
		board.setPlayers(isWhite);
		// drawing stones to start locations
		placeStones(board.getAmountArray(), board.getColorArray());
		// evaluating first set of valid moves
		movesLeft = board.getPossibleMoves();
	}
	
	private void moveMade() {
		board.move(chosenMove);
		// redrawing board to keep stones nicely in the line
		placeStones(board.getAmountArray(), board.getColorArray());
		//TODO split mowesLeft to two voids
		// moves left recalculates valid moves and says if any left
		movesLeft = board.getPossibleMoves();
		
		// if no possible moves left change the player
		if (!movesLeft) {
			changeTurn();
		}
	}
	
	private void changeTurn() {
		// swaps white for black and vice versa
		changePlayer();
		dice.trowDices();
		board.setDices(dice.getDices());
		board.setPlayers(isWhite);
		//player changed recalculate player moves
		movesLeft = board.getPossibleMoves();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			System.exit(0);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		stoneDragged = Integer.parseInt(e.getComponent().getName());
		clickX = e.getX();
		clickY = e.getY();
		initialX = e.getComponent().getX();
		initialY = e.getComponent().getY();
	}

	/**
	 * Method makes sure when stone is dropped it is dropped
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (stoneDragged >= 0 && stoneDragged < NUMBER_OF_STONES) {
			if (stones[stoneDragged].getX() < LEFT_BOUNDARY)
				stones[stoneDragged].setLocation(initialX, initialY);
			if (stones[stoneDragged].getY() < TOP_BOUNDARY)
				stones[stoneDragged].setLocation(initialX, initialY);
			if (stones[stoneDragged].getX() > RIGHT_BOUNDARY)
				stones[stoneDragged].setLocation(initialX, initialY);
			if (stones[stoneDragged].getY() > BOTTOM_BOUNDARY)
				stones[stoneDragged].setLocation(initialX, initialY);
			if (stonePlacedValidly(stones[stoneDragged].getX(),
					stones[stoneDragged].getY())) {
				moveMade();
			} else {
				stones[stoneDragged].setLocation(initialX, initialY);
			}

		}

		// no stone is dragged
		stoneDragged = NUMBER_OF_STONES;
	}


	/**
	 * Method checks if User moved Stone from and to a valid location
	 * 
	 * @param currLeftX top left corner of an object at the moment, X coordinate
	 * @param currLeftY top left corner of an object at the moment, Y coordinate
	 * @return true for valid and false for an invalid move
	 */
	private boolean stonePlacedValidly(int currLeftX, int currLeftY) {
		// getting valid moves for current game state
		ArrayList<Move> moves = board.getValidMoves();
		Zone startLoc;
		Zone endLoc;
		for (int i = 0; i < moves.size(); i++) {
			startLoc = zones.get(moves.get(i).getStartField());
			// if piece was taken from valid start zone
			if (startLoc.isInZone(initialX, initialY)) {
				endLoc = zones.get(moves.get(i).getEndField());
				// if piece was placed at valid location for given start
				// location, place it
				if (endLoc.isInZone(currLeftX, currLeftY)) {
					chosenMove = i;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Method Drags selected piece and doesn't allow the piece to be dragged of
	 * the screen
	 * 
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		// off screen check
		int yLoc;
		int xLoc;

		if (stoneDragged >= 0 && stoneDragged < NUMBER_OF_STONES) {
			xLoc = e.getComponent().getX() + e.getX() - clickX;
			yLoc = e.getComponent().getY() + e.getY() - clickY;
			stones[stoneDragged].setLocation(xLoc, yLoc);
			if (stones[stoneDragged].getX() < LEFT_BOUNDARY)
				stones[stoneDragged].setLocation(initialX, initialY);
			if (stones[stoneDragged].getY() < TOP_BOUNDARY)
				stones[stoneDragged].setLocation(initialX, initialY);
			if (stones[stoneDragged].getX() > RIGHT_BOUNDARY)
				stones[stoneDragged].setLocation(initialX, initialY);
			if (stones[stoneDragged].getY() > BOTTOM_BOUNDARY)
				stones[stoneDragged].setLocation(initialX, initialY);
		}

	}

	/**
	 * Method places all stones round the board with given amount and colour
	 * @param amountArray array that holds how many pieces at each field
	 * @param colorArray array that holds color of the pieces at each field
	 */
	private void placeStones(int[] amountArray, game.Color[] colorArray) {
		currStone = 0;
		for (int i = 0; i < Board.NO_OF_PLAYABLE_FIELDS; i++) {
			if (amountArray[i] != 0) {
				placeStonesOnAField(i, amountArray[i], stoneImage.get(colorArray[i]));
			}
		}
	}

	/**
	 * Method nicely places stones from one field into their locations
	 * 
	 * @param fieldID
	 * @param amount
	 * @param picture
	 */
	private void placeStonesOnAField(int fieldID, int amount, String picture) {
		// if more than MAX_NO_PER_FIELD_NO_STACK, then stack stones 
		if (amount > MAX_NO_PER_FIELD_NO_STACK) {
			stackProportion = 240/amount;
		} else {
			stackProportion = 40;
		}
		for (int i = 0; i < amount; i++) {
			//TODO create image icons at start
			stones[currStone].setIcon(new ImageIcon(picture));
			
			if (fieldID < 13) {
				stones[currStone].setLocation(locations.get(fieldID)[0],
						locations.get(fieldID)[1] - i * stackProportion);
			} else {
				stones[currStone].setLocation(locations.get(fieldID)[0],
						locations.get(fieldID)[1] + i * stackProportion);
			}
			currStone++;
		}
	}


	// simply changes current player
	private void changePlayer() {
		if (isWhite) {
			isWhite = false;
		} else {
			isWhite = true;
		}
	}

	// sets images for stones
	private void initStoneColors() {
		stoneImage = new HashMap<game.Color, String>();
		stoneImage.put(game.Color.BLACK, "SilverNSH.gif");
		stoneImage.put(game.Color.WHITE, "GoldNSH.gif");
	}

	// Creating zones for fields on a board for dropping stones
	private void initFieldZones() {
		zones = new HashMap<Integer, Zone>();
		zones.put(0, new Zone(350, 399, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(1, new Zone(650, 699, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(2, new Zone(600, 649, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(3, new Zone(550, 599, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(4, new Zone(500, 549, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(5, new Zone(450, 499, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(6, new Zone(400, 449, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(7, new Zone(300, 349, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(8, new Zone(250, 299, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(9, new Zone(200, 249, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(10, new Zone(150, 199, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(11, new Zone(100, 149, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		zones.put(12, new Zone(50, 99, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
		// top of the board
		zones.put(13, new Zone(50, 99, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(14,
				new Zone(100, 149, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(15,
				new Zone(150, 199, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(16,
				new Zone(200, 249, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(17,
				new Zone(250, 299, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(18,
				new Zone(300, 349, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(19,
				new Zone(400, 449, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(20,
				new Zone(450, 499, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(21,
				new Zone(500, 549, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(22,
				new Zone(550, 599, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(23,
				new Zone(600, 649, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(24,
				new Zone(650, 699, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(25,
				new Zone(350, 399, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		// bear offs
		zones.put(26,
				new Zone(700, 749, TOP_BOUNDARY, (HORIZONTAL_HALFWAY - 1)));
		zones.put(27, new Zone(700, 749, HORIZONTAL_HALFWAY, BOTTOM_BOUNDARY));
	}
	// Creating location for each field to place stones nicely
	private void initLocs() {
		locations = new HashMap<Integer, int[]>();
		locations.put(0, new int[] { 355, 509 });
		locations.put(1, new int[] { 655, 509 });
		locations.put(2, new int[] { 605, 509 });
		locations.put(3, new int[] { 555, 509 });
		locations.put(4, new int[] { 505, 509 });
		locations.put(5, new int[] { 455, 509 });
		locations.put(6, new int[] { 405, 509 });
		locations.put(7, new int[] { 305, 509 });
		locations.put(8, new int[] { 255, 509 });
		locations.put(9, new int[] { 205, 509 });
		locations.put(10, new int[] { 155, 509 });
		locations.put(11, new int[] { 105, 509 });
		locations.put(12, new int[] { 55, 509 });
		locations.put(13, new int[] { 55, 51 });
		locations.put(14, new int[] { 105, 51 });
		locations.put(15, new int[] { 155, 51 });
		locations.put(16, new int[] { 205, 51 });
		locations.put(17, new int[] { 255, 51 });
		locations.put(18, new int[] { 305, 51 });
		locations.put(19, new int[] { 405, 51 });
		locations.put(20, new int[] { 455, 51 });
		locations.put(21, new int[] { 505, 51 });
		locations.put(22, new int[] { 555, 51 });
		locations.put(23, new int[] { 605, 51 });
		locations.put(24, new int[] { 655, 51 });
		locations.put(25, new int[] { 355, 51 });
		locations.put(26, new int[] { 705, 51 });
		locations.put(27, new int[] { 705, 509 });
	}
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}
}
