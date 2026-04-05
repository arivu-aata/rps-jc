package org.arivuaata.rps;

public class RPSAI {

	public static char getMove() {
		return anyMove();
	}

	private static char anyMove() {
		return RPSPlayer.validInput.stream().findAny().get();
	}

}
