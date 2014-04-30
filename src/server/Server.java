package server;

import game.Game;

import java.io.*;
import java.net.*;

import main.Main;

public class Server {
	private static final int PORT_NUMBER = 5001;
	
	public static void connect(String serverName, boolean serverMode, Game game) {
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