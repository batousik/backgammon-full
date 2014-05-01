package server;

import game.Game;

import java.io.*;
import java.net.*;

import main.Main;

public class Server {
	private int PORT_NUMBER = 5001;
	private String serverName = "127.0.0.1";
	
	/**
	 * Server Mode
	 * @param port_num
	 */
	public Server(String port_num) {
		PORT_NUMBER = Integer.parseInt(port_num);
	}
	
	/**
	 * Client Mode
	 * @param port_num
	 * @param server
	 */
	public Server(String port_num, String server) {
		PORT_NUMBER = Integer.parseInt(port_num);
		serverName = server;
	}
	
	// TODO after constructor straight away connect
	public void connect(String serverName, boolean serverMode, Game game) {
		if (!serverMode) {
			try {
				Socket clientSocket = new Socket(serverName, PORT_NUMBER);
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream());
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			}
			catch (Exception e) {
				
			}
		}
		else {
			try {
				ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
				Socket clientSocket = serverSocket.accept();
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream());
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				
				BackgammonProtocol protocol = new BackgammonProtocol();
				String inputLine;
				String outputLine;
				
				while ((inputLine = input.readLine()) != null) {
					outputLine = protocol.process(inputLine, game);
					output.println(outputLine);
					if (inputLine.equals("quit") || inputLine.equals("YouWin,Bye")) {
						break;
					}
				}
			}
			catch (Exception e) {
			}
		}
	}
}