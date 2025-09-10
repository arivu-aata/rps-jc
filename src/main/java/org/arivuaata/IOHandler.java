package org.arivuaata;

public interface IOHandler {

	Object takeInput(String inputType);
	
	void writeOutput(Object outputInfo, String outputType);
}