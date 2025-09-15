package org.arivuaata.rpsjc;

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
		System.out.println(getStringToOutput(outputInfo, outputType));
	}

	static String getStringToOutput(Object outputInfo, String outputType) {
		switch (OUTPUT_TYPE.valueOf(outputType)) {
		case OUTPUT_TYPE.ILLEGAL_STATE_AND_PLAY_TERMINATION:
			return String.format("Illegal State | %s | Terminating Play...", outputInfo);
		case OUTPUT_TYPE.WINNER:
			return "winner: " + outputInfo;
		case OUTPUT_TYPE.INVALID_AI_MOVE:
			return "Invalid AI Move: " + outputInfo;
		default: throw new IllegalArgumentException("outputType: " + outputType);
		}
	}

	@Override
	public Object takeInput(String inputType) {
		switch (INPUT_TYPE.valueOf(inputType)) {
		case INPUT_TYPE.PLAYER_INPUT:
		default:
			try (Scanner scanner = new Scanner(System.in)) {
				return scanner.nextLine();
			}
		}
	}

}
