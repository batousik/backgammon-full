package server;

import game.Game;
import AI.AI;
import java.io.*;
import java.net.*;

import main.Main;

public class Server {
	private static int PORT_NUMBER = 5001;
	private static String serverName = "127.0.0.1";
	
	// TODO This method should be static, we only ever have one connection. Don't refactor live code.
	public static void connect(String serverName, boolean serverMode, Game game) {
		if (!serverMode) {
			try {
				Socket clientSocket = new Socket(serverName, PORT_NUMBER);
				PrintWriter output = new PrintWriter(clientSocket.getOutputStream());
				BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String inputLine;
				while((inputLine = input.readLine()) != null) {
					if (inputLine.equals("YouWin,Bye") || inputLine.equals("quit")) {
						break;
					}
					if(inputLine.equals("hello")) {
						output.print("newgame");
					}
					else {
						game.play(null);
						output.print(AI.lastMoveMade);
					}
				}
				
				input.close();
				output.close();
				clientSocket.close();
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
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