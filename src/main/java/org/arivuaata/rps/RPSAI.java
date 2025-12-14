package org.arivuaata.rps;

public class RPSAI {

	public static char getMove() {
		int randomMoveNumber = (int) Math.floor(RPSPlayer.validInput.size() * Math.random());

		return RPSPlayer.validInput.stream().skip(randomMoveNumber).findFirst().get();
	}

}
