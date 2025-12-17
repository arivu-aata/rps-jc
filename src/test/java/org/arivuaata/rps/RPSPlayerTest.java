package org.arivuaata.rps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.arivuaata.io.IOHandler;
import org.arivuaata.rps.RPSPlayer.INPUT_TYPE;
import org.arivuaata.rps.RPSPlayer.OUTPUT_TYPE;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

class RPSPlayerTest {

	@Test
	void validInputSet() {
		assertTrue(RPSPlayer.validInput.size() == 3);
		assertTrue(RPSPlayer.validInput.containsAll(Arrays.asList('r', 'p', 's')));
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "ra", "a", "ble was", "s ", "" })
	void getMoveThrowsIllegalStateExceptionOnInvalidPlayerInput(String invalidInput) {
		IOHandler ioHandler = Mockito.mock();
		RPSPlayer rpsPlayer = new RPSPlayer(ioHandler);
		
		doNothing().when(ioHandler).writeOutput(null, OUTPUT_TYPE.PLAYER_INPUT_PROMPT.name());
		when(ioHandler.takeInput(INPUT_TYPE.PLAYER_INPUT.name())).thenReturn(invalidInput);
		
		IllegalStateException thrownException = assertThrows(
				IllegalStateException.class,
	            () -> rpsPlayer.getMove(),
	            "Expected getMove to throw IllegalStateException"
	    );
		
		assertEquals(thrownException.getMessage(),
					String.format("invalid player input - '%s'", invalidInput));
		
		verify(ioHandler).writeOutput(null, OUTPUT_TYPE.PLAYER_INPUT_PROMPT.name());
		verify(ioHandler).takeInput(INPUT_TYPE.PLAYER_INPUT.name());
		verifyNoMoreInteractions(ioHandler);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"r", "p", "s"})
	void getMoveReturnsCorrespondingCharForValidPlayerInput(String validInput) {
		IOHandler ioHandler = Mockito.mock();
		RPSPlayer rpsPlayer = new RPSPlayer(ioHandler);
		
		doNothing().when(ioHandler).writeOutput(null, OUTPUT_TYPE.PLAYER_INPUT_PROMPT.name());
		when(ioHandler.takeInput(INPUT_TYPE.PLAYER_INPUT.name())).thenReturn(validInput);
		
		assertEquals(validInput.charAt(0), rpsPlayer.getMove());
		
		verify(ioHandler).writeOutput(null, OUTPUT_TYPE.PLAYER_INPUT_PROMPT.name());
		verify(ioHandler).takeInput(INPUT_TYPE.PLAYER_INPUT.name());
		verifyNoMoreInteractions(ioHandler);
	}
	
	@ParameterizedTest
	@CsvSource({ "r, r, draw", "r, p, ai", "r, s, player", "p, p, draw", "p, s, ai", "p, r, player", "s, s, draw",
		"s, r, ai", "s, p, player", "r, 0, player" })
	void validPlayerInputDeterminesWinner(String validPlayerInput, char aiMove, String winner) {
		IOHandler ioHandler = Mockito.mock();
		
		doNothing().when(ioHandler).writeOutput(null, OUTPUT_TYPE.PLAYER_INPUT_PROMPT.name());
		when(ioHandler.takeInput(INPUT_TYPE.PLAYER_INPUT.name())).thenReturn(validPlayerInput);
		doNothing().when(ioHandler).writeOutput(aiMove, OUTPUT_TYPE.AI_MOVE.name());
		doNothing().when(ioHandler).writeOutput(aiMove, OUTPUT_TYPE.INVALID_AI_MOVE.name());
		doNothing().when(ioHandler).writeOutput(winner, OUTPUT_TYPE.WINNER.name());
		
		try (MockedStatic<RPSAI> mockedAI = Mockito.mockStatic(RPSAI.class)) {
			mockedAI.when(RPSAI::getMove).thenReturn(aiMove);
			
			new RPSPlayer(ioHandler).play();

			mockedAI.verify(RPSAI::getMove);
		}
		
		verify(ioHandler).writeOutput(null, OUTPUT_TYPE.PLAYER_INPUT_PROMPT.name());
		verify(ioHandler).takeInput(INPUT_TYPE.PLAYER_INPUT.name());
		verify(ioHandler).writeOutput(aiMove, OUTPUT_TYPE.AI_MOVE.name());
		
		if (!RPSPlayer.validInput.contains(aiMove)) {
			verify(ioHandler).writeOutput(aiMove, OUTPUT_TYPE.INVALID_AI_MOVE.name());
		}

		verify(ioHandler).writeOutput(winner, OUTPUT_TYPE.WINNER.name());
		
		verifyNoMoreInteractions(ioHandler);
	}
	
}
