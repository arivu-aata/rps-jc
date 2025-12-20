package org.arivuaata.rpsjc;

import java.util.Scanner;

import org.arivuaata.io.IOHandler;
import org.arivuaata.rps.RPSPlayer;
import org.arivuaata.rps.RPSPlayer.ERROR_TYPE;

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
		case PLAYER_INPUT_PROMPT:
			return "Enter Player Input";
		case ILLEGAL_STATE_AND_PLAY_TERMINATION:
			return String.format("Illegal State | %s | Terminating Play...", outputInfo);
		case AI_MOVE:
			return "AI Move: " + outputInfo;
		case WINNER:
			return "winner: " + outputInfo;
		default: throw new IllegalArgumentException("outputType: " + outputType);
		}
	}

	@Override
	public void writeError(Object errorInfo, String errorType) {
		System.err.println(getStringToError(errorInfo, errorType));
	}
	
	static String getStringToError(Object errorInfo, String errorType) {
		switch (ERROR_TYPE.valueOf(errorType)) {
		case INVALID_AI_MOVE:
			 return "Invalid AI Move: " + errorInfo;
		default: throw new IllegalArgumentException("errorType: " + errorType);
		}
	}
	
	@Override
	public Object takeInput(String inputType) {
		switch (INPUT_TYPE.valueOf(inputType)) {
		case PLAYER_INPUT:
		default:
			try (Scanner scanner = new Scanner(System.in)) {
				return scanner.nextLine();
			}
		}
	}

}
