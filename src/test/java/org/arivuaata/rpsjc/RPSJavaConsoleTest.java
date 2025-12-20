package org.arivuaata.rpsjc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class RPSJavaConsoleTest {

	@ParameterizedTest
	@ValueSource(strings = { "ra", "a", "ble was", "s " })
	void it_invalidPlayerInputTerminatesPlay(String playerInput) {
		// Create a ByteArrayOutputStream to capture the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		// Redirect standard output to the PrintStream
		PrintStream oldOut = System.out;
		System.setOut(ps);

		// Set-up test stdin
		InputStream oldIn = System.in;
		System.setIn(new ByteArrayInputStream(playerInput.getBytes()));

		RPSJavaConsole.main(null);

		System.setIn(oldIn);
		System.setOut(oldOut);

		assertTrue(baos.toString().trim().endsWith(
				String.format("Illegal State | invalid player input - '%s' | Terminating Play...", playerInput)));
	}

	@ParameterizedTest
	@MethodSource("getStringToOutputArgumentsProvider")
	void getStringToOutput(String expected, Object outputInfo, String outputType) {
		assertEquals(expected, RPSJavaConsole.getStringToOutput(outputInfo, outputType));
	}
	
	static Stream<Arguments> getStringToOutputArgumentsProvider() {
		return Stream.of(
			arguments("Enter Player Input",
					null, "PLAYER_INPUT_PROMPT"),
			arguments("Illegal State | someText | Terminating Play...",
					"someText", "ILLEGAL_STATE_AND_PLAY_TERMINATION"),
			arguments("AI Move: r",
					'r', "AI_MOVE"),
			arguments("winner: player",
					"player", "WINNER")
		);
	}
	
	@Test
	void it_writeOutput_for_WINNER() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		PrintStream oldOut = System.out;
		System.setOut(ps);
		
		new RPSJavaConsole().writeOutput("player", "WINNER");
		
		System.setOut(oldOut);

		assertEquals("winner: player", baos.toString().trim());
	}
	
	@ParameterizedTest
	@MethodSource("getStringToErrorArgumentsProvider")
	void getStringToError(String expected, Object errorInfo, String errorType) {
		assertEquals(expected, RPSJavaConsole.getStringToError(errorInfo, errorType));
	}
	
	static Stream<Arguments> getStringToErrorArgumentsProvider() {
		return Stream.of(
			arguments("Invalid AI Move: i",
					'i', "INVALID_AI_MOVE")
		);
	}
	
	@Test
	void writeError_for_INVALID_AI_MOVE() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		PrintStream oldErr = System.err;
		System.setErr(ps);
		
		new RPSJavaConsole().writeError('i', "INVALID_AI_MOVE");
		
		System.setErr(oldErr);
		
		String errorOutput = baos.toString();
		String trimmedErrorOutput = errorOutput.trim();
		
		assertTrue(trimmedErrorOutput.length() < errorOutput.length());
		assertEquals("Invalid AI Move: i", trimmedErrorOutput);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "ra\n", "a\n", "ble was\n", "s \n", "\n" })
	void takeInput_for_PLAYER_INPUT(String input) {
		InputStream oldIn = System.in;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		
		Object takenInput = new RPSJavaConsole().takeInput("PLAYER_INPUT");
		
		System.setIn(oldIn);

		assertTrue(input.startsWith(takenInput.toString()));
	}
}
