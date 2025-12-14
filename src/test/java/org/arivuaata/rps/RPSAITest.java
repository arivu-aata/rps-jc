package org.arivuaata.rps;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RPSAITest {

	@Test
	void testGetMove() {
		char actualMove = RPSAI.getMove();
		assertTrue(RPSPlayer.validInput.contains(actualMove));
	}

}
