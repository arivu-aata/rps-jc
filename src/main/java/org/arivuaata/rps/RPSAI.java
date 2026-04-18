package org.arivuaata.rps;

public class RPSAI {

	public static char getMove() {
		int validMovesSize = RPSPlayer.validMoves.size();
		int randomMoveNum = (int)(Math.random() * validMovesSize);
		return RPSPlayer.validMoves.toArray(new Character[validMovesSize])[randomMoveNum];
	}

}
