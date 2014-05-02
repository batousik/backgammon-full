package server;

import game.Game;
import game.Move;
import gui.MainWindow;
import AI.AI;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import javax.print.attribute.standard.Finishings;

import main.Main;
/**
 * 
 * @author 130017964
 * @version 4.20(release)
 */
public class Server implements Runnable {
	private static int PORT_NUMBER = 5001;
	private static boolean firstMove = true;

	private boolean serverMode;
	private MainWindow mw;
	private String serverName = "127.0.0.1";
	public volatile PrintWriter output;
	public volatile boolean isMoveReceived;
	public volatile String moveReceived;

	public Server(String serverName, String serverPort, boolean serverMode,
			MainWindow mw) {
		Thread vasya = new Thread(this);
		if (serverName != null) {
			this.serverName = serverName;
		}
		this.serverMode = serverMode;
		this.mw = mw;
		vasya.start(); // ne ostav serdce musoram
	}

	// TODO This method should be static, we only ever have one connection.
	// Don't refactor live code.
	public void connect() {
		if (!serverMode) {
			try {
				Socket clientSocket = new Socket(serverName, PORT_NUMBER);
				output = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader input = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));
				String inputLine;
				while ((inputLine = input.readLine()) != null) {
					System.out.println("MESSAGE FROM SERVER '" + inputLine
							+ "'");
					if (inputLine.equals("YouWin,Bye")
							|| inputLine.equals("quit")) {
						break;
					}

					if (inputLine.equals("hello")) {
						output.println("newgame");
					} else if (inputLine.equals("OK")) {
						mw.myTurnInNetwork = true;
						if (mw.myTurnInNetwork) {
							System.out.println(mw.myTurnInNetwork);
							
							
						} else {
							output.println("pass");
						}
						
					} else {
						while (mw.getBoard().hasValidMovesLeft()) {

						}
						output.println(parseMoveMade(mw.getMovesMade()));

						isMoveReceived = true;
						moveReceived = inputLine;
						System.out.println(inputLine);
					}
				}

				input.close();
				output.close();
				clientSocket.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		} else {
			try {
				ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
				Socket clientSocket = serverSocket.accept();
				output = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader input = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));

				output.println("hello");

				String inputLine;
				while ((inputLine = input.readLine()) != null) {
					System.out.println("MESSAGE FROM CLIENT '" + inputLine
							+ "' ");
					if (inputLine.equals("newgame")) {
						output.println("OK");
						System.out.println("server got newgame");
					} else if (inputLine.equals("pass")) {
						mw.setMyTurnInNetwork(true);
						mw.setUpMove();
						while (mw.getBoard().hasValidMovesLeft()) {

						}
						output.println(parseMoveMade(mw.getMovesMade()));
					} else if (inputLine.equals("quit")
							|| inputLine.equals("YouWin,Bye")) {
						break;
					} else {
						isMoveReceived = true;
						moveReceived = inputLine;
						mw.setUpMove();
						while (mw.getBoard().hasValidMovesLeft()) {

						}
						output.println(parseMoveMade(mw.getMovesMade()));
					}
				}
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void run() {
		this.connect();
	}

	public void sendMsg(String msg) {
		output.println(msg);
	}

	private String parseMoveMade(ArrayList<Move> movesMade) {
		String output = mw.getBoard().getDices()[0] + "-"
				+ mw.getBoard().getDices()[0] + ":";
		for (int i = 0; i < movesMade.size(); i++) {

			output += "(";
			movesMade.get(i).getStartField();
			output += "|";
			movesMade.get(i).getEndField();
			if (i != movesMade.size() - 1) {
				output += "),";
			} else {
				output += ");";
			}
		}
		return output;
	}

	public String getMoveReceived() {
		String returnStr = moveReceived;
		moveReceived = "";
		isMoveReceived = false;
		return returnStr;
	}
}