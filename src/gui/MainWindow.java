package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements ActionListener, ItemListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JLabel background;
	private JMenuBar menuBar;
	private JMenu menuGameTitle;
	private JMenu aboutGameTitle;
	private JMenuItem newGame;
	private JMenuItem exit;
	private JMenuItem help;

	public MainWindow() {
		mainPanel = new JPanel();
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		background = new JLabel(new ImageIcon("BackGammon.jpg"));
		mainPanel.add(background);
		setResizable(false);
		setTitle("BackGammon RUS edition v4.20");
		// creating menu
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
		add(mainPanel);
		pack();
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
//		if (e.getSource() == exit) {
//			System.out.println("1");
//			System.exit(0);
//		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == exit) {
			System.out.println("1");
			System.exit(0);
		}
	}
}
