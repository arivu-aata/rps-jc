import java.util.Scanner;

import org.arivuaata.io.IOHandler;
import org.arivuaata.rps.RPSPlayer;

import static org.arivuaata.rps.RPSPlayer.*;

public class RPSJavaConsole implements IOHandler {

	public static void main(String[] args) {
		new RPSPlayer(getIOHandler()).play();
	}

	private static IOHandler getIOHandler() {
		return new RPSJavaConsole();
	}

	@Override
	public void writeOutput(Object outputInfo, String outputType) {
		switch (outputType) {
		case OUTPUT_TYPE.INVALID_PLAYER_INPUT_AND_PLAY_TERMINATION:
			System.out.print("Invalid Player Input! Terminating Play...");
			break;
		case OUTPUT_TYPE.WINNER:
			System.out.print("winner: " + outputInfo);
			break;
		case OUTPUT_TYPE.INVALID_AI_MOVE:
			System.out.println("Invalid AI Move: " + outputInfo);
			break;
		default:
			break;
		}
	}

	@Override
	public Object takeInput(String inputType) {
		switch (inputType) {
		case INPUT_TYPE.PLAYER_INPUT:
		default:
			try (Scanner scanner = new Scanner(System.in)) {
				return scanner.nextLine();
			}
		}
	}

}
