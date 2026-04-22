package org.arivuaata.rps;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RPSAITest {

	@Test
	void getMove() {
		for (int i = 0; i < RPSPlayer.validMoves.size(); i++) {
			assertTrue(RPSPlayer.validMoves.contains(RPSAI.getMove()));
		}
	}

}
