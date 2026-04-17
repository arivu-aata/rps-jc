package org.arivuaata.rps;

public class RPSAI {

	public static char getMove() {
		int validInputSize = RPSPlayer.validInput.size();
		int randomMoveNum = (int)(Math.random() * validInputSize);
		return RPSPlayer.validInput.toArray(new Character[validInputSize])[randomMoveNum];
	}

}
