package org.arivuaata.rps;

public class RPSAI {

	public static char getMove() {
		return RPSPlayer.validInput.stream().findAny().get();
	}

}
