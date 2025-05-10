import java.util.Scanner;
import java.util.Set;

public class RPSJavaConsole {

	private static final Set<Character> validInput = Set.of('r', 'p', 's');

	public static void main(String[] args) {
		try(Scanner scanner = new Scanner(System.in)) {
			String input = scanner.nextLine();
			
			if (isInvalid(input)) {
				System.out.print("Invalid Input! Terminating Play...");
				return;
			}
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
