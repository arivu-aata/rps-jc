import java.util.Scanner;
import java.util.Set;

import org.arivuaata.rps.RPSAI;

public class RPSJavaConsole {

	public static class OUTPUT_TYPE {

		public static final String INVALID_PLAYER_INPUT = "invalid_player_input";
		public static final String WINNER = "winner";
		public static final String INVALID_AI_MOVE = "invalid_ai_move";

	}

	public static class INPUT_TYPE {

		public static final String PLAYER_INPUT = "player_input";

	}

	private static final String PLAYER = "player";
	private static final String AI = "ai";
	static final Set<Character> validInput = Set.of('r', 'p', 's');

	public static void main(String[] args) {
		String input = takeInput(INPUT_TYPE.PLAYER_INPUT);

		if (isInvalid(input)) {
			String output = "Invalid Input! Terminating Play...";
			writeOutput(output, OUTPUT_TYPE.INVALID_PLAYER_INPUT);
			return;
		}

		char playerMove = input.charAt(0);
		char aiMove = RPSAI.move();

		String winner = determineWinner(playerMove, aiMove);

		String output = "winner: " + winner;
		writeOutput(output, OUTPUT_TYPE.WINNER);
	}

	private static void writeOutput(String output, String outputType) {
		switch (outputType) {
		case OUTPUT_TYPE.INVALID_PLAYER_INPUT:
		case OUTPUT_TYPE.WINNER:
			System.out.print(output);
			break;
		case OUTPUT_TYPE.INVALID_AI_MOVE:
		default:
			System.out.println(output);
			break;
		}
	}

	private static String takeInput(String inputType) {
		switch (inputType) {
		case INPUT_TYPE.PLAYER_INPUT:
		default:
			try (Scanner scanner = new Scanner(System.in)) {
				return scanner.nextLine();
			}
		}
	}

	private static String determineWinner(char playerMove, char aiMove) {
		switch (aiMove) {
		case 'r':
			return playerMove == 'p' ? PLAYER : AI;
		case 'p':
			return playerMove == 's' ? PLAYER : AI;
		case 's':
			return playerMove == 'r' ? PLAYER : AI;
		default:
			String output = "Invalid AI Move: " + aiMove;
			writeOutput(output, OUTPUT_TYPE.INVALID_AI_MOVE);
			return PLAYER;
		}
	}

	private static boolean isInvalid(String input) {
		if (input.length() > 1) {
			return true;
		}

		char charInput = input.charAt(0);
		if (validInput.contains(charInput)) {
			return false;
		}

		return true;
	}

}
