import java.util.Scanner;
import java.util.Set;

import org.arivuaata.rps.RPSAI;

public class RPSJavaConsole {

	public static class INPUT_TYPE {

		public static final String READ_LINE_FROM_STDIN = "read_line_from_stdin";

	}

	private static final String PLAYER = "player";
	private static final String AI = "ai";
	static final Set<Character> validInput = Set.of('r', 'p', 's');

	public static void main(String[] args) {
		String input = takeInput(INPUT_TYPE.READ_LINE_FROM_STDIN);

		if (isInvalid(input)) {
			System.out.print("Invalid Input! Terminating Play...");
			return;
		}

		char playerMove = input.charAt(0);
		char aiMove = RPSAI.move();

		String winner = determineWinner(playerMove, aiMove);

		System.out.print("winner: " + winner);

	}

	private static String takeInput(String inputType) {
		switch (inputType) {
		case INPUT_TYPE.READ_LINE_FROM_STDIN:
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
			System.out.println("Invalid AI Move: " + aiMove);
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
