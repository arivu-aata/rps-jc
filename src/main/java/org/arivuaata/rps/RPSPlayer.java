package org.arivuaata.rps;

import java.util.Set;

import org.arivuaata.io.IOHandler;

public class RPSPlayer {

	public static class INPUT_TYPE {
	
		public static final String PLAYER_INPUT = "player_input";
	
	}

	public static class OUTPUT_TYPE {
	
		public static final String ILLEGAL_STATE_AND_PLAY_TERMINATION = "illegal_state_and_play_termination";
		public static final String WINNER = "winner";
		public static final String INVALID_AI_MOVE = "invalid_ai_move";
	
	}

	private static final String PLAYER = "player";
	private static final String AI = "ai";
	private static final String DRAW = "draw";
	public static final Set<Character> validInput = Set.of('r', 'p', 's');

	private static String determineWinner(char playerMove, char aiMove) {
		switch (aiMove) {
		case 'r':
			return playerMove == 'p' ? PLAYER : playerMove == 's' ? AI : DRAW;
		case 'p':
			return playerMove == 's' ? PLAYER : playerMove == 'r' ? AI : DRAW;
		case 's':
			return playerMove == 'r' ? PLAYER : playerMove == 'p' ? AI : DRAW;
		default:
			throw new IllegalArgumentException();
		}
	}

	private static boolean isInvalid(String playerInput) {
		if (playerInput.length() > 1) {
			return true;
		}
	
		char charInput = playerInput.charAt(0);
		if (validInput.contains(charInput)) {
			return false;
		}
	
		return true;
	}

	private final IOHandler ioHandler;
	
	public RPSPlayer(IOHandler ioHandler) {
		this.ioHandler = ioHandler;
	}
	
	public void play() {
		char playerMove;
		try {
			playerMove = this.getMove();
		} catch (IllegalStateException e) {
			ioHandler.writeOutput(e.getMessage(), OUTPUT_TYPE.ILLEGAL_STATE_AND_PLAY_TERMINATION);
			return;
		}
	
		char aiMove = RPSAI.move();
	
		String winner;
		try {
			winner = determineWinner(playerMove, aiMove);
		} catch (IllegalArgumentException e) {
			ioHandler.writeOutput(aiMove, OUTPUT_TYPE.INVALID_AI_MOVE);
			winner = PLAYER;
		}
	
		ioHandler.writeOutput(winner, OUTPUT_TYPE.WINNER);
	}

	public char getMove() {
		String playerInput = (String) ioHandler.takeInput(INPUT_TYPE.PLAYER_INPUT);
		
		if (isInvalid(playerInput)) {
			throw new IllegalStateException(String.format("invalid player input - '%s'", playerInput));
		}
		
		return playerInput.charAt(0);
	}

}
