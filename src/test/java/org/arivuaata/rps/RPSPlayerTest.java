package org.arivuaata.rps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.arivuaata.io.IOHandler;
import org.arivuaata.rps.RPSPlayer.INPUT_TYPE;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

class RPSPlayerTest {

	@ParameterizedTest
	@ValueSource(strings = { "ra", "a", "ble was", "s " })
	void getMoveThrowsIllegalStateExceptionOnInvalidPlayerInput(String invalidInput) {
		IOHandler ioHandler = Mockito.mock();
		RPSPlayer rpsPlayer = new RPSPlayer(ioHandler);
		
		when(ioHandler.takeInput(INPUT_TYPE.PLAYER_INPUT)).thenReturn(invalidInput);
		
		IllegalStateException thrownException = assertThrows(
				IllegalStateException.class,
	            () -> rpsPlayer.getMove(),
	            "Expected getMove to throw IllegalStateException"
	    );
		
		assertEquals(thrownException.getMessage(), "invalid player input - " + invalidInput);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"r", "p", "s"})
	void getMoveReturnsCorrespondingCharForValidPlayerInput(String validInput) {
		IOHandler ioHandler = Mockito.mock();
		RPSPlayer rpsPlayer = new RPSPlayer(ioHandler);
		
		when(ioHandler.takeInput(INPUT_TYPE.PLAYER_INPUT)).thenReturn(validInput);
		
		assertEquals(validInput.charAt(0), rpsPlayer.getMove());
	}
}
