package org.arivuaata.rpsjc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
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

		assertEquals(String.format("Illegal State | invalid player input - '%s' | Terminating Play...", playerInput),
				baos.toString().trim());
	}

	@Test
	void getStringToOutput_for_ILLEGAL_STATE_AND_PLAY_TERMINATION() {
		assertEquals("Illegal State | someText | Terminating Play...",
				RPSJavaConsole.getStringToOutput("someText", "ILLEGAL_STATE_AND_PLAY_TERMINATION"));
	}
	
	@Test
	void getStringToOutput_for_WINNER() {
		assertEquals("winner: player",
				RPSJavaConsole.getStringToOutput("player", "WINNER"));
	}
	
	@Test
	void getStringToOutput_for_INVALID_AI_MOVE() {
		assertEquals("Invalid AI Move: i",
				RPSJavaConsole.getStringToOutput('i', "INVALID_AI_MOVE"));
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
	@ValueSource(strings = { "ra\n", "a\n", "ble was\n", "s \n", "\n" })
	void takeInput_for_PLAYER_INPUT(String input) {
		InputStream oldIn = System.in;
		System.setIn(new ByteArrayInputStream(input.getBytes()));
		
		Object takenInput = new RPSJavaConsole().takeInput("PLAYER_INPUT");
		
		System.setIn(oldIn);

		assertTrue(input.startsWith(takenInput.toString()));
	}
}
